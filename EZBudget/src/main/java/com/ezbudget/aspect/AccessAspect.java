package com.ezbudget.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AccessAspect {

	/*
	 * @Pointcut(value = "execution(public * *(..))") public void
	 * anyPublicMethod() {
	 * System.out.println("**********ACCESS ANNOTATION HIT**************"); }
	 */

	@Before("@target(com.ezbudget.annotation.Access)")
	public void accesCheck() throws Throwable {
		System.out.println("**********ACCESS ANNOTATION HIT**************");
	}

}
