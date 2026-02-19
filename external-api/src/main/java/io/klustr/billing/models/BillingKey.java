package io.klustr.billing.models;

public class BillingKey {
    private String orgId;
    private String projectId;

    @Deprecated
    public BillingKey() {}

    public BillingKey(String orgId, String projectId) {
        this.setOrgId(orgId);
        this.setProjectId(projectId);
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
