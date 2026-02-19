package io.klustr.permissions.dsl;

import io.klustr.permissions.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public final class OwnerSubjectStage {
    final PermissionProvider provider;
    OwnerSubjectStage(PermissionProvider p) { this.provider = p; }

    public OwnerResourceStage user(OAuth2AuthenticatedPrincipal id)   { return new OwnerResourceStage(provider, SubjectKey.user(id)); }
    public OwnerResourceStage client(String id) { return new OwnerResourceStage(provider, SubjectKey.client(id)); }
    public OwnerResourceStage apiKey(String id) { return new OwnerResourceStage(provider, SubjectKey.apiKey(id)); }
    public OwnerResourceStage app(String id)    { return new OwnerResourceStage(provider, SubjectKey.app(id)); }
    public OwnerResourceStage org(String id)    { return new OwnerResourceStage(provider, SubjectKey.org(id)); }
    public OwnerResourceStage group(String id)  { return new OwnerResourceStage(provider, SubjectKey.group(id)); }
    public OwnerResourceStage anyone()          { return new OwnerResourceStage(provider, SubjectKey.ANYONE); }
}
