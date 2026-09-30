package com.wipro.aop;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class UserAspect {
	
	@Before("execution( * com.wipro.controller.UserController.get*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		log.info("Executing aspect method...");
		log.info("JoinPint method is "+ joinPoint.getSignature().getName());
	}
	
	@After("execution( * com.wipro.controller.UserController.get*(..))")
	public void afterAdvice(JoinPoint joinPoint) {
		log.info("Executing aspect method...");
		log.info("JoinPint method is "+ joinPoint.getSignature().getName());
	}
	
//	@Around("execution( * com.wipro.controller.UserController.get*(..))")
//	public Object beforeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
//		log.info("Before joinPoint method, "+ joinPoint.getSignature().getName());
//		//explicitly invoke JoinPoint method
//
//		Object object = joinPoint.proceed();
//		
//		
//		log.info("After joinPoint method, "+ joinPoint.getSignature().getName());
//		
//		return object;
//	}
	
	
	@Pointcut("execution(* com.wipro.controller.UserController.*(..))")
	public void applicationPackagePointcut() {
	}
	@Around("applicationPackagePointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			if (log.isDebugEnabled()) {
				log.debug("Entering method : {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
			}
			try {
				//explicitly invoke joinpoint method
				Object object = joinPoint.proceed();
				if (log.isDebugEnabled()) {					
					log.debug("Exiting method: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
							joinPoint.getSignature().getName(), object);
				}
				return object;
			} catch (Exception e) {
				log.error("Error in {}.{}()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
				log.error(e.getMessage());
				throw e;
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	@AfterThrowing(pointcut = "applicationPackagePointcut()", throwing="ex")
	public void logAfterThworing(JoinPoint joinPoint, Exception ex) throws Throwable {
		log.error("Error in {}.{}()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
		log.error("Exception caught: " + ex.getMessage());
	}
}
