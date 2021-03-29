package dmit2015.security;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.util.logging.Logger;

public class TodoItemSecurityInterceptor {

    @Inject
    private Logger _logger;

    @Inject
    private SecurityContext _securityContext;

    @AroundInvoke
    public Object verifyAccess(InvocationContext ic) throws Exception {
        String methodName = ic.getMethod().getName();
        _logger.info("Entering method " + methodName);
        // Only authenticated users can access the methods
        if (_securityContext.getCallerPrincipal() == null) {
            throw new RuntimeException("Access denied!. Only authenticated users have permission to execute this method.");
        }
        return ic.proceed();
    }
}
