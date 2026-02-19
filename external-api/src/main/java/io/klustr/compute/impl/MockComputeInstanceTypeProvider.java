package io.klustr.compute.impl;

import io.klustr.compute.ComputeInstanceTypeProvider;
import io.klustr.schemas.integrations.compute.ComputeInstanceType;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class MockComputeInstanceTypeProvider implements ComputeInstanceTypeProvider {

    private final List<ComputeInstanceType> machines;

    public MockComputeInstanceTypeProvider() {
        String json = U.getResourceAsString("machine_types.json", this);
        this.machines = Collections.unmodifiableList(Json.parseArray(json, ComputeInstanceType.class));
    }


    @Override
    public List<ComputeInstanceType> getInstanceTypes() {
        return this.machines;
    }

    @Override
    public Optional<ComputeInstanceType> getInstanceTypeByName(String name) {
        return this.machines.stream().filter(x ->x.getName().equalsIgnoreCase(name)).findFirst();
    }
}
