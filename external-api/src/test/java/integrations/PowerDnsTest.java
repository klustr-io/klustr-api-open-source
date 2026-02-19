package integrations;

import com.google.common.collect.Lists;
import io.klustr.compute.dns.impl.PowerDnsApiComputeDnsProvider;
import io.klustr.schemas.integrations.compute.ComputeDnsZone;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRecord;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRequest;
import io.klustr.schemas.integrations.pdns.PdnsZone;
import io.klustr.utils.Json;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PowerDnsTest {

    PowerDnsApiComputeDnsProvider dns = new PowerDnsApiComputeDnsProvider("http://192.168.0.210:7000", "mykey");

    @Test
    public void we_can_list_zones() throws Exception {
        List<ComputeDnsZone> zones = dns.listZones();
        assertThat(zones.size()).isGreaterThan(0);
        System.out.println(Json.toJsonPrettyFormat(zones));
    }

    @Test
    public void we_can_create_zone() throws Exception {
        ComputeDnsZone zone = dns.createZone("my-org.klustr.io.");
        assertThat(zone).isNotNull();
        System.out.println(zone);
    }

    @Test
    public void we_can_delete_zone() throws Exception {
        dns.deleteZone("my-org.klustr.io.");
    }

    @Test
    public void we_can_list_records_in_zone() throws Exception {
        PdnsZone zone = dns.listRecordsInZone("my-org.klustr.io.");
        System.out.println(Json.toJsonPrettyFormat(zone));

        PdnsZone zone1 = dns.listRecordsInZone("dev.klustr.io.");
        System.out.println(Json.toJsonPrettyFormat(zone1));
    }

    @Test
    public void we_can_create_new_record_in_zone() throws Exception {
        dns.createRecords("my-org.klustr.io.", Lists.newArrayList(new ComputeDnsZoneRequest()
                .withName("project1.my-org.klustr.io.")
                .withTtl(30)
                .withType("A")
                .withRecords(Lists.newArrayList(
                        new ComputeDnsZoneRecord().withContent("127.0.0.1").withDisabled(false),
                        new ComputeDnsZoneRecord().withContent("192.128.0.1").withDisabled(false)
                ))
        ));
    }

    @Test
    public void we_can_delete_record_in_zone() throws Exception {
        dns.deleteRecords("my-org.klustr.io.", Lists.newArrayList(new ComputeDnsZoneRequest()
                .withName("project1.my-org.klustr.io.")
                .withTtl(30)
                .withType("A")
                .withRecords(Lists.newArrayList(
                        new ComputeDnsZoneRecord().withContent("127.0.0.1").withDisabled(false)
                ))
        ));
    }
}
