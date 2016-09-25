package com.ezbudget.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AccessAspect {
	
	private static Logger logger = LoggerFactory.getLogger(AccessAspect.class);

	@Value("${security.enabled}")
	private boolean securityEnabled;
	
	@Pointcut("@annotation(com.ezbudget.annotation.Access)")
    public void annotationPointCutDefinition(){}

    @Pointcut("execution(* *(..))")
    public void atExecution(){}
 
    @Around("annotationPointCutDefinition() && atExecution()")
    public Object printNewLine(ProceedingJoinPoint joinPoint){
    	logger.debug("*****ACCESS CHECK ANNOTATION HIT***");
		Object rtn = null;
    	try {
    		if (securityEnabled){
    			rtn = joinPoint.proceed();
    		}			
		} catch (Throwable e) {
			logger.debug(e.getMessage());
		}
		return rtn;
    }
}
