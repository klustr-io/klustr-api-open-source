package io.klustr.permissions.dsl;

import io.klustr.permissions.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public final class CheckSubjectStage {
    final PermissionProvider provider;
    CheckSubjectStage(PermissionProvider p) { this.provider = p; }

    public CheckOperationStage user(OAuth2AuthenticatedPrincipal id)   { return new CheckOperationStage(provider, SubjectKey.user(id)); }
    public CheckOperationStage userId(String id)   { return new CheckOperationStage(provider, SubjectKey.userId(id)); }

    public CheckOperationStage client(String id) { return new CheckOperationStage(provider, SubjectKey.client(id)); }
    public CheckOperationStage apiKey(String id) { return new CheckOperationStage(provider, SubjectKey.apiKey(id)); }
    public CheckOperationStage app(String id)    { return new CheckOperationStage(provider, SubjectKey.app(id)); }
    public CheckOperationStage org(String id)    { return new CheckOperationStage(provider, SubjectKey.org(id)); }
    public CheckOperationStage group(String id)  { return new CheckOperationStage(provider, SubjectKey.group(id)); }
    public CheckOperationStage anyone()          { return new CheckOperationStage(provider, SubjectKey.ANYONE); }
}
