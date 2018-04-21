package com.hotent.makshi.utils;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

/**
 * 将src对象值赋给des对象
 * 
 * @author cesc
 *
 */
public class BeanUtils {
	private final static Logger log = Logger.getLogger(BeanUtils.class);

	public static void copyPropertysWithoutNull(Object des, Object src) {
		Class<?> clazz = des.getClass();
		Field[] srcfields = src.getClass().getDeclaredFields();
		for (Field field : srcfields) {
			if (field.getName().equals("serialVersionUID"))
				continue;
			Field f;
			try {
				f = clazz.getDeclaredField(field.getName());
				f.setAccessible(true);
				field.setAccessible(true);
				Object obj = field.get(src);
				if (obj != null)
					f.set(des, obj);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				log.error("错误信息", e);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				log.error("错误信息", e);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				log.error("错误信息", e);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				log.error("错误信息", e);
			}
		}
	}
}