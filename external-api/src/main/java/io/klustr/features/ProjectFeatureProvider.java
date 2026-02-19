package io.klustr.features;

import io.klustr.schemas.console.features.AbstractProjectFeature;

import java.util.List;

public interface ProjectFeatureProvider {

    List<AbstractProjectFeature> getAvailableFeatures(String org_id);
}
