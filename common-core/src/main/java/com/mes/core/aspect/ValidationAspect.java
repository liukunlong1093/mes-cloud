package com.mes.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.mes.core.utils.RequestCheckUtils;

/**
 * 数据校验
 * @author liukl
 */
@Aspect
@Order(6)
@Component
public class ValidationAspect {
	private static final Logger logger = LoggerFactory.getLogger(ValidationAspect.class);

	@Pointcut("execution(public * com.mes.*.web.*.*(..))")
	public void validation() {
	}

	@Before("validation()")
	public void doBefore(JoinPoint joinPoint){
		logger.info("校验参数开始 ");
		// 接收到请求，记录请求内容
		String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		if (RequestCheckUtils.isContainsConfig(methodName)) {
			RequestCheckUtils.vaildator(methodName, args);
		}
		logger.info("校验参数结束");
	}
}
