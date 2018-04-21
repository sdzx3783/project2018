/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.hotent.core.mail;

import com.hotent.core.mail.api.AttacheHandler;
import com.hotent.core.mail.model.Mail;
import com.hotent.core.mail.model.MailAddress;
import com.hotent.core.mail.model.MailAttachment;
import com.hotent.core.mail.model.MailSeting;
import com.hotent.core.util.DateUtil;
import com.sun.net.ssl.internal.ssl.Provider;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.SearchTerm;
import javax.mail.util.ByteArrayDataSource;

public class MailUtil {
    private MailSeting mailSetting;
    private AttacheHandler handler;

    public MailUtil(MailSeting mailSeting) {
        this.mailSetting = mailSeting;
    }

    public void connectSmtpAndReceiver() throws MessagingException {
        this.connectSmtp();
        this.connectReciever();
    }

    public void connectSmtp() throws MessagingException {
        Session session = this.getMailSession("smtp");
        Transport transport = null;
        try {
            try {
                transport = session.getTransport("smtp");
                transport.connect(this.mailSetting.getSendHost(), this.mailSetting.getMailAddress(), this.mailSetting.getPassword());
            }
            catch (MessagingException e) {
                e.printStackTrace();
                throw e;
            }
        }
        finally {
            transport.close();
        }
    }

    public void connectReciever() throws MessagingException {
        Session session = this.getMailSession(this.mailSetting.getProtocal());
        Store store = null;
        URLName urln = new URLName(this.mailSetting.getProtocal(), this.mailSetting.getReceiveHost(), Integer.parseInt(this.mailSetting.getReceivePort()), null, this.mailSetting.getMailAddress(), this.mailSetting.getPassword());
        try {
            try {
                store = session.getStore(urln);
                store.connect();
            }
            catch (MessagingException e) {
                e.printStackTrace();
                throw e;
            }
        }
        finally {
            store.close();
        }
    }

    public void send(Mail mail) throws UnsupportedEncodingException, MessagingException {
        Session session = this.getMailSession("smtp");
        MimeMessage message = new MimeMessage(session);
        this.addAddressInfo(mail, (Message)message);
        MimeBodyPart contentPart = new MimeBodyPart();
        MimeMultipart multipart = new MimeMultipart();
        contentPart.setHeader("Content-Transfer-Encoding", "base64");
        contentPart.setContent((Object)mail.getContent(), "text/html;charset=utf-8");
        message.setSubject(mail.getSubject(), "utf-8");
        message.setText("utf-8", "utf-8");
        message.setSentDate(new Date());
        multipart.addBodyPart((BodyPart)contentPart);
        message.setContent((Multipart)multipart);
        for (MailAttachment attachment : mail.getMailAttachments()) {
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Object source = null;
            String filePath = attachment.getFilePath();
            source = filePath == null || "".equals(filePath) ? new ByteArrayDataSource(attachment.getFileBlob(), "application/octet-stream") : new FileDataSource(new File(filePath));
            messageBodyPart.setDataHandler(new DataHandler((DataSource)source));
            messageBodyPart.setFileName(MimeUtility.encodeWord((String)attachment.getFileName(), (String)"UTF-8", (String)"Q"));
            multipart.addBodyPart((BodyPart)messageBodyPart);
        }
        message.setContent((Multipart)multipart);
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect(this.mailSetting.getSendHost(), this.mailSetting.getMailAddress(), this.mailSetting.getPassword());
        transport.sendMessage((Message)message, message.getAllRecipients());
    }

    public List<Mail> receive(AttacheHandler handler) throws Exception {
        return this.receive(handler, "");
    }

