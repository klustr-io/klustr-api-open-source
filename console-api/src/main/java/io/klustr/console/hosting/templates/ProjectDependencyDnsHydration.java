package io.klustr.console.hosting.templates;

import com.google.common.collect.Lists;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRecord;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRequest;
import io.klustr.schemas.integrations.compute.ComputeIngressDns;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Order(5)
public class ProjectDependencyDnsHydration implements ProjectDependencyHydration {

    private ComputeLoadBalancerResolver lb;
    private ComputeDnsProvider dns;

    public ProjectDependencyDnsHydration(ComputeLoadBalancerResolver lb, ComputeDnsProvider dns) {
        this.lb = lb;
        this.dns = dns;
    }

    @Override
    public void hydrate(Storage storage, String org_id, String projectId, ProjectTemplate template, EnvironmentVars env) {
        if (template.getRequiredFeatures() == null) return;
        if (template.getRequiredFeatures().getDns() == null) return;
        if (template.getRequiredFeatures().getDns().getZone() == null) return;
        if (template.getRequiredFeatures().getDns().getName() == null) return;

        String zone = StringUtils.replace(template.getRequiredFeatures().getDns().getZone(), "{projectId}", projectId);
        zone = StringUtils.replace(zone, "{orgId}", org_id);

        String name = StringUtils.replace(template.getRequiredFeatures().getDns().getName(), "{projectId}", projectId);
        name = StringUtils.replace(name, "{orgId}", org_id);

        ComputeLoadBalancerProvider lb = this.lb.resolve(projectId, org_id);
        Set<String> ips = lb.getPublicIPV4Addresses();
        List<ComputeDnsZoneRecord> records = ips.stream().map(ip -> {
            return new ComputeDnsZoneRecord().withContent(ip).withDisabled(false);
        }).toList();

        // register DNS alias
        this.dns.createRecords(zone, Lists.newArrayList(
                new ComputeDnsZoneRequest()
                        .withName(name)
                        .withType("A")
                        .withTtl(60)
                        .withRecords(records)
        ));
    }
}
