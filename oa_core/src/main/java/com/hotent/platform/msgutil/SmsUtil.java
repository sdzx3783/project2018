package com.hotent.platform.msgutil;

import com.hotent.core.util.AppUtil;
import com.hotent.platform.model.system.MessageLog;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.MessageLogService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.hotent.platform.webservice.util.PropUtils;

import java.rmi.RemoteException;

/**
 * Created by Cat on 2017/11/2.
 */
public class SmsUtil {

    public static void main(String[] args) {
       // System.out.println("this is path:" + ClassLoader.getSystemResource(""));
    }

    private static final Logger log = Logger.getLogger(SmsUtil.class);

    public static final String TASK_INFO_1 = "，您在OA系统提交的流程“";

    public static final String TASK_INFO_2 = "”已审批结束，请知悉。";

    public static final String TIME_PATTERN = "MM月dd日 HH:mm";

    //短信发送成功
    public static final int SMS_SUC = 1;

    //短信发送失败
    public static final int SMS_FAILED = 0;

    //失败的MSGID
    public static final String FAILED_MSGID = "0";

    //默认发送给用户手机状态为未知
    public static final int DEF_MSG_SEND_STATUS = -10;

    public static final String NO_NEED_REPORT = "0";

    public static final String NEED_REPORT = "1";

    //短信接口批次？
    public static final String SMS_BATCH = "3";

    private static final String TEST_MOBILE = "13590147628";

    /**
     * 测试
     */
    public static final String SMS_ENV_TYPE_TEST = "test";

    /**
     * 正式服
     */
    public static final String SMS_ENV_TYPE_OFFICIAL = "official";

    //流程结束后是否发送短信，0不发，1发
    public static final String SMS_PRO_FIN_NOT_SEND = "0";
    //流程结束后是否发送短信，0不发，1发
    public static final String SMS_PRO_FIN_SEND = "1";

    //定时任务发送待办，是否执行0不执行，1执行。---【用于区分多台服务器调用同一个定时任务配置（数据库）的情况】
    public static final String SMS_UNDONE_TIMER_NOT_SEND = "0";
    //定时任务发送待办，是否执行0不执行，1执行。---【用于区分多台服务器调用同一个定时任务配置（数据库）的情况】
    public static final String SMS_UNDONE_TIMER_SEND = "1";


    /**
     * 环境
     */
    private static String SMS_ENV_TYPE = "";

    private static String SMS_ENV_TEST_REC = "";

    /**
     * 流程结束后是否发送短信，0不发，1发
     */
    private static String SMS_PRO_FIN_SEN = "";

    //定时任务发送待办，是否执行0不执行，1执行。---【用于区分多台服务器调用同一个定时任务配置（数据库）的情况】
    private static String SMS_UNDONE_TIMER_SEN = "";

    /**
     * 初始化参数
     */
    private static void initParam() {

        log.info("smsutil init param!");

        SMS_ENV_TYPE = PropUtils.getPropertyByDirKey("sms.env.type", "application");

        SMS_ENV_TEST_REC = PropUtils.getPropertyByDirKey("sms.env.test.rec", "application");

        SMS_PRO_FIN_SEN = PropUtils.getPropertyByDirKey("sms.pro.fin.sen", "application");

        SMS_UNDONE_TIMER_SEN = PropUtils.getPropertyByDirKey("sms.undone.timer.sen", "application");
    }

    /**
     *SAGE标签内容说明:
     * <message>:多条短信可以拥有多个<message>节点
     * <orgaddr>:源地址,可为空,在现有系统管理员分配号码上再扩展号码.比如:移动分配号码为:106573000573,系统管理员分配号码为:08,该节点填写号码为05,则下发的号码为:1065730005730805
     * <mobile>:手机号码,多个手机号码公用<content>节点,则用英文逗号(,)隔开
     * <content>:短信内容
     * <needreport>是否要状态报告,0,不要,1,要
     * <sendtime>:要求下发时间,可为空,为空则立即下发.格式:yyyyMMddhhmmss
     * <publicContent>公共短信内容,可为空,不为空则所有<message>节点内的<content>内容节点,在短信下发时都默认加上<publicContent>节点内容.例如: 两个手机号码<content>节点分别为:李老师,杨老师<publicContent>节点为: ,今天下午举行活动,则短信下发内容分别为:
     * 李老师,今天下午举行活动
     * 杨老师,今天下午举行活动
     *
     * @param orgaddr
     * @param mobile
     * @param content
     * @param sendTime
     * @param needreport
     * @return
     */
    public static String getMsgBody(String orgaddr, String mobile, String content, String sendTime, String needreport) {
        String messgeTag = "<message>" +
                "<orgaddr>" + orgaddr + "</orgaddr>" +
                "<mobile>" + mobile + "</mobile>" +
                "<content>" + content + "</content>" +
                "<sendtime>" + sendTime + "</sendtime>" +
                "<needreport>" + needreport + "</needreport>" +
                "</message>";

        return messgeTag;
    }


    /**
     * 完整发送的消息体
     *
     * @param msgBodys
     * @return
     */
    public static String getSendBody(String msgBodys) {
        String sendBody = "<sendbody>" + msgBodys + "<publicContent></publicContent></sendbody>";

        return sendBody;
    }


