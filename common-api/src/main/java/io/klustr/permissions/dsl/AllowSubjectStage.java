package io.klustr.permissions.dsl;

import io.klustr.permissions.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public final class AllowSubjectStage {
    final PermissionProvider provider;
    AllowSubjectStage(PermissionProvider p) { this.provider = p; }

    public AllowOperationStage user(OAuth2AuthenticatedPrincipal id)   { return new AllowOperationStage(provider, SubjectKey.user(id)); }
    public AllowOperationStage userId(String id)   { return new AllowOperationStage(provider, SubjectKey.userId(id)); }
    public AllowOperationStage client(String id) { return new AllowOperationStage(provider, SubjectKey.client(id)); }
    public AllowOperationStage apiKey(String id) { return new AllowOperationStage(provider, SubjectKey.apiKey(id)); }
    public AllowOperationStage app(String id)    { return new AllowOperationStage(provider, SubjectKey.app(id)); }
    public AllowOperationStage org(String id)    { return new AllowOperationStage(provider, SubjectKey.org(id)); }
    public AllowOperationStage group(String id)  { return new AllowOperationStage(provider, SubjectKey.group(id)); }
    public AllowOperationStage anyone()          { return new AllowOperationStage(provider, SubjectKey.ANYONE); }
}
