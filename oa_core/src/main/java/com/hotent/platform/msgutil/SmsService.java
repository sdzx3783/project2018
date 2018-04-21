/**
 * SmsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hotent.platform.msgutil;

public interface SmsService extends javax.xml.rpc.Service {
    public String getSmsAddress();

    public Sms getSms() throws javax.xml.rpc.ServiceException;

    public Sms getSms(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
