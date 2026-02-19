package io.klustr.integrations.growthbook.responses;

import io.klustr.integrations.growthbook.entities.GrowthBookProject;

import java.util.ArrayList;
import java.util.List;

public class GrowthbookProjectPaginationResult extends GrowthBookPaginationResult {

    public List<GrowthBookProject> projects = new ArrayList<>();
}
