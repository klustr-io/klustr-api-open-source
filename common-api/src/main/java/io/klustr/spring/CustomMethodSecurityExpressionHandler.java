package io.klustr.spring;

import io.klustr.permissions.PermissionProvider;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final PermissionProvider permissionProvider;

    @Autowired
    public CustomMethodSecurityExpressionHandler(PermissionProvider permissionProvider) {
        this.permissionProvider = permissionProvider;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        return new CustomSecurityExpressionRoot(authentication, permissionProvider);
    }

    @Override
    public EvaluationContext createEvaluationContext(Supplier<Authentication> authentication, MethodInvocation mi) {
        return createEvaluationContext(authentication.get(), mi);
    }
}
