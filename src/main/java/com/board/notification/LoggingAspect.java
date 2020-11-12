package com.board.notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import com.board.notification.exception.DataAccessException;

@Configuration
@Aspect
public class LoggingAspect {
	private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

	@Around("execution(* *(..)) && " + "@annotation(Loggable)")
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		try {
			MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
			// Get intercepted method details
			String className = methodSignature.getDeclaringType().getSimpleName();
			String methodName = methodSignature.getName();

			LOGGER.info("Inside {} method in {}...", methodName, className);
			Object result = proceedingJoinPoint.proceed();
			return result;

		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		}
	}

}
