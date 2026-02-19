package io.klustr.integrations.kong.models;

import java.util.ArrayList;
import java.util.List;

public class KongConsumerRequest {
    public String username;

    public String custom_id;

    public List<String> tags = new ArrayList<>();
}
