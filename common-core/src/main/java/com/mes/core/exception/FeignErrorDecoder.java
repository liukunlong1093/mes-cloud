package com.mes.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.mes.core.pojos.ServiceResponse;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

/**
 * Feign自定义错误解码处理
 * 这个自定义错误解码处理主要处理Http返回404-1000的请求相应信息
 * 如果没有这个解码器那么 404,500出现异常会立刻进入短路器.导致客户端不知道问题所在.
 * 加上这个只有服务提供者宕机才会进入短路器.
 * @author Administrator
 *
 */
public class FeignErrorDecoder implements ErrorDecoder {
	private Logger logger = LoggerFactory.getLogger("UserErrorDecoder");

	@Override
	public Exception decode(String methodKey, Response response) {
		Exception exception = new RemoteServiceException(15, "远程服务异常");
		try {
			if (400 <= response.status() && response.status() <= 1000) {
				Response.Body body = response.body();
				String errorMsg = Util.toString(body.asReader());
				ServiceResponse<?> serviceResponse = JSON.parseObject(errorMsg, ServiceResponse.class);
				exception = new RemoteServiceException(Integer.parseInt(serviceResponse.getCode()), serviceResponse.getMsg());
			} else {
				logger.error("---UserErrorDecoder decode ERROR:", exception);
			}
		} catch (Exception e) {
			logger.error("---UserErrorDecoder decode ERROR:", exception);
		}
		exception = new HystrixBadRequestException("request exception wrapper", exception);
		return exception;

	}
}
