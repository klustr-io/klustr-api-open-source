package integrations;

import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.utils.U;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class KratosApiTest {

    private KratosApi api = new KratosApi("https://kratos-admin.dev.klustr.io", "kratos");

    @Test
    public void we_can_get_an_identity() {
        Identity identity = api.getIdentity("c33fe483-9e07-49e5-ad68-0d6087b4b11d");

        assertThat(identity).isNotNull();

        System.out.println(U.toJsonPrettyFormat(identity));
    }

    @Test
    public void we_can_list_identity() {
        List<Identity> identities = api.listIdentities();
        assertThat(identities).isNotNull();
        assertThat(identities).isNotEmpty();
        System.out.println(U.toJsonPrettyFormat(identities));
    }

}
