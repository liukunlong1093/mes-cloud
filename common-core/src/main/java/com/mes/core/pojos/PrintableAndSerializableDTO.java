package com.mes.core.pojos;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;

/**
 * 覆盖toString默认打印及序列化DTO
 * @author Administrator
 * @date 2018/4/28
 */
public class PrintableAndSerializableDTO implements Serializable{

	private static final long serialVersionUID = 1260343681044206085L;

	@Override
	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		} catch (Throwable e) {
			return getClass().getName();
		}
	}
}
