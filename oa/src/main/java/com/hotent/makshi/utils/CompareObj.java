package com.hotent.makshi.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.hotent.platform.model.system.SysUser;

public class CompareObj<T> {
	private final Logger log = Logger.getLogger(this.getClass());

	public String contrastObj(Object oldBean, Object newBean) {
		String str = "";
		// if (oldBean instanceof SysConfServer && newBean instanceof SysConfServer) {
		T pojo1 = (T) oldBean;
		T pojo2 = (T) newBean;
		try {
			Class clazz = pojo1.getClass();
			Field[] fields = pojo1.getClass().getDeclaredFields();
			int i = 1;
			for (Field field : fields) {
				if ("serialVersionUID".equals(field.getName())) {
					continue;
				}
				PropertyDescriptor pd = null;
				try {
					pd = new PropertyDescriptor(field.getName(), clazz);
				} catch (Exception e) {
					continue;
				}

				Method getMethod = pd.getReadMethod();
				Object o1 = getMethod.invoke(pojo1);
				Object o2 = getMethod.invoke(pojo2);
				if (o1 == null || o2 == null) {
					continue;
				}
				if (!o1.toString().equals(o2.toString())) {
					if (i != 1) {
						str += "\n";
					}
					str += i + "、字段名称" + field.getName() + ",旧值:" + o1 + ",新值:" + o2;
					i++;
				}
			}
		} catch (Exception e) {
			log.error("错误信息", e);
		}
		// }
		return str;
	}

	public static void main(String[] args) {
		// ContractBorrowApplication obj1 = new ContractBorrowApplication();
		// ContractBorrowApplication obj2 = new ContractBorrowApplication();
		// obj1.setContract_name("111");
		// obj2.setContract_name("222");
		// obj1.setBorrower("111");
		// obj2.setBorrower("222");
		// Date date = new Date();
		// obj1.setBorrow_date(date);
		// obj2.setBorrow_date(date);
		SysUser obj1 = new SysUser();
		SysUser obj2 = new SysUser();
		obj1.setMobile("111");
		obj2.setMobile("222");

		CompareObj<SysUser> test = new CompareObj<SysUser>();
		String contrastObj = test.contrastObj(obj1, obj2);

	}
}
