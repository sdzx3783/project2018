package com.hotent.makshi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ValidateUtils {
	/** 手机 */  
    private static final String V_MOBILE="^1[3|4|5|7|8][0-9]{9}$";
    /** 
     * 验证是不是手机号码 
     * @param value 要验证的字符串 
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
     */  
    public static boolean Mobile(String value){ 
    	if(StringUtils.isEmpty(value)){
    		return false;
    	}
        return match(V_MOBILE,value);  
    }  
    private static boolean match(String regex, String str)  
    {  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str);  
        return matcher.matches();  
    } 
    
}


