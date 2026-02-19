package io.klustr.console.org;

import com.google.common.collect.Sets;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.projects.Project;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * When using PPIDs, the client does not know about the user's actual identifier,
 * which may be an email address, employee number, social security number, or other
 * ID that contains Personally Identifiable Information (PII). Even when the user ID
 * does not include sensitive information, PPIDs are helpful in increasing privacy
 * by creating a unique ID for each client. As a consequence, different clients
 * are not able to collude or share information about users. The combination of
 * user and client creates a unique identifier which represents the user
 * for that particular client.
 *
 * PPIDs using sector identifiers
 *
 * There are times when multiple clients are working together in legitimate ways. This
 * often comes up when two clients need to access or store user preferences, products,
 * shopping carts, medical records, etc. In such cases, clients may be placed in the
 * same group or "sector". This will allow clients within this sector to obtain the
 * same PPID for a user.
 *
 * Using sectors, the pairing is not client-based but rather sector-based.
 */
@RestController
@Component
@RequestMapping("/directory/public/{organizationId}")
@Tag(name = "Org Management", description = "Organization management functions.")
public class OrgSectorsService {

    private final Storage storage;

    public OrgSectorsService(Storage storage) {
        this.storage = storage;
    }

    @GetMapping("/sectors")
    @Operation(
operationId = "adminGetOidcSectorIdentifier",
            summary = "Retrieve OIDC Sector Identifier for organization.",
            description = """
This endpoint provides the OIDC Sector Identifier used for calculating Pseudonymous Identifiers. It ensures that clients within the same sector can access the same unique identifier for users, enhancing privacy and data protection. Use this to manage user identifiers securely within your organization.
""",
            parameters = {
                    @Parameter(name = "organizationId", required = true, description = "The organization ID to fetch.")
            },
            security = @SecurityRequirement(name = "API_KEY")
)
    public Set<String> getSectorIdentifer(@PathVariable("organizationId") String organizationId) {
        DbQuery fq = Db.query("org_id").eq(organizationId);
        DocumentResult<Project> result = this.storage.projects().insecureQuery(fq, Pagination.all());
        Set<String> urls = Sets.newHashSet();
        result.docs.forEach(x -> {
            if (x.getClients() == null) return;
            x.getClients().forEach(client -> {
                if (client.getAuthorizedRedirectUris() == null) {
                    return;
                }
                urls.addAll(client.getAuthorizedRedirectUris());
            });
        });
        return urls;
    }
}
