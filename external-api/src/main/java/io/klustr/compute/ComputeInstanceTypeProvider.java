package io.klustr.compute;

import io.klustr.schemas.integrations.compute.ComputeInstanceType;
import io.klustr.schemas.integrations.compute.ComputeInstanceType;

import java.util.List;
import java.util.Optional;

public interface ComputeInstanceTypeProvider {

    public List<ComputeInstanceType> getInstanceTypes();

    public Optional<ComputeInstanceType> getInstanceTypeByName(String name);
}
