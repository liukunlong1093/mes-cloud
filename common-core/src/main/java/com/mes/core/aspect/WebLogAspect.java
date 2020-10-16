package com.mes.core.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Web层日志切面
 * @author liukl
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
	private ThreadLocal<Long> startTime = new ThreadLocal<>();
	@Pointcut("execution(public * com.mes.*.web..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint){
		startTime.set(System.currentTimeMillis());

		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Signature signature = joinPoint.getSignature();  
		MethodSignature methodSignature = (MethodSignature) signature;  
		String[] parameterNames = methodSignature.getParameterNames();  
		// 记录下请求内容
		logger.info("------------------------------------------------------------------------------------------------------------");
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : {}", request.getMethod());
		logger.info("IP : {}", request.getRemoteAddr());
		logger.info("CLASS_METHOD : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
		logger.info("PARAMETER_NAMES : {}",Arrays.toString(parameterNames));
		logger.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));

	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret){
		// 处理完请求，返回内容
		logger.info("RESPONSE : {}", ret);
			logger.info("SPEND TIME : {}", (System.currentTimeMillis() - startTime.get()));
			startTime.remove();
	}

}
