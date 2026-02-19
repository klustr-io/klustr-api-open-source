package io.klustr.permissions.models;

import io.klustr.permissions.dsl.Permissions;
import io.klustr.permissions.generator.OplPermissionModelGenerator;
import io.klustr.utils.U;
import org.junit.jupiter.api.Test;

public class OplKetoOryPermissionModelGeneratorTest {

    Permissions perms = new Permissions(new DebugPermissionProvider());

    @Test
    public void examples() {

        // grants bob create project rights in org with id "my_org"
        // naming convention in keto would be `org.projects.create`
        perms.allow().userId("bob").create("projects").in("orgs").withId("my_org");   // grant bob create rights in org (should include read operations)

        // should remove the permissions for bob if they exist to explicit create
        perms.revoke().userId("bob").create("projects").in("orgs").withId("my_org");

        // other situations
        perms.allow().userId("bob").admin("orgs").withId("my_org");
        perms.allow().userId("bob").create("projects").in("orgs").withId("my_org");  // SHOULD BE YES becuase he is ADMIN over my_org

        // this should RESOLVE to 'yes' as bob was given create rights (which includes read)
        perms.can().userId("bob").read("projects").in("orgs").withId("my_org"); // should be YES
        perms.can().userId("bob").delete("projects").in("orgs").withId("my_org"); // should be NO
    }

    @Test
    public void gen_model() throws Exception {
        OplPermissionModelGenerator g = new OplPermissionModelGenerator();
        System.out.println(g.generateFromYaml(U.getResourceAsStream("example.keto.yaml", this)));

    }

    @Test
    public void gen_java() throws Exception {
        String source = JavaPermissionGenerator.generate(U.getResourceAsStream("example.keto.yaml", this));
        System.out.println(source);
    }

    @Test
    public void try_it_out() {
        // IAM.Orgs.Projects.Collections.Permissions.Create();
        // IAM.Orgs.Projects.Permissions.List();
        // IAM.Orgs.Permissions.List()
    }
}
