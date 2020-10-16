package com.mes.core.exhandler;

import com.mes.core.pojos.ServiceResponse;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class HttpErrorHandler implements ErrorController {
	private static final String ERROR_ATTRIBUTE = DefaultErrorAttributes.class.getName() + ".ERROR";
	private final static String ERROR_PATH = "/error";

	/**
	 * Supports other formats like JSON, XML
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public Object error(HttpServletRequest request) {
		Map<String, Object> map = getExceptionAttributes(request, true);
		String message = (String) map.get("message");
		HttpStatus status = getStatus(request);
		String code = String.valueOf(status.value());
		ServiceResponse<Map<String, String>> response = ServiceResponse.handleFail(code, message);
		handleResponse(response);
		return response;
	}

	private void handleResponse(ServiceResponse<Map<String, String>> response) {
		if ("404".equals(response.getCode()) && "No message available".equals(response.getMsg())) {
			response.setMsg("找不到当前请求地址.");
		}
	}

	/**
	 * Returns the path of the error page.
	 *
	 * @return the error path
	 */
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
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

	private Map<String, Object> getExceptionAttributes(HttpServletRequest request, boolean includeStackTrace) {
		Map<String, Object> exceptionAttributes = new LinkedHashMap<String, Object>();
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		addExceptionDetails(exceptionAttributes, requestAttributes, includeStackTrace);
		return exceptionAttributes;
	}

	public Throwable getError(RequestAttributes requestAttributes) {
		Throwable exception = getAttribute(requestAttributes, ERROR_ATTRIBUTE);
		if (exception == null) {
			exception = getAttribute(requestAttributes, "javax.servlet.error.exception");
		}
		return exception;
	}

	@SuppressWarnings("unchecked")
	private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
		return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

	private void addExceptionDetails(Map<String, Object> errorAttributes, RequestAttributes requestAttributes, boolean includeStackTrace) {
		Throwable error = getError(requestAttributes);
		if (error != null) {
			while (error instanceof ServletException && error.getCause() != null) {
				error = ((ServletException) error).getCause();
			}
			errorAttributes.put("exception", error.getClass().getName());
			addExceptionMessage(errorAttributes, error);
			if (includeStackTrace) {
				addStackTrace(errorAttributes, error);
			}
		}
		Object message = getAttribute(requestAttributes, "javax.servlet.error.message");
		if (StringUtils.isEmpty(errorAttributes.get("message"))) {
			errorAttributes.put("message", StringUtils.isEmpty(message) ? "No message available" : message);
		}
	}

	private void addExceptionMessage(Map<String, Object> errorAttributes, Throwable error) {
		Throwable cause = error.getCause();
		if (cause != null) {
			errorAttributes.put("message", cause.getMessage());
		} else {
			errorAttributes.put("message", error.getMessage());
		}
	}

	private void addStackTrace(Map<String, Object> exceptionAttributes, Throwable error) {
		StringWriter stackTrace = new StringWriter();
		error.printStackTrace(new PrintWriter(stackTrace));
		stackTrace.flush();
		exceptionAttributes.put("trace", stackTrace.toString());
	}
}
