package com.wipro.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AttendanceAspect {
	@Before("execution( * com.wipro.controller.AttendanceController.get*(..))")
	public void beforeAdviceAttendance(JoinPoint joinPoint) {
		log.info("Executing attendance methods...");
		log.info("JoinPint method is "+ joinPoint.getSignature().getName());
	}
	
	@After("execution( * com.wipro.controller.AttendanceController.get*(..))")
	public void afterAdviceAttendance(JoinPoint joinPoint) {
		log.info("Executed the attendance method...");
		log.info("JoinPint method is "+ joinPoint.getSignature().getName());
	}
	
	@Pointcut("execution(* com.wipro.controller.UserController.*(..))")
	public void applicationPackagePointcut() {
	}
	@Around("applicationPackagePointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			long startTime = System.currentTimeMillis();
			Object object = joinPoint.proceed();
			long endTime = System.currentTimeMillis();
			
			log.info("Time taken by " + joinPoint.getSignature().getName() + " is " + (endTime - startTime) + " ms");
			return object;
		}catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
