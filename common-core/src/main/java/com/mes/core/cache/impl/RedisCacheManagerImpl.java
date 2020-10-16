package com.mes.core.cache.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mes.core.cache.RedisCacheManager;

/**
 * Redis缓存管理类
 *
 * @author Administrator
 * @date 2018/4/28
 */
@Service("redisCacheManager")
public class RedisCacheManagerImpl implements RedisCacheManager {
	private static final Logger logger = LoggerFactory.getLogger(RedisCacheManagerImpl.class);
	/** Redis模板对象*/
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	public RedisCacheManagerImpl(RedisTemplate<String,Object> redisTemplate){
		this.redisTemplate=redisTemplate;
	}

	/**
	 * 设置字符串
	 * @param key 字符串键
	 * @param value	字符串值
	 * @return <code>true</code>成功 <code>false</code>失败
	 */
	@Override
	public boolean set(String key, String value) {
		boolean flag = false;
		try {
			redisTemplate.opsForValue().set(key, value);
			flag = true;
		} catch (Exception e) {
			logger.error("set(String key, String value) params key:" + key + ",value:" + value + "_" + e.getMessage(), e);
		}

		return flag;
	}

	/**
	 * 设置字符串
	 * @param key 字符串键
	 * @param value	字符串值
	 * @param expiry 失效时间单位毫秒 当<code>expiry</code>大于等于1000毫秒启用失效时间否者永不过期
	 * @return <code>true</code>成功 <code>false</code>失败
	 */
	@Override
	public boolean set(String key, String value, long expiry) {
		boolean flag = false;
		try {
			redisTemplate.opsForValue().set(key, value, expiry, TimeUnit.MILLISECONDS);
			flag = true;
		} catch (Exception e) {
			logger.error("set(String key, String value) params key:" + key + ",value:" + value + "_" + e.getMessage(), e);
		}
		return flag;
	}

	/**
	 * 获取字符串
	 * @param key 字符串键
	 * @return 返回缓存字符串信息
	 */
	@Override
	public String get(String key) {
		return (String) redisTemplate.opsForValue().get(key);
	}

	/**
	 * 设置对象
	 * @param key 字符串键
	 * @param object 对象
	 * @return <code>true</code>成功 <code>false</code>失败
	 */
	@Override
	public boolean setObject(String key, Object object) {
		boolean flag = false;
		try {
			redisTemplate.opsForValue().set(key, object);
			flag = true;
		} catch (Exception e) {
			logger.error("set(String key, String value) params key:" + key + ",value:" + object.toString() + "_" + e.getMessage(), e);
		}

		return flag;
	}

	/**
	 * 设置字符串
	 * @param key 字符串键
	 * @param object 对象
	 * @param expiry 失效时间单位毫秒 当<code>expiry</code>大于等于1000毫秒启用失效时间否者永不过期
	 * @return <code>true</code>成功 <code>false</code>失败
	 */
	@Override
	public boolean setObject(String key, Object object, long expiry) {
		boolean flag = false;
		try {
			redisTemplate.opsForValue().set(key, object, expiry, TimeUnit.MILLISECONDS);
			flag = true;
		} catch (Exception e) {
			logger.error("set(String key, String value) params key:" + key + ",value:" + object.toString() + "_" + e.getMessage(), e);
		}
		return flag;
	}

	/**
	 * 获取对象
	 * @param key 字符串键
	 * @return 返回缓存对象信息
	 */
	@Override
	public Object getObject(String key) {
		return redisTemplate.opsForValue().get(key);
	}
}
