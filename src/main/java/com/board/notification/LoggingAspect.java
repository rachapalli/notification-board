package com.board.notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import com.board.notification.exception.DataAccessException;

@Configuration
@Aspect
public class LoggingAspect {
	private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

	@Pointcut(value = "execution(* com.board.notification.controller.*.*(..)) "
			+ "|| execution(* com.board.notification.service.*.*(..)) "
			+ "|| execution(* com.board.notification.dao.*.*(..)) "
			+ "|| execution(* com.board.notification.utils.*.*(..))"
			+ "|| execution(* com.board.notification.exception.*.*(..))"
			+ "|| execution(* com.board.notification.config.*.*(..))")
	public void myPointCut() {

	}

	@Around("myPointCut()")
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		try {
			MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

			// Get intercepted method details
			String className = methodSignature.getDeclaringType().getSimpleName();
			String methodName = methodSignature.getName();

			LOGGER.info("Starts execution of {} method in {}...", methodName, className);
			Object object = proceedingJoinPoint.proceed();
			LOGGER.info("Ends execution of {} method in {}...", methodName, className);
			return object;

		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		}
	}

}
