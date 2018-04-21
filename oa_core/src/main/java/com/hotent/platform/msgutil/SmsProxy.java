package com.hotent.platform.msgutil;

import org.springframework.stereotype.Service;

@Service
public class SmsProxy implements Sms {
  private String _endpoint = null;
  private Sms sms = null;
  
  public SmsProxy() {
    _initSmsProxy();
  }
  
  public SmsProxy(String endpoint) {
    _endpoint = endpoint;
    _initSmsProxy();
  }
  
  private void _initSmsProxy() {
    try {
      sms = (new SmsServiceLocator()).getSms();
      if (sms != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sms)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sms)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sms != null)
      ((javax.xml.rpc.Stub)sms)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Sms getSms() {
    if (sms == null)
      _initSmsProxy();
    return sms;
  }
  
  public String insertDownSms(String username, String password, String batch, String sendbody) throws java.rmi.RemoteException{
    if (sms == null)
      _initSmsProxy();
    return sms.insertDownSms(username, password, batch, sendbody);
  }
  
  public String connMas(String username, String password) throws java.rmi.RemoteException{
    if (sms == null)
      _initSmsProxy();
    return sms.connMas(username, password);
  }
  
  public String getUpSms(String username, String password, String destaddr) throws java.rmi.RemoteException{
    if (sms == null)
      _initSmsProxy();
    return sms.getUpSms(username, password, destaddr);
  }
  
  public String rspUpSms(String username, String password, String msgid) throws java.rmi.RemoteException{
    if (sms == null)
      _initSmsProxy();
    return sms.rspUpSms(username, password, msgid);
  }
  
  public String getDownSmsResult(String username, String password, String batch, String cnt) throws java.rmi.RemoteException{
    if (sms == null)
      _initSmsProxy();
    return sms.getDownSmsResult(username, password, batch, cnt);
  }
  
  public String getSpecialDownSmsResult(String username, String password, String batch, String msgid) throws java.rmi.RemoteException{
    if (sms == null)
      _initSmsProxy();
    return sms.getSpecialDownSmsResult(username, password, batch, msgid);
  }
  
  public boolean checkpass(String username, String password) throws java.rmi.RemoteException{
    if (sms == null)
      _initSmsProxy();
    return sms.checkpass(username, password);
  }
  
  
}