/**   
 * @Title: ArrayUtil.java 
 * @Package net.makshi.data.utils 
 * @Description: TODO
 * @author Sherwin  
 * @date 2013-2-28 下午3:34:32 
 * @version V1.0
 * @Copyright (c)2012  MaiShi technology Co.Ltd. 
 */
package com.hotent.platform.webservice.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

/**
 * @ClassName: ArrayUtil
 * @Description: TODO
 */
public class ArrayUtil {

	@Deprecated
	public static List<Object> getFieldList(List<? extends Object> list, String methodName) {
		List<Object> tmpList = new ArrayList<Object>(list.size());
		if (null == list || list.size() == 0) {
			return tmpList;
		}
		Method method = null;
		try {
			method = list.get(0).getClass().getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("没有这个方法：" + methodName);
		}
		for (int i = 0; i < list.size(); i++) {
			Object val = null;
			try {
				val = method.invoke(list.get(i), null);
			} catch (Exception e) {
			}

			tmpList.add(val);
		}
		return tmpList;
	}

	public static List<Object> getMapProperty(List<? extends Map> list, String property) {
		List<Object> tmpList = new ArrayList<Object>(list.size());
		if (null == list || list.size() == 0) {
			return tmpList;
		}
		for (int i = 0; i < list.size(); i++) {
			Map m = list.get(i);
			tmpList.add(m.get(property));
		}
		return tmpList;
	}

