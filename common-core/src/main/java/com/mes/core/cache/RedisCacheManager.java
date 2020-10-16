package com.mes.core.cache;

/**
 * Redis缓存管理类
 *
 * @author Administrator
 * @date 2018/4/28
 */
public interface RedisCacheManager {
	/**
	 * 设置字符串
	 * @param key 字符串键
	 * @param value	字符串值
	 * @return <code>true</code>成功 <code>false</code>失败
	 */
	boolean set(String key, String value);

	/**
	 * 设置字符串
	 * @param key 字符串键
	 * @param value	字符串值
	 * @param expiry 失效时间单位毫秒 当<code>expiry</code>大于等于1000毫秒启用失效时间否者永不过期
	 * @return <code>true</code>成功 <code>false</code>失败
	 */
	boolean set(String key, String value, long expiry);

	/**
	 * 获取字符串
	 * @param key 字符串键
	 * @return 返回缓存字符串信息
	 */
	String get(String key);

	/**
	 * 设置对象
	 * @param key 字符串键
	 * @param object 对象
	 * @return <code>true</code>成功 <code>false</code>失败
	 */
	boolean setObject(String key, Object object);

	/**
	 * 设置字符串
	 * @param key 字符串键
	 * @param object 对象
	 * @param expiry 失效时间单位毫秒 当<code>expiry</code>大于等于1000毫秒启用失效时间否者永不过期
	 * @return <code>true</code>成功 <code>false</code>失败
	 */
	boolean setObject(String key, Object object, long expiry);

	/**
	 * 获取对象
	 * @param key 字符串键
	 * @return 返回缓存对象信息
	 */
	Object getObject(String key);
}