    public List<Mail> receive(AttacheHandler handler, String lastHandleUID) throws Exception {
        this.handler = handler;
        Store connectedStore = this.getConnectedStore();
        Folder folder = this.getFolder(connectedStore);
        try {
            List<Mail> list = this.getMessages(folder, lastHandleUID);
            return list;
        }
        catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            this.close(folder, connectedStore);
        }
    }

    public Mail getByMessageID(AttacheHandler handler, String messageID) throws Exception {
        MessageIDTerm searchTerm;
        this.handler = handler;
        Store connectedStore = this.getConnectedStore();
        Folder folder = this.getFolder(connectedStore);
        Message[] messages = folder.search((SearchTerm)(searchTerm = new MessageIDTerm(messageID)));
        if (messages == null || messages.length == 0) {
            return null;
        }
        ArrayList<Mail> mailList = new ArrayList<Mail>();
        this.buildMailList(messageID, (MimeMessage)messages[0], mailList);
        return mailList.get(0);
    }

    private Store getConnectedStore() throws MessagingException {
        Session session = this.getMailSession(this.mailSetting.getProtocal());
        URLName urln = new URLName(this.mailSetting.getProtocal(), this.mailSetting.getReceiveHost(), Integer.parseInt(this.mailSetting.getReceivePort()), null, this.mailSetting.getMailAddress(), this.mailSetting.getPassword());
        Store store = session.getStore(urln);
        store.connect();
        return store;
    }

    private Folder getFolder(Store store) throws MessagingException {
        if (!store.isConnected()) {
            store = this.getConnectedStore();
        }
        Folder folder = store.getFolder("INBOX");
        if (this.mailSetting.getIsDeleteRemote().booleanValue()) {
            folder.open(2);
        } else {
            folder.open(1);
        }
        return folder;
    }

    private List<Mail> getMessages(Folder folder, String lastHandleUID) throws Exception {
        int total = folder.getMessageCount();
        ArrayList<Mail> mailList = new ArrayList<Mail>();
        return this.getMessages(folder, lastHandleUID, mailList, total);
    }

    private List<Mail> getMessages(Folder folder, String lastHandleUID, List<Mail> mailList, int endIndex) throws Exception {
        MimeMessage msg = null;
        boolean isLastHandleUIDNotEmpty = lastHandleUID != null && !"".equals(lastHandleUID.trim());
        try {
            int i = endIndex;
            while (i > 0) {            	
                if (!folder.isOpen()) {
                    folder = this.getFolder(folder.getStore());
                }
                msg = (MimeMessage)folder.getMessage(i);                
                if (lastHandleUID.equals("") && DateUtil.daysBetween(msg.getSentDate(),new Date())>7) {//收取最近7天
					break;
				}
                String messageId = msg.getMessageID();
                if (!isLastHandleUIDNotEmpty || !lastHandleUID.equals(messageId)) {
                    this.buildMailList(messageId, msg, mailList);
                    --i;
                    continue;                    
                }
                break;
            }
        }
        catch (FolderClosedException closeException) {
            folder = this.getFolder(folder.getStore());
            this.getMessages(folder, lastHandleUID, mailList, endIndex - mailList.size());
        }catch (Exception exception) {//add by sherwin 其他异常错误
            folder = this.getFolder(folder.getStore());
            this.getMessages(folder, lastHandleUID, mailList, endIndex - mailList.size());
        }        
        Collections.reverse(mailList);
        return mailList;
    }

    private void buildMailList(String messageId, MimeMessage message, List<Mail> list) throws Exception {
        if (this.handler.isDownlad(messageId) == null || !this.handler.isDownlad(messageId).booleanValue()) {
            return;
        }
        Mail mail = this.getMail(message);
        mail.setUID(messageId);
        list.add(mail);
        if (this.mailSetting.getIsDeleteRemote().booleanValue()) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
        System.gc();
    }

    private Mail getMail(MimeMessage message) throws Exception {
        Mail mail = new Mail();
        Date sentDate = null;
        sentDate = message.getSentDate() != null ? message.getSentDate() : new Date();
        mail.setSendDate(sentDate);
        mail.setSubject(MimeUtility.decodeText((String)message.getSubject()));
        StringBuffer bodytext = new StringBuffer();
        this.getMailContent((Part)message, bodytext, mail);
        mail.setContent(bodytext.toString());
        MailAddress temp = this.getFrom(message);
        mail.setSenderAddress(temp.getAddress());
        mail.setSenderName(temp.getName());
        temp = this.getMailAddress(Message.RecipientType.TO, message);
        mail.setReceiverAddresses(temp.getAddress());
        mail.setReceiverName(temp.getName());
        temp = this.getMailAddress(Message.RecipientType.BCC, message);
        mail.setBcCAddresses(temp.getAddress());
        mail.setBccName(temp.getName());
        temp = this.getMailAddress(Message.RecipientType.CC, message);
        mail.setCopyToAddresses(temp.getAddress());
        mail.setCopyToName(temp.getName());
        return mail;
    }

    private MailAddress getFrom(MimeMessage mimeMessage) throws Exception {
        MailAddress mailAddress = new MailAddress();
        try {
            InternetAddress[] address = (InternetAddress[])mimeMessage.getFrom();
            if (address == null || address.length == 0) {
                return mailAddress;
            }
            mailAddress.setAddress(address[0].getAddress());
            mailAddress.setName(address[0].getPersonal());
        }
        catch (Exception address) {
            // empty catch block
        }
        return mailAddress;
    }

    private MailAddress getMailAddress(Message.RecipientType recipientType, MimeMessage mimeMessage) throws Exception {
        MailAddress mailAddress = new MailAddress();
        InternetAddress[] address = (InternetAddress[])mimeMessage.getRecipients(recipientType);
        if (address == null) {
            return mailAddress;
        }
        StringBuffer addresses = new StringBuffer("");
        StringBuffer name = new StringBuffer("");
        int i = 0;
        while (i < address.length) {
            String email = address[i].getAddress();
            if (email != null) {
                String personal = address[i].getPersonal();
                if (personal == null) {
                    personal = email;
                }
                switch (i) {
                    case 0: {
                        addresses.append(MimeUtility.decodeText((String)email));
                        name.append(MimeUtility.decodeText((String)personal));
                        break;
                    }
                    default: {
                        addresses.append(",").append(MimeUtility.decodeText((String)email));
                        name.append(",").append(MimeUtility.decodeText((String)personal));
                    }
                }
            }
            ++i;
        }
        mailAddress.setAddress(addresses.toString());
        mailAddress.setName(name.toString());
        return mailAddress;
    }

    private void getMailContent(Part message, StringBuffer bodyText, Mail mail) throws Exception {
        String contentType = message.getContentType();
        int nameindex = contentType.indexOf("name");
        boolean conname = false;
        if (nameindex != -1) {
            conname = true;
        }
        if ((message.isMimeType("text/plain") || message.isMimeType("text/html")) && !conname) {
        	try{
        		bodyText.append((String)message.getContent());
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart)message.getContent();
            int count = multipart.getCount();
            LinkedHashMap<String, BodyPart> partMap = new LinkedHashMap<String, BodyPart>();
            boolean blnTxt = false;
            boolean blnHtml = false;
            int i = 0;
            while (i < count) {
                BodyPart tmpPart = multipart.getBodyPart(i);
                String partType = tmpPart.getContentType();
                if (tmpPart.isMimeType("text/plain")) {
                    partMap.put("text/plain", tmpPart);
                    blnTxt = true;
                } else if (tmpPart.isMimeType("text/html")) {
                    partMap.put("text/html", tmpPart);
                    blnHtml = true;
                } else {
                    partMap.put(partType, tmpPart);
                }
                ++i;
            }
            if (blnTxt && blnHtml) {
                partMap.remove("text/plain");
            }
            Set set = partMap.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                this.getMailContent((Part)((Entry)it.next()).getValue(), bodyText, mail);
            }
        } else if (message.isMimeType("message/rfc822")) {
            this.getMailContent((Part)message.getContent(), bodyText, mail);
        } else if (message.isMimeType("application/octet-stream") || message.isMimeType("image/*") || message.isMimeType("application/*")) {
            if (this.mailSetting.getIsHandleAttach().booleanValue()) {
                this.handler.handle(message, mail);
            } else {
                String filename = MimeUtility.decodeText((String)message.getFileName());
                mail.getMailAttachments().add(new MailAttachment(filename, ""));
            }
        }
    }

    private Properties getProperty(String protocal) {
        Security.addProvider(new Provider());
        Properties props = new Properties();
        if (this.mailSetting.getSSL().booleanValue()) {
            props.setProperty("mail." + protocal + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        props.setProperty("mail." + protocal + ".socketFactory.fallback", "false");
        if ("smtp".equals(protocal)) {
            String host = this.mailSetting.getSendHost();
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.port", this.mailSetting.getSendPort());
            props.setProperty("mail.smtp.socketFactory.port", this.mailSetting.getSendPort());
            props.setProperty("mail.smtp.auth", String.valueOf(this.mailSetting.getValidate()));
            int gmail = host.indexOf("gmail");
            int live = host.indexOf("live");
            if (gmail != -1 || live != -1) {
                props.setProperty("mail.smtp.starttls.enable", "true");
            }
            if (!this.mailSetting.getSSL().booleanValue()) {
                props.setProperty("mail.smtp.socketFactory.class", "javax.net.SocketFactory");
            }
        } else {
            props.setProperty("mail." + protocal + ".host", this.mailSetting.getReceiveHost());
            props.setProperty("mail." + protocal + ".port", this.mailSetting.getReceivePort());
            props.setProperty("mail." + protocal + ".socketFactory.port", this.mailSetting.getReceivePort());
            if ("pop3".equals(protocal)) {
                props.setProperty("mail.smtp.starttls.enable", "true");
            } else {
                props.setProperty("mail.store.protocol", "imap");
            }
        }
        return props;
    }

    private Session getMailSession(String protocal) {
        Properties props = this.getProperty(protocal);
        Session instance = null;
        instance = "imap".equals(protocal) ? Session.getDefaultInstance((Properties)props, (Authenticator)new Authenticator(){

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailUtil.this.mailSetting.getMailAddress(), MailUtil.this.mailSetting.getPassword());
            }
        }) : Session.getInstance((Properties)props);
        return instance;
    }

    private void addAddressInfo(Mail mail, Message message) throws UnsupportedEncodingException, MessagingException {
        InternetAddress senderAddress = this.toInternetAddress(this.mailSetting.getNickName(), mail.getSenderAddress());
        message.setFrom((Address)senderAddress);
        this.addAddressInfo(message, mail.getReceiverAddresses(), Message.RecipientType.TO);
        this.addAddressInfo(message, mail.getCopyToAddresses(), Message.RecipientType.CC);
        this.addAddressInfo(message, mail.getBcCAddresses(), Message.RecipientType.BCC);
    }

    private void addAddressInfo(Message message, String address, RecipientType recipientType) throws UnsupportedEncodingException, MessagingException {
        new MailAddress();
        HashSet addresseSet = new HashSet();
        if(address != null && !"".equals(address.trim())) {
           String[] addressArr = address.split(",");
           String[] arg9 = addressArr;
           int arg8 = addressArr.length;

           for(int arg7 = 0; arg7 < arg8; ++arg7) {
              String id = arg9[arg7];
              if(id != null && !"".equals(id.trim())) {
                 MailAddress mailAddress = new MailAddress();
                 mailAddress.setAddress(id);
                 mailAddress.setName(id);
                 addresseSet.add(mailAddress);
              }
           }
        }

        InternetAddress[] arg10 = this.toInternetAddress((Set)addresseSet);
        if(arg10 != null) {
           message.addRecipients(recipientType, arg10);
        }

     }

    private InternetAddress toInternetAddress(String name, String address) throws UnsupportedEncodingException, AddressException {
        if (name != null && !name.trim().equals("")) {
            return new InternetAddress(address, MimeUtility.encodeWord((String)name, (String)"utf-8", (String)"Q"));
        }
        return new InternetAddress(address);
    }

    private InternetAddress toInternetAddress(MailAddress emailAddress) throws UnsupportedEncodingException, AddressException {
        return this.toInternetAddress(emailAddress.getName(), emailAddress.getAddress());
    }

    private InternetAddress[] toInternetAddress(Set<MailAddress> set) throws UnsupportedEncodingException, AddressException {
        if (set == null || set.size() < 1) {
            return null;
        }
        InternetAddress[] address = new InternetAddress[set.size()];
        Iterator<MailAddress> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            address[i++] = this.toInternetAddress(it.next());
        }
        return address;
    }

    private void close(Folder folder, Store store) {
        try {
            try {
                if (folder != null && folder.isOpen()) {
                    folder.close(this.mailSetting.getIsDeleteRemote().booleanValue());
                }
                if (store != null && store.isConnected()) {
                    store.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                folder = null;
                store = null;
            }
        }
        finally {
            folder = null;
            store = null;
        }
    }

}