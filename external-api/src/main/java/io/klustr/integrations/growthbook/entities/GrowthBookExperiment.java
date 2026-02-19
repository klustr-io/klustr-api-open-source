package io.klustr.integrations.growthbook.entities;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;

public class GrowthBookExperiment {
    public String id;
    public String name;
    public String project;
    public String project_name;
    public String description;
    public List<String> tags = Lists.newArrayList();
    public String owner;
    public DateTime dateCreated;
    public DateTime dateUpdated;
    public String status;
}
