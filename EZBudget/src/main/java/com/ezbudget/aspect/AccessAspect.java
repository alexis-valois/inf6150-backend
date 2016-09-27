package com.ezbudget.aspect;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ezbudget.annotation.Access;
import com.ezbudget.entity.EBAuthority;
import com.ezbudget.enumtype.RoleType;
import com.ezbudget.exception.UserPrivilegesException;
import com.ezbudget.service.AuthenticationService;

@Component
@Aspect
public class AccessAspect {

	private static Logger logger = LoggerFactory.getLogger(AccessAspect.class);

	@Autowired
	AuthenticationService authService;

	@Value("${security.enabled}")
	private boolean securityEnabled;

	@Pointcut("@annotation(access)")
	public void annotationPointCutDefinition(Access access) {
	}

	@Pointcut("execution(* *(..))")
	public void atExecution() {
	}

	@Around("annotationPointCutDefinition(access) && atExecution() && args(*,sessionToken,..)")
	public Object accessCheck(ProceedingJoinPoint joinPoint, Access access, String sessionToken) throws Throwable {
		Object rtn = null;
			if (securityEnabled) {
				logger.info("Security is enabled");
				RoleType requiredRole = access.role();

				Set<EBAuthority> roles = null;
				try {
					roles = authService.getUserRoles(sessionToken);
				} catch (Exception e) {
					handleUnauthorizedAccess();
					logger.error(e.getMessage());
				}

				boolean containsRole = false;
				for (EBAuthority authority : roles) {
					if (authority.getAuthority() == requiredRole) {
						containsRole = true;
						break;
					}
				}

				logger.info("Access annotated method launched with role : " + requiredRole + ", sessionToken = "
						+ sessionToken);
				if ((containsRole)) {
					rtn = joinPoint.proceed();
				} else {
					handleUnauthorizedAccess();
				}

			}
		return rtn;
	}

	private void handleUnauthorizedAccess() {
		throw new UserPrivilegesException("User doesn't have the required role.",
				new RuntimeException("User doesn't have the required role"));
	}
}
