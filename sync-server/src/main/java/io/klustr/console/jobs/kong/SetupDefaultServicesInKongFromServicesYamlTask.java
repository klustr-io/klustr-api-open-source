package io.klustr.console.jobs.kong;

import io.klustr.setup.SetupTask;
import io.klustr.setup.SetupTaskRunnable;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.kong.interfaces.ServiceRegistry;
import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.services.ApiServiceSpecification;
import io.klustr.utils.U;
import io.klustr.utils.Yaml;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@SetupTask
public class SetupDefaultServicesInKongFromServicesYamlTask implements SetupTaskRunnable {

    private final Storage storage;
    private final ServiceRegistry kong;

    public SetupDefaultServicesInKongFromServicesYamlTask(Storage storage, ServiceRegistry kong) {
        this.storage = storage;
        this.kong = kong;
    }

    public void setup() {
        String yaml = U.getResourceAsString("setup/services.yaml", this);
        List<ApiServiceSpecification> specs = Yaml.fromYamlGenericList(yaml, ApiServiceSpecification.class);
        specs.forEach(spec -> {
            Optional<ApiServiceSpecification> match = this.storage.api_catalog().tryGetObject(spec.getId());
            if (match.isEmpty()) {
                this.kong.create(spec);
                this.storage.api_catalog().insertObject(spec.getId(), spec);
            } else {
                EntityReference ref = this.kong.update(spec);
                this.storage.api_catalog().updateObject(spec.getId(), spec.withExternalId(ref.getId()));
            }
        });
    }

    @Override
    public boolean repeat() {
        return false;
    }
}
