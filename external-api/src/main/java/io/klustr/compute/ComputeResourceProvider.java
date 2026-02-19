package io.klustr.compute;

import org.springframework.boot.actuate.health.Health;

public interface ComputeResourceProvider {

    Health health();
}
