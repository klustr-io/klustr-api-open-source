package integrations;

import io.klustr.integrations.ory.KetoReadApi;
import io.klustr.schemas.integrations.ory.Relationship;
import io.klustr.integrations.ory.KetoAdminApi;
import io.klustr.utils.Json;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class KetoAdminApiTest {

    @Test
    public void we_can_make_projects() {
        KetoReadApi read = new KetoReadApi("https://keto.dev.klustr.io");

        String subjectId = "user:621b3e3c-2ae7-494b-ad83-e9e37618bfd4";
        // KetoReadApi.RelationshipResponse orgs = read.getSubjectRelationships("orgs", subjectId, 100);

        KetoReadApi.RelationshipResponse orgs = read.getSubjectRelationshipsToObject("orgs", "klustr.io", subjectId, 100);
        System.out.println(Json.toJsonPrettyFormat(orgs));
        // roles

        Set<String> roles = Sets.newHashSet();
        orgs.relation_tuples.stream().forEach(x -> {
            if (x.getRelation().startsWith("roles_")) {
                String role = x.getRelation().replaceAll("roles_", "");
                roles.add(role);
            }
        });
    }
}
