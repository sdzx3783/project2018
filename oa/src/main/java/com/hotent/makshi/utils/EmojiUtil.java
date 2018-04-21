package com.hotent.makshi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.hotent.core.mail.model.Mail;
import com.hotent.platform.model.mail.OutMail;

public class EmojiUtil {
	
	public static String emojiConvert(String str) {
		if (str == null || str.length() <= 0) {
			return str;
		}
		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb,
						"[[" + URLEncoder.encode(matcher.group(1), "UTF-8")
								+ "]]");
			} catch (UnsupportedEncodingException e) {
				return str;
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String emojiRecovery(String str) {
		if (str == null || str.length() <= 0) {
			return str;
		}
		String patternString = "\\[\\[(.*?)\\]\\]";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb,
						URLDecoder.decode(matcher.group(1), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				return str;
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public static OutMail Convert(OutMail outMail){
		if(outMail==null){
			return null;
		}
		if(outMail!=null && StringUtils.isNotEmpty(outMail.getContent())){
			outMail.setContent(EmojiUtil.emojiConvert(outMail.getContent()));
		}
		if(outMail!=null && StringUtils.isNotEmpty(outMail.getTitle())){
			outMail.setTitle(EmojiUtil.emojiConvert(outMail.getTitle()));
		}
		return outMail;
	}
	
	public static Mail Convert(Mail mail){
		if(mail==null){
			return null;
		}
		if(mail!=null && StringUtils.isNotEmpty(mail.getContent())){
			mail.setContent(EmojiUtil.emojiConvert(mail.getContent()));
		}
		if(mail!=null && StringUtils.isNotEmpty(mail.getSubject())){
			mail.setSubject(EmojiUtil.emojiConvert(mail.getSubject()));
		}
		return mail;
	}
	
	public static OutMail Recovery(OutMail outMail){
		if(outMail==null){
			return null;
		}
		if(outMail!=null && StringUtils.isNotEmpty(outMail.getContent())){
			outMail.setContent(EmojiUtil.emojiRecovery(outMail.getContent()));
		}
		if(outMail!=null && StringUtils.isNotEmpty(outMail.getTitle())){
			outMail.setTitle(EmojiUtil.emojiRecovery(outMail.getTitle()));
		}
		return outMail;
	}
}
