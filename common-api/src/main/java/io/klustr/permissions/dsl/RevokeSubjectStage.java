package io.klustr.permissions.dsl;

import io.klustr.permissions.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public final class RevokeSubjectStage {
    final PermissionProvider provider;
    RevokeSubjectStage(PermissionProvider p) { this.provider = p; }

    public RevokeOperationStage user(OAuth2AuthenticatedPrincipal id)   { return new RevokeOperationStage(provider, SubjectKey.user(id)); }
    public RevokeOperationStage userId(String id)   { return new RevokeOperationStage(provider, SubjectKey.userId(id)); }
    public RevokeOperationStage client(String id) { return new RevokeOperationStage(provider, SubjectKey.client(id)); }
    public RevokeOperationStage apiKey(String id) { return new RevokeOperationStage(provider, SubjectKey.apiKey(id)); }
    public RevokeOperationStage app(String id)    { return new RevokeOperationStage(provider, SubjectKey.app(id)); }
    public RevokeOperationStage org(String id)    { return new RevokeOperationStage(provider, SubjectKey.org(id)); }
    public RevokeOperationStage group(String id)  { return new RevokeOperationStage(provider, SubjectKey.group(id)); }
    public RevokeOperationStage anyone()          { return new RevokeOperationStage(provider, SubjectKey.ANYONE); }
}
