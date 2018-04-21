/**   
 * @Title: ActionException.java 
 * @Package net.makshi.site.exception 
 * @Description: TODO
 * @author Sherwin  
 * @date 2012-5-10 下午5:36:37 
 * @version V1.0
 * @Copyright (c)2012  MaiShi technology Co.Ltd. 
 */
package com.hotent.platform.webservice.exception;


/**
 * @ClassName: ActionException 
 * @Description: TODO
 */
public abstract class AppRuntimeException extends RuntimeException {

 

    /** 
	 * @Fields serialVersionUID : TODO 
	 */ 
	private static final long serialVersionUID = -550393596058018434L;

	public AppRuntimeException(String message) {

       super(message);

    } 

    public AppRuntimeException(Throwable throwable){

       super(throwable);

    }
 

    public AppRuntimeException(String message,Throwable throwable){

       super(message,throwable);

    }

	public String getMessage() {
		if (getCause() != null) {
			StringBuilder sb = new StringBuilder();
			if (super.getMessage() != null) {
				sb.append(super.getMessage()).append("; ");
			}
			sb.append("nested exception is ").append(getCause());
			return sb.toString();
		}
		else {
			return super.getMessage();
		}
	}


	public Throwable getRootCause() {
		Throwable rootCause = null;
		Throwable cause = getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}
}