    /**
     * 根据返回的结果取msgid.返回0代表没有MSGID，小于0代表发送失败
     *
     * @param str
     * @return
     */
    public static String getMsgIdByXMLStr(String str) {

        if(str == null || str.isEmpty())
        {
            return "0";
        }

        String result = "0";
        if (str.contains("<msgid>") && str.contains("</msgid>")) {
            String subStr = str.substring(str.indexOf("<msgid>"), str.indexOf("</msgid>"));

            if (subStr.indexOf(",") > -1) {
                subStr = subStr.substring(subStr.indexOf(",") + 1);

                result = subStr;
            }
        }

        return result;
    }




    /**
     * 保存已发送的短信
     *
     * @param subject
     * @param recUser
     * @param state
     */
    public static void saveSmsLog(String subject, String recUser, int state, String msgSendId, Integer msgToMobStatus) {
        // 保存发送消息日志
        MessageLogService messageLogService = (MessageLogService) AppUtil.getBean(MessageLogService.class);
        messageLogService.addSMSLog(subject, recUser, MessageLog.MOBILE_TYPE, state, msgSendId, msgToMobStatus);
    }



    /**
     * 发送短信提醒
     *
     * @param user
     */
    public static void toSendSMSbyUser(SysUser user, Sms smsService, String smsContent) {

        if(SMS_ENV_TYPE_TEST.equals(getSmsEnvType())) {
            log.info("test send sms");
            sendTest(user, smsService, smsContent);
        } else if(SMS_ENV_TYPE_OFFICIAL.equals(getSmsEnvType())) {
            log.info("official send sms");
            sendOfficial(user, smsService, smsContent);
        } else {
            log.error("send sms not valid type!");
        }
    }

    /**
     * 测试的发送接口
     * @param user
     * @param smsService
     * @param smsContent
     */
    private static void sendTest(SysUser user, Sms smsService, String smsContent) {
        if (user != null && user.getUserId() != null && user.getFullname() != null
                && user.getMobile() != null) {
            user.setMobile(getSmsEnvTestRec());
            String result = "";
            try {
                result = smsService.insertDownSms(SmsConfig.USER_NAME, SmsConfig.PASS_WORD, SmsUtil.SMS_BATCH,
                        SmsUtil.getSendBody(SmsUtil.getMsgBody("", user.getMobile(), smsContent, "", SmsUtil.NEED_REPORT)));
                //短信发送给平台成功或平台返回失败码
                String msgId = SmsUtil.getMsgIdByXMLStr(result);
                int SMS_STATUS = SmsUtil.SMS_SUC;
                if (("0").equals(msgId) || msgId.contains("-")) {
                    SMS_STATUS = SmsUtil.SMS_FAILED;
                }
                SmsUtil.saveSmsLog(smsContent, user.getMobile(), SMS_STATUS, msgId, SmsUtil.DEF_MSG_SEND_STATUS);
            } catch (RemoteException e) {
                e.printStackTrace();
                log.error("sms call send interface error.userid=" + user.getUserId() + ";content=" + smsContent);
                log.error(e.getMessage());

                SmsUtil.saveSmsLog(smsContent, user.getMobile(), SmsUtil.SMS_FAILED, SmsUtil.FAILED_MSGID, SmsUtil.DEF_MSG_SEND_STATUS);
            }

        }
    }

    /**
     * 正式有发送接口
     * @param user
     * @param smsService
     * @param smsContent
     */
    private static void sendOfficial(SysUser user, Sms smsService, String smsContent) {
        if (user != null && user.getUserId() != null && user.getFullname() != null
                && user.getMobile() != null) {

            String result = "";
            try {
                result = smsService.insertDownSms(SmsConfig.USER_NAME, SmsConfig.PASS_WORD, SmsUtil.SMS_BATCH,
                        SmsUtil.getSendBody(SmsUtil.getMsgBody("", user.getMobile(), smsContent, "", SmsUtil.NEED_REPORT)));
                //短信发送给平台成功或平台返回失败码
                String msgId = SmsUtil.getMsgIdByXMLStr(result);
                int SMS_STATUS = SmsUtil.SMS_SUC;
                if (("0").equals(msgId) || msgId.contains("-")) {
                    SMS_STATUS = SmsUtil.SMS_FAILED;
                }
                SmsUtil.saveSmsLog(smsContent, user.getMobile(), SMS_STATUS, msgId, SmsUtil.DEF_MSG_SEND_STATUS);
            } catch (RemoteException e) {
                e.printStackTrace();
                log.error("sms call send interface error.userid=" + user.getUserId() + ";content=" + smsContent);
                log.error(e.getMessage());

                SmsUtil.saveSmsLog(smsContent, user.getMobile(), SmsUtil.SMS_FAILED, SmsUtil.FAILED_MSGID, SmsUtil.DEF_MSG_SEND_STATUS);
            }

        }
    }

    /**
     *
     * @return SmsEnvType
     */
    public static String getSmsEnvType() {
        if(StringUtils.isEmpty(SMS_ENV_TYPE)) {
            initParam();
        }

        return SMS_ENV_TYPE;
    }

    public static String getSmsEnvTestRec() {
        if(StringUtils.isEmpty(SMS_ENV_TEST_REC)) {
            initParam();
        }

        return SMS_ENV_TEST_REC;
    }

    public static String getSmsProFinSen() {
        if(StringUtils.isEmpty(SMS_PRO_FIN_SEN)) {
            initParam();
        }

        return SMS_PRO_FIN_SEN;
    }

    public static String getSmsUndoneTimerSen() {
        if(StringUtils.isEmpty(SMS_UNDONE_TIMER_SEN)) {
            initParam();
        }

        return SMS_UNDONE_TIMER_SEN;
    }

}
