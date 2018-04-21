package com.hotent.platform.webservice.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtil {
	private static Logger logger = LoggerFactory.getLogger(WebUtil.class);

	private static String RUN_ENV = "dev";

	private static final String[] BASIC_ARRAY = { "quot", "amp", "lt", "gt" };

	private static final String[] APOS_ARRAY = { "apos" };

	private static final String[] ISO8859_1_ARRAY = { "nbsp", "iexcl", "cent", "pound", "curren", "yen", "brvbar",
			"sect", "uml", "copy", "ordf", "laquo", "not", "shy", "reg", "macr", "deg", "sup2", "sup3","plusmn",
			"acute", "micro", "para", "middot", "cedil", "sup1", "ordm", "raquo", "frac14", "frac12", "frac34",
			"iquest", "Agrave", "Aacute", "Acirc", "Atilde", "Auml", "Aring", "AElig", "Ccedil", "Egrave", "Eacute",
			"Ecirc", "Euml", "Igrave", "Iacute", "Icirc", "Iuml", "ETH", "Ntilde", "Ograve", "Oacute", "Ocirc",
			"Otilde", "Ouml", "times", "Oslash", "Ugrave", "Uacute", "Ucirc", "Uuml", "Yacute", "THORN", "szlig",
			"agrave", "aacute", "acirc", "atilde", "auml", "aring", "aelig", "ccedil", "egrave", "eacute", "ecirc",
			"euml", "igrave", "iacute", "icirc", "iuml", "eth", "ntilde", "ograve", "oacute", "ocirc", "otilde", "ouml",
			"divide", "oslash", "ugrave", "uacute", "ucirc", "uuml", "yacute", "thorn", "yuml" };

	private static final String[] HTML40_ARRAY = { "fno", "Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta",
			"Theta", "Iota", "Kappa", "Lambda", "Mu", "Nu", "Xi", "Omicron", "Pi", "Rho", "Sigma", "Tau", "Upsilon",
			"Phi", "Chi", "Psi", "Omega", "alpha", "beta", "gamma", "delta", "epsilon", "zeta", "eta", "theta", "iota",
			"kappa", "lambda", "mu", "nu", "xi", "omicron", "pi", "rho", "sigmaf", "sigma", "tau", "upsilon", "phi",
			"chi", "psi", "omega", "thetasym", "upsih", "piv", "bull", "hellip", "prime", "Prime", "oline", "frasl",
			"weierp", "image", "real", "trade", "alefsym", "larr", "uarr", "rarr", "darr", "harr", "crarr", "lArr",
			"uArr", "rArr", "dArr", "hArr", "forall", "part", "exist", "empty", "nabla", "isin", "notin", "ni", "prod",
			"sum", "minus", "lowast", "radic", "prop", "infin", "ang", "and", "or", "cap", "cup", "int", "there4",
			"sim", "cong", "asymp", "ne", "equiv", "le", "ge", "sub", "sup", "sube", "supe", "oplus", "otimes", "perp",
			"sdot", "lceil", "rceil", "lfloor", "rfloor", "lang", "rang", "loz", "spades", "clubs", "hearts", "diams",
			"OElig", "oelig", "Scaron", "scaron", "Yuml", "circ", "tilde", "ensp", "emsp", "thinsp", "zwnj", "zwj",
			"lrm", "rlm", "ndash", "mdash", "lsquo", "rsquo", "sbquo", "ldquo", "rdquo", "bdquo", "dagger", "Dagger",
			"permil", "lsaquo", "rsaquo", "euro" };

	private static final String[] ALL_ARRAY = ArrayUtil.concatAll(BASIC_ARRAY, APOS_ARRAY, ISO8859_1_ARRAY,
			HTML40_ARRAY);

	/**
	 * 得到容器的主机名
	 * 
	 * @return
	 */
	public static String getHost() {
		String host = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getLocalAddr();
		if (((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getLocalPort() != 80) {
			host += ":" + ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
					.getLocalPort();
		}
		return host;
	}

	public static String getRequestPath() {
		String s = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURL()
				.toString();
		return s.replaceAll("/[^/]*$", "");
	}

	/**
	 * 获取web容器路径
	 * 
	 * @param path
	 * @return
	 */
	public static String getWebPath() {
		ServletContext servletContext = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest().getServletContext();
		return servletContext.getRealPath("/");
	}

	public static boolean isTrueEnvir() {
		try {
			RUN_ENV = System.getenv("RUN_ENV");
		} catch (NullPointerException e) {
			logger.info("没有配置环境变量RUN_ENV，默认使用开发环境");
		} catch (SecurityException e) {
			logger.error("读取环境变量错误" + e.getMessage());
		}
		if (RUN_ENV.equals("prd")) {
			return true;
		} else if (RUN_ENV.equals("pub")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * url参数转换为map
	 * 
	 * @Title: paramToMap
	 * @Description: TODO
	 * @param queryString
	 * @return
	 */
	public static Map<String, String> paramToMap(String queryString) {
		Map<String, String> params = new HashMap<String, String>();
		for (String keyword : ALL_ARRAY) {
			queryString = queryString.replaceAll("&" + keyword + ";", "@" + keyword + ";");
		}
		String[] paramArray = queryString.split("&");
		for (String param : paramArray) {
			String[] temp = param.split("=");
			if (temp.length > 1) {
				String param1 = param.split("=")[1];
				for (String keyword : ALL_ARRAY) {
					param1 = param1.replaceAll("@" + keyword + ";", "&" + keyword + ";");
				}
				try {
					params.put(param.split("=")[0], URLDecoder.decode(param1, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				params.put(param.split("=")[0], null);
			}

		}
		return params;
	}

	/**
	 * 通过正则表达式将URL串中的参数名-值进行分解
	 * 
	 * @param url
	 * @param key
	 * @return
	 */

	public static String getUrlParam(String url, String key) {
		Pattern p = Pattern.compile("(\\?|&+)(.+?)=([^&]*)");
		Matcher m = p.matcher(url);
		while (m.find()) {
			if (m.group(2).equals(key)) {
				return m.group(3);
			}
		}
		return null;
	}
}