	/**
	 * 
	 * @Title: sortList
	 * @Description: List<Object>内部排序，object可以是Map或者domain对象
	 * @param list
	 * @param key
	 *            需要排序的key
	 * @param order
	 *            0-升序，1-倒序
	 */
	public static void sortList(List<? extends Object> list, final String key, final Locale local, final int order) {

		Collections.sort(list, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				Object d0 = null;
				Object d1 = null;
				if (o1 instanceof Map) {
					d0 = ((Map) o1).get(key);
					d1 = ((Map) o2).get(key);
				} else {
					Method method = null;
					try {
						String methodName = key.substring(0, 1).toUpperCase() + key.substring(1);
						method = o1.getClass().getMethod("get" + methodName);
					} catch (NoSuchMethodException e) {
						throw new RuntimeException("没有这个属性或者属性的get方法：" + e);
					}
					try {
						d0 = method.invoke(o1);
						d1 = method.invoke(o2);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new RuntimeException("调用方法出错：" + key);
					}
				}
				if (d0 == null && d1 == null) {
					return 0;
				}
				if (d0 != null && d1 == null) {
					return -1;
				}
				if (d0 == null && d1 != null) {
					return 1;
				}

				if (d0.equals(d1)) {
					return 0;
				}
				int rst = 0;
				if (d0 instanceof String) {
					if (local != null) {
						rst = Collator.getInstance(local).compare(d0, d1);
					} else {
						rst = ((String) d0).compareTo((String) d1);
					}
				} else if (d1 instanceof Double) {
					rst = ((Double) d0).compareTo((Double) d1);
				} else if (d1 instanceof Integer) {
					rst = ((Integer) d0).compareTo((Integer) d1);
				} else if (d1 instanceof Date) {
					rst = ((Date) d0).compareTo((Date) d1);
				}
				if (order == 1) {
					return rst > 0 ? -1 : 1;
				}
				return rst;
			}

		});
	}

	public static void sortList(List<? extends Object> list, final String key, final int order) {
		sortList(list, key, null, order);
	}

	public static void sortList(List<? extends Object> list, final String key) {
		sortList(list, key, 0);
	}

	/**
	 * 转换Map集合的某个key到double数组
	 * 
	 * @param data
	 *            数据源
	 * @param key
	 * @return
	 */
	public static double[] convertSingleValueToArrays(List<Map<String, Object>> data, String key) {
		double[] rst = new double[data.size()];
		int i = 0;
		for (Map m : data) {
			Object o = null;
			o = m.get(key);
			if (o == null) {
				throw new NumberFormatException("\"" + key + "\" key's value don't allowed is null.");
			}
			if (o instanceof Double) {
				rst[i] = (Double) o;
			} else if (o instanceof Long) {
				rst[i] = (Long) o;
			} else if (o instanceof BigDecimal) {
				rst[i] = ((BigDecimal) o).doubleValue();
			}

			i++;
		}
		return rst;
	}

	public static List<Map<String, Object>> generics(List<Map> data) {
		List<Map<String, Object>> rst = new ArrayList<Map<String, Object>>();
		for (Map m : data) {
			rst.add(m);
		}
		return rst;
	}

	public static <T> List<T> copyToBean(Class<T> cls, List<? extends Map> list) {
		List<T> rst = new ArrayList<T>();
		for (Map m : list) {
			try {
				T o = cls.newInstance();
				BeanUtils.copyProperties(o, m);
				rst.add(o);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage());
			} catch (InstantiationException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return rst;
	}

	/**
	 * 获取Map对应Key数据,通过计算put新的属性
	 * 
	 * @param data
	 *            Map元素的集合
	 * @param keys
	 *            要取对太的值
	 * @param newKey
	 * @param count
	 */
	public static void countMap(List<? extends Object> data, String[] keys, String newKey, Count count) {
		Object[] l = new Object[keys.length];
		int i = 0;
		for (Object o : data) {
			Map m = (Map) o;
			for (String k : keys) {
				l[i] = m.get(k);
				i++;
			}
			i = 0;
			m.put(newKey, count.count(l));
		}
	}

	public static void countMap(List<? extends Object> data, String[] keys, Count count) {
		Object[] l = new Object[keys.length];
		for (Object o : data) {
			Map m = (Map) o;
			for (String k : keys) {
				m.put(k, count.count(new Object[] { m.get(k) }));
			}

		}
	}

	public interface Count {
		public Object count(Object[] obj);
	}

	public static float getMaxofArray(float a[]) {
		int k = a.length;
		float max = a[0];
		for (int i = 0; i < k; i++) {
			if (a[i] > max) {
				max = a[i];
			}
		}
		return max;
	}

	public static int getMaxofArray(int a[]) {
		int k = a.length;
		int max = a[0];
		for (int i = 0; i < k; i++) {
			if (a[i] > max) {
				max = a[i];
			}
		}
		return max;
	}

	public static double getMaxofArray(double a[]) {
		int k = a.length;
		double max = a[0];
		for (int i = 0; i < k; i++) {
			if (a[i] > max) {
				max = a[i];
			}
		}
		return max;
	}

	public static double getMinofArray(double a[]) {
		int k = a.length;
		double min = a[0];
		for (int i = 0; i < k; i++) {
			if (a[i] < min) {
				min = a[i];
			}
		}
		return min;
	}

	public static Double getMaxofArray(List<Double> list) {
		int k = list.size();
		if (k == 0) {
			return null;
		}
		double max = list.get(0);
		for (int i = 0; i < k; i++) {
			if (list.get(i) > max) {
				max = list.get(i);
			}
		}
		return max;
	}

	public static Double getMinofArray(List<Double> list) {
		int k = list.size();
		if (k == 0) {
			return null;
		}
		double min = list.get(0);
		for (int i = 0; i < k; i++) {
			if (list.get(i) < min) {
				min = list.get(i);
			}
		}
		return min;
	}

	public static int[] getChangeBeis(float a[], int b[]) {

		int k = a.length;
		for (int i = 0; i < k; i++) {
			a[i] = Math.abs(a[i]);
		}
		float max = getMaxofArray(a);
		for (int i = 0; i < k; i++) {
			b[i] = getSingleBei(a[i], max);
		}
		return b;
	}

	public static long[] getChangeBeis(double a[], long b[]) {

		int k = a.length;
		for (int i = 0; i < k; i++) {
			a[i] = Math.abs(a[i]);
		}
		double max = getMaxofArray(a);
		for (int i = 0; i < k; i++) {
			b[i] = getSingleBei(a[i], max);
		}
		return b;
	}

	public static int getSingleBei(float a, float max) {
		if (a == 0) {
			return 1;
		}
		int bei = (int) (Math.abs(max) / Math.abs(a));
		return bei;
	}

	public static long getSingleBei(double a, double max) {
		if (a == 0) {
			return 1;
		}
		long bei = (long) (Math.abs(max) / Math.abs(a));
		return bei;
	}

	/**
	 * 合并多个数组
	 * 
	 * @param first
	 * @param rest
	 * @return
	 */
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	public static void main(String[] args) throws Exception {

	}

	public static void setValue(Map map, Object thisObj) {
		Set set = map.keySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			Object val = map.get(obj);
			setMethod(obj, val, thisObj);
		}
	}

	public static void setMethod(Object method, Object value, Object thisObj) {
		Class c;
		try {
			c = Class.forName(thisObj.getClass().getName());
			String met = (String) method;
			met = met.trim();
			if (!met.substring(0, 1).equals(met.substring(0, 1).toUpperCase())) {
				met = met.substring(0, 1).toUpperCase() + met.substring(1);
			}
			if (!String.valueOf(method).startsWith("set")) {
				met = "set" + met;
			}
			Class types[] = new Class[1];
			types[0] = Class.forName("java.lang.String");
			Method m = c.getMethod(met, types);
			m.invoke(thisObj, value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
