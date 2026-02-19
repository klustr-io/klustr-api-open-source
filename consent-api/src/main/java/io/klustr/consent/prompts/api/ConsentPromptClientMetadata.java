package io.klustr.consent.prompts.api;

import java.util.Map;

public class ConsentPromptClientMetadata {
    // client: {
    //   client_name: body.client.client_name,
    //   policy_uri: body.client.policy_uri,
    //   tos_uri: body.client.tos_uri,
    //   client_uri: body.client.client_uri,
    //   logo_uri: body.client.logo_uri,
    //   metadata: body.client.metadata,
    // },

    public String client_name;
    public String policy_uri;
    public String tos_uri;
    public String client_uri;
    public String logo_uri;
    public Map<String, String> metadata;
}
