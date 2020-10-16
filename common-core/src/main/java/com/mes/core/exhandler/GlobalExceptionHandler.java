package com.mes.core.exhandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mes.core.exception.ApiRuleException;
import com.mes.core.exception.RemoteServiceException;
import com.mes.core.exception.ServiceException;
import com.mes.core.pojos.ServiceResponse;
import com.netflix.hystrix.exception.HystrixBadRequestException;
/**
 * 全局异常回调
 *
 * @author Administrator
 * @date 2018/4/28
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger("GlobalExceptionHandler");

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {
		logger.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
		logger.error("DefaultException" + e.getMessage(), e);
		HttpStatus status = getStatus(req);
		String code = String.valueOf(status.value());
		ServiceResponse<Map<String, String>> response = ServiceResponse.handleFail(code, getErrorMessage(e));
		res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return response;
	}

	/**
	 * 服务端校验错误回调
	 * @param req	请求对象
	 * @param res   响应对象
	 * @param e		异常对象
	 * @return 		服务响应对象
	 * @throws Exception
	 */
	@ExceptionHandler(value = ApiRuleException.class)
	@ResponseBody
	public Object apiRuleErrorHandler(HttpServletRequest req, HttpServletResponse res, ApiRuleException e) throws Exception {
		ServiceResponse<Map<String, String>> response = ServiceResponse.handleFail(String.valueOf(e.getExceptionEnums().getCode()), e.getMessage());
		res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return response;
	}
	
	/**
	 * 远程 服务调用异常
	 * @param req 	请求对象
	 * @param res 	响应对象
	 * @param e		异常对象
	 * @return		服务响应对象
	 * @throws Exception
	 */
	@ExceptionHandler(value = HystrixBadRequestException.class)
	@ResponseBody
	public Object RemoteServiceErrorHandler(HttpServletRequest req, HttpServletResponse res, RemoteServiceException e) throws Exception {
		logger.error("---ServerException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
		ServiceResponse<Map<String, String>> response = ServiceResponse.handleFail(String.valueOf(e.getCode()), e.getMessage());
		res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return response;
	}
	/**
	 * 服务异常
	 * @param req	请求对象
	 * @param res   响应对象
	 * @param e		异常对象
	 * @return 		服务响应对象
	 * @throws Exception
	 */
	@ExceptionHandler(value = ServiceException.class)
	@ResponseBody
	public Object serviceErrorHandler(HttpServletRequest req, HttpServletResponse res, ServiceException e) throws Exception {
		ServiceResponse<Map<String, String>> response = ServiceResponse.handleFail(String.valueOf(e.getExceptionEnums().getCode()), e.getMessage());
		res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return response;
	}

	/**
	 * 异常信息转换
	 * @param ex 异常对象
	 * @return 异常信息
	 */
	private String getErrorMessage(Exception ex) {
		if (ex instanceof ArithmeticException) {
			return "系统异常：计算错误";
		}
		if (ex instanceof NullPointerException) {
			return "系统异常：空指针异常.";
		}
		if (ex instanceof ClassCastException) {
			return "系统异常：类型转换错误";
		}
		if (ex instanceof NegativeArraySizeException) {
			return "系统异常：集合负数";
		}
		if (ex instanceof ArrayIndexOutOfBoundsException) {
			return "系统异常：集合超出范围";
		}
		if (ex instanceof FileNotFoundException) {
			return "系统异常：文件未找到";
		}
		if (ex instanceof NumberFormatException) {
			return "系统异常：输入数字错误";
		}
		if (ex instanceof SQLException) {
			return "系统异常：数据库异常";
		}
		if (ex instanceof IOException) {
			return "系统异常：文件读写错误";
		}
		if (ex instanceof NoSuchMethodException) {
			return "系统异常：方法找不到";
		}

		if (ex instanceof MissingServletRequestParameterException) {
			return "系统异常：接口请求参数为空";
		}

		if (ex instanceof HttpMessageNotReadableException) {
			return "系统异常：接口请求参数转换对象异常";
		}
		return ex.getMessage();
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		} catch (Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}
