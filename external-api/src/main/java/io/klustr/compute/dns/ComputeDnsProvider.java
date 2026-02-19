package io.klustr.compute.dns;

import io.klustr.schemas.integrations.compute.ComputeDnsZone;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRequest;
import io.klustr.schemas.integrations.pdns.PdnsZone;
import org.springframework.boot.actuate.health.Health;

import java.util.List;

public interface ComputeDnsProvider {

    Health health();

    List<ComputeDnsZone> listZones();

    ComputeDnsZone createZone(String zone);

    PdnsZone listRecordsInZone(String zone);

    void createRecords(String zone, List<ComputeDnsZoneRequest> records);

    void deleteRecords(String zone, List<ComputeDnsZoneRequest> records);
}
