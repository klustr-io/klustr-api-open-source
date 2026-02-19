package io.klustr.spring;


import io.klustr.permissions.Target;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Scope;
import io.klustr.permissions.SubjectKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

public class CustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private static final Logger log = LoggerFactory.getLogger(CustomSecurityExpressionRoot.class);

    private final PermissionProvider permissionProvider;
    private Object filterObject;
    private Object returnObject;

    public CustomSecurityExpressionRoot(Authentication authentication, PermissionProvider permissionProvider) {
        super(authentication);
        this.permissionProvider = permissionProvider;
    }

    /**
     * Uses KetoPermissionProvider to check ACL.
     */
    public boolean hasAcl(OAuth2IntrospectionAuthenticatedPrincipal principal, String objectId, String namespace, String relation) {

        String prod = System.getenv("PROD");
        if (StringUtils.isBlank(prod)) {
            System.out.println("Spring disabled security ACL checks, no 'PROD' environment set.");
            log.warn("Spring disabled security ACL checks, no 'PROD' environment set.");
            return true;
        }
        if (prod.equalsIgnoreCase("false")) {
            System.out.println("Spring disabled security ACL checks, 'PROD' environment set to false.");
            log.warn("Spring disabled security ACL checks, no 'PROD' environment set to false.");
            return true;
        }

        SubjectKey subjectKey = SubjectKey.user(principal);
        Scope scope = Scope.attribute(namespace, relation);
        Target objId = Target.of(objectId);
        if (log.isDebugEnabled()) {
            log.debug("Check permissions -> Subject: {}, Scope: {}, ObjectId: {}", subjectKey, scope, objId);
        }
        return permissionProvider.check(subjectKey, scope, objId);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
