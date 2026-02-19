package io.klustr.integrations.growthbook.responses;

import com.google.common.collect.Lists;
import io.klustr.integrations.growthbook.entities.GrowthBookExperiment;

import java.util.List;

public class GrowthBookExperimentPaginationResult extends GrowthBookPaginationResult {

    public List<GrowthBookExperiment> experiments = Lists.newArrayList();
}
