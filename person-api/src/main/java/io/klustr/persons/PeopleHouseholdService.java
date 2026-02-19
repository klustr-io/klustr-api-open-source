package io.klustr.persons;

import com.google.common.collect.Lists;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.schemas.console.Gender;
import io.klustr.schemas.console.Status;
import io.klustr.schemas.console.consent.ConsentDelegationRequest;
import io.klustr.schemas.console.identity.*;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.schemas.persons.*;
import io.klustr.schemas.persons.households.Household;
import io.klustr.schemas.persons.households.HouseholdMember;
import io.klustr.schemas.persons.supervised.SupervisedStatus;
import io.klustr.schemas.persons.supervised.ThirdPartyPermission;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Gives the digital profile for a user which is displayed
 * to others and them and is a customizable version of thier
 * online presence (vs. verified identity).
 */
@RestController
@Component
@RequestMapping("/identities/me/households")
@Tag(name = "Household APIs", description = "Identity Management APIs")
public class PeopleHouseholdService extends AbstractApiService {

    private final PersonStorage storage;
    private final KratosApi kratos;

    public PeopleHouseholdService(PersonStorage storage, KratosApi kratos) {
        this.storage = storage;
        this.kratos = kratos;
    }

    @GetMapping
    @Operation(
summary = "Retrieve households linked to the authenticated user",
            operationId = "getMyHouseholds",
            description = """
This endpoint allows the authenticated user to access all households associated with their profile, including any that may have expired. It enhances the user's ability to manage their digital presence by providing a comprehensive view of their household affiliations.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public DocumentResult<Household> getHousehold(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Person person = this.storage.persons().getObject(oauth.getName());
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person not found"
            );
        }

        DbQuery fq = Db.query("members").contains("person_id").eq(person.getId());
        return this.storage.households().insecureQuery(fq, Pagination.all());
    }

    @PostMapping
    @Operation(
summary = "Create a household for the authenticated user.",
            operationId = "createMyHousehold",
            description = """
This endpoint allows the authenticated user to create a household. The household acts as a customizable digital profile, reflecting the user's online presence. It plays a crucial role in managing identity and visibility within the platform.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Household createHousehold(@RequestBody HouseholdCreateRequest body, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Person person = this.storage.persons().getObject(oauth.getName());
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person not found for current user."
            );
        }

        Household obj = new Household()
                .withId(UUID.randomUUID().toString())
                .withModifiedDate(DateTime.now())
                .withCreationDate(DateTime.now())
                .withName(body.name)
                .withMembers(Lists.newArrayList(
                        new HouseholdMember()
                                .withPrimary(true)
                                .withPersonId(person.getId())
                                .withStatus(Status.active)
                                .withStartDate(DateTime.now())
                ));
        this.storage.households().insertObject(obj.getId(), obj);
        return obj;
    }

    public static class HouseholdCreateRequest {
        public String name;
    }

    @GetMapping("/{householdId}")
    @Operation(
summary = "Retrieve my linked households, including expired ones.",
            operationId = "getMyHousehold",
            description = """
This endpoint allows users to access all households linked to their profile, including those that have expired. It provides a comprehensive view of household information, enabling effective management and oversight of personal household data.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Household getHousehold(
            @PathVariable("householdId") String householdId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Person person = this.storage.persons().getObject(oauth.getName());
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person not found"
            );
        }

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        return household;
    }

    @GetMapping("/{householdId}/actions")
    @Operation(
summary = "Retrieve actions for my household management",
            operationId = "getMyHouseholdActions",
            description = """
This endpoint provides a list of actions that the household owner needs to complete. It aims to enhance visibility into essential tasks for effective household management, ensuring users can stay organized and informed.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<HouseholdAction> getHouseholdActions(@PathVariable("householdId") String householdId,
                                    @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        // consent delegation right now is the only household action
        // in the future maybe ekyc is required or other actions like registering address

        DbQuery fq = Db.and(
                Db.query("household_id").eq(household.getId())
        );

        DocumentResult<ConsentDelegationRequest> req = this.storage.consent_delegation()
                .insecureQuery(fq, DbSort.dsc("creation_date"), Pagination.all());

        return req.docs.stream().map(x -> {
            HouseholdAction a = new HouseholdAction();
            a.id = x.getId();
            a.type = "consent";
            a.timestamp = x.getCreationDate();
            a.payload = x;
            a.person = new SimpleProfile(this.storage.persons().getObject(x.getSubjectId()));
            return a;
        }).toList();
    }

    public static class HouseholdAction {
        public String id;
        public String type;
        public DateTime timestamp;
        public SimpleProfile person;
        public Object payload;
    }

    public static class SimpleProfile {
        public String given_name;
        public String family_name;
        public String photo;
        public String id;

        public SimpleProfile() {}
        public SimpleProfile(Person p) {
            if (p.getTraits() != null && p.getTraits().getName() != null) {
                this.family_name = p.getTraits().getName().getFamilyName();
                this.given_name = p.getTraits().getName().getGivenName();
            }
            if (p.getMetadataPublic() != null) {
                this.photo = p.getMetadataPublic().getPicture();
            }
            this.id = p.getId();
        }
    }

    @GetMapping("/{householdId}/members/{personId}")
    @Operation(
summary = "Retrieve details for a household member securely",
            operationId = "getMyHouseholdMemberDetails",
            description = """
This endpoint allows users to access their own household member information. The data retrieved is part of the user's digital profile, ensuring privacy and customization based on individual preferences.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public PersonHouseholdRelationship getPersonHouseholdRelationship(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        Optional<HouseholdMember> match = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(personId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person not found in household."
            );
        }

        Person person = this.storage.persons().getObject(match.get().getPersonId());

        PersonHouseholdRelationship r = new PersonHouseholdRelationship();
        r.person = person;
        r.member = match.get();
        return r;
    }

    @PatchMapping("/{householdId}/members/{personId}")
    @Operation(
summary = "Invite a user to join your household",
            operationId = "inviteMemberToMyHousehold",
            description = """
Send an invitation to the specified user to join your household. The invitation will be received upon their next login or through other activation channels. This operation enhances user engagement and strengthens household connectivity.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Household inviteToHousehold(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Person person = this.storage.persons().getObject(oauth.getName());
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person not found"
            );
        }

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        Optional<HouseholdMember> exists = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(personId))
                .filter(x -> x.getExpirationDate() == null)
                .findFirst();
        if (exists.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Person already joined."
            );
        }

        // TODO start workflow to invite a user into the household
        household.getMembers().add(new HouseholdMember()
                .withPersonId(person.getId())
                .withCreateDate(DateTime.now())
                .withStatus(Status.pending)  // its not fully accepted yet
        );

        return household;
    }

    @GetMapping("/{householdId}/members")
    @Operation(
summary = "List members of your household",
            operationId = "listMyHouseholdMembers",
            description = """
This endpoint retrieves detailed information about individuals in your household. It provides visibility into the profiles of household members, allowing you to manage and view your relationships effectively. This functionality is intended for personal use, ensuring you have access to the necessary information about your household.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<PersonHouseholdRelationship> listPersonsInHousehold(
            @PathVariable("householdId") String householdId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Person person = this.storage.persons().getObject(oauth.getName());
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person not found"
            );
        }

        Household household = this.storage.households().getObject(householdId);
        if (null == household) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Household not found"
            );
        }

        // make sure is member
        Optional<HouseholdMember> isMember = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(oauth.getName())).findFirst();
        if (isMember.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not a member of the household."
            );
        }

        return household.getMembers().stream().map(x -> {
            Person p = this.storage.persons().getObject(x.getPersonId());
            PersonHouseholdRelationship r = new PersonHouseholdRelationship();
            r.person = p;
            r.member = x;
            return r;
        }).toList();
    }

    public static class PersonHouseholdRelationship {
        public Person person;
        public HouseholdMember member;
    }

    public static class FormResponse<T> {
        public T data;
        public List<String> errors = Lists.newArrayList();
    }

    public static class HouseholdFormResponse extends FormResponse<HouseholdMember> { }

    @PostMapping("/{householdId}/members")
    @Operation(
summary = "Add a member to your household",
            operationId = "joinMyHouseholdMember",
            description = """
This endpoint allows you to register a new member under your household. It ensures the new member's account is linked, enhancing the household's digital profile. This action is for personal use and aids in managing household identities.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public HouseholdFormResponse joinHousehold(
            @PathVariable("householdId") String householdId,
            @RequestBody MemberRequest body,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        HouseholdFormResponse r = new HouseholdFormResponse();

        org.joda.time.LocalDate dob = null;

        if (StringUtils.isBlank(body.given_name)) {
            r.errors.add("Missing given name");
        }
        if (StringUtils.isBlank(body.date_of_birth)) {
            r.errors.add("Missing date of birth");
        }
        if (StringUtils.isBlank(body.gender)) {
            r.errors.add("Missing gender");
        }
        if (StringUtils.isBlank(body.username)) {
            r.errors.add("Missing username");
        }
        if (StringUtils.isBlank(body.password)) {
            r.errors.add("Missing password");
        }
        if (!body.date_of_birth.matches("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")) {
            r.errors.add("Date of birth must be in format YYYY-MM-DD");
        } else {
            try {
                dob = org.joda.time.LocalDate.parse(body.date_of_birth);
            } catch (Exception ex) {
                // ignore
            }
            if (dob == null) {
                r.errors.add("Invalid date");
            } else if (dob.isAfter(org.joda.time.LocalDate.now())) {
                r.errors.add("Invalid date, date is after now.");
            }
        }
        if (dob != null && dob.isBefore(org.joda.time.LocalDate.now().minusYears(16))) {
            r.errors.add("Can not add an adult account to household.");
        }
        if (!r.errors.isEmpty()) {
            return r;
        }

        // create identity first to assign to id of person
        Identity identity = this.kratos.create(new Identity()
                .withCredentials(new IdentityCredentials()
                        .withPassword(new IdentityPasswordCredential()
                                .withConfig(new IdentityPasswordCredentialConfig()
                                        .withPassword(body.password)
                                )
                        )
                )
                .withTraits(new IdentityTraits()
                        .withBirthdate(dob)
                        .withGender(Gender.fromValue(body.gender))
                        .withUsername(body.username)
                        .withName(new IdentityName()
                                .withGivenName(body.given_name)
                                .withFamilyName(body.family_name)
                        )
                )
        );

        DateTime timestamp = DateTime.now();
        Person person = new Person()
                .withId(identity.getId())
                .withTraits(new IdentityTraits()
                        .withGender(Gender.fromValue(body.gender))
                        .withBirthdate(dob)
                        .withName(new IdentityName()
                                .withFamilyName(body.family_name)
                                .withGivenName(body.given_name)
                        )
                        .withUsername(body.username)
                )
                .withName(Lists.newArrayList(
                        new Name()
                                .withSource(Name.Source.USER)
                                .withFamilyName(body.family_name)
                                .withGivenName(body.given_name)
                ))
                .withStatus(
                        new ProfileStatus()
                                .withSupervisedStatus(new SupervisedStatus()
                                        .withThirdPartyConsent(ThirdPartyPermission.DENY_ALL)
                                )
                )
                .withCredentials(new IdentityCredentials()
                        .withPassword(new IdentityPasswordCredential()
                                .withConfig(new IdentityPasswordCredentialConfig()
                                        .withPassword(body.password)
                                )
                        )
                );

        this.storage.persons().insertObject(person.getId(), person);

        // link the person to kratos identity
        person.setIds(Lists.newArrayList(
                new ExternalReference().withExternalId(identity.getId()).withSource("kratos").withCreationDate(timestamp)
                        .withUpdateDate(timestamp)
        ));
        this.storage.persons().updateObject(person.getId(), person);

        // add to hosehold
        Household hh = this.storage.households().getObject(householdId);
        HouseholdMember member = new HouseholdMember()
                .withStatus(Status.active)
                .withCreateDate(timestamp)
                .withPrimary(false)
                .withPersonId(person.getId());
        hh.getMembers().add(member);
        this.storage.households().updateObject(hh.getId(), hh);

        HouseholdFormResponse f = new HouseholdFormResponse();
        f.data = member;
        return f;
    }

    public static class MemberRequest {
        public String given_name;
        public String family_name;
        public String date_of_birth;
        public String gender;
        public String username;
        public String password;
    }

    public static class UpdateAvatarRequest {
        public String uri;
    }

    public static class UpdateProfileRequest {
        public String given_name;
        public String family_name;
        public String date_of_birth;
        public String gender;
    }

    public static class UpdateThirdPartyConsentRequest {
        public ThirdPartyPermission permission;
    }

    @PostMapping("/{householdId}/members/{personId}/thirdparties")
    @Operation(
summary = "Update consent for third party apps for a household member",
            operationId = "updateMyThirdPartyConsent",
            description = """
This operation allows users to manage their consent settings for third party applications associated with their household. It is specifically designed for supervised accounts, ensuring that users can effectively control their privacy preferences and visibility.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void updateConsent(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @RequestBody UpdateThirdPartyConsentRequest body,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        Optional<HouseholdMember> match = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(personId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person is not a member of the household."
            );
        }

        Person person = this.storage.persons().getObject(match.get().getPersonId());

        ProfileStatus status = person.getStatus();
        if (status == null) {
            status = new ProfileStatus();
        }
        SupervisedStatus supervisedStatus = status.getSupervisedStatus();
        if (supervisedStatus == null) {
            supervisedStatus = new SupervisedStatus();
        }
        body.permission = body.permission != null ? body.permission : ThirdPartyPermission.DENY_ALL;
        supervisedStatus.setThirdPartyConsent(body.permission);
        this.storage.persons().updateObject(personId, person);
    }

    @PostMapping("/{householdId}/members/{personId}/profile")
    @Operation(
summary = "Update profile for a household member",
            operationId = "updateMyHouseholdMemberProfile",
            description = """
This endpoint allows the owner of a household to update the profile information of a specified member. The calling user must be the household owner to perform this action. Ensure that the provided information accurately reflects the member's digital profile.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void updateProfile(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @RequestBody UpdateProfileRequest body,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        Optional<HouseholdMember> match = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(personId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person is not a member of the household."
            );
        }

        Person updatePerson = this.storage.persons().getObject(personId);
        if (updatePerson.getTraits() == null) {
            updatePerson.setTraits(new IdentityTraits());
        }
        updatePerson.getTraits()
                .withName(new IdentityName()
                        .withGivenName(body.given_name)
                        .withFamilyName(body.family_name)
                )
                .withBirthdate(org.joda.time.LocalDate.parse(body.date_of_birth))
                .withGender(Gender.fromValue(body.gender));

        this.storage.persons().updateObject(personId, updatePerson);

        // identity
        Identity identity = this.kratos.getIdentity(personId);
        if (identity.getTraits() == null) {
            identity.setTraits(new IdentityTraits());
        }
        identity.getTraits()
                .withName(new IdentityName()
                        .withGivenName(body.given_name)
                        .withFamilyName(body.family_name)
                )
                .withBirthdate(LocalDate.parse(body.date_of_birth))
                .withGender(Gender.fromValue(body.gender));

        this.kratos.update(identity);
    }

    @PostMapping("/{householdId}/members/{personId}/avatar")
    @Operation(
summary = "Update avatar for a household member.",
            operationId = "updateMyHouseholdMemberAvatar",
            description = """
This endpoint allows the owner of a household to update the avatar of a specified member. Ensure that the provided householdId and personId match the intended member. This action enhances the digital profile visibility for the user and their household.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void updateAvatar(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @RequestBody UpdateAvatarRequest body,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        Optional<HouseholdMember> match = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(personId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person is not a member of the household."
            );
        }

        HouseholdMember member = match.get();
        Person updatePerson = this.storage.persons().getObject(member.getPersonId());
        if (updatePerson.getMetadataPublic() == null) {
            updatePerson.setMetadataPublic(new IdentityMetadataPublic());
        }

        updatePerson.getMetadataPublic().setPicture(body.uri);
        this.storage.persons().updateObject(personId, updatePerson);

        // identity update the profile
        Identity identity = this.kratos.getIdentity(personId);
        if (identity.getMetadataPublic() == null) {
            identity.setMetadataPublic(new IdentityMetadataPublic());
        }
        identity.getMetadataPublic().setPicture(body.uri);
        this.kratos.update(identity);
    }

    @PutMapping("/{householdId}/members/{personId}")
    @Operation(
summary = "Add yourself or another member to your household",
            operationId = "joinMyHouseholdMember",
            description = """
This endpoint allows the primary contact of a household to add a member. The request must be made by an authenticated user who is authorized as the primary contact. This operation updates the household's member list, ensuring that only authorized users can make changes.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Household joinHousehold(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Person person = this.storage.persons().getObject(oauth.getName());
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person not found"
            );
        }

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        Optional<HouseholdMember> exists = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(oauth.getName())).findFirst();
        if (exists.isPresent()) {
            exists.get().setStartDate(DateTime.now());
            exists.get().setExpirationDate(null);
        } else {
            household.getMembers().add(new HouseholdMember()
                    .withPersonId(person.getId())
                    .withStartDate(DateTime.now())
            );
        }

        this.storage.households().updateObject(householdId, household);

        return household;
    }


    @DeleteMapping("/{householdId}/members/{personId}")
    @Operation(
summary = "Remove a member from your household.",
            operationId = "removeMyHouseholdMember",
            description = """
This endpoint allows you to remove a specified person from your household. This action updates the visibility of the person's association with your household, ensuring accurate representation of its composition. Authentication is required to perform this operation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Household removeHousehold(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Person person = this.storage.persons().getObject(oauth.getName());
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person not found"
            );
        }

        Household household = checkIsHouseholdSupervisor(householdId, oauth);

        if (oauth.getName().equalsIgnoreCase(personId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Can not remove self, other primary member must remove other user."
            );
        }

        Optional<HouseholdMember> exists = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(oauth.getName())).findFirst();
        if (exists.isPresent()) {
            exists.get().setExpirationDate(DateTime.now());
            this.storage.households().updateObject(householdId, household);
        }

        return household;
    }

    private Household checkIsHouseholdSupervisor(String householdId, OAuth2AuthenticatedPrincipal oauth) {
        Household household = this.storage.households().getObject(householdId);
        if (null == household) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Household not found"
            );
        }

        Optional<HouseholdMember> isOwner = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(oauth.getName())
                && x.getPrimary()).findFirst();
        if (isOwner.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Caller is not primary contact of household."
            );
        }
        return household;
    }
}