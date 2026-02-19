package io.klustr.compute;


import io.klustr.features.MockProjectFeatureRespository;
import io.klustr.schemas.console.features.AbstractProjectFeature;
import io.klustr.utils.Json;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MockProjectResourceRepositoryTest {

    @Test
    public void x() {
        List<AbstractProjectFeature> features =
                new MockProjectFeatureRespository().getAvailableFeatures("foo");
        System.out.println(Json.toJsonPrettyFormat(features));
    }
}
