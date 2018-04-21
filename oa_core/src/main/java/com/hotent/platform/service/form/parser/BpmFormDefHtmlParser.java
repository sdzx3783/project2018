/**
 * 描述：TODO
 * 包名：com.hotent.platform.service.form.parser
 * 文件名：BpmFormDefHtmlParser.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2015-12-11-下午2:48:31
 *  2015广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.platform.service.form.parser;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.service.form.parser.util.ParserParam;

/**
 * <pre>
 * 描述：表单html解释器
 * 构建组：bpm33
 * 作者：aschs
 * 邮箱:liyj@jee-soft.cn
 * 日期:2015-12-11-下午2:48:31
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class BpmFormDefHtmlParser {
	public final static String PARSE_TARGET = "parser";//解释器的html中的标记
	public final static String SUBID = "subid";//解释子表时循环数据时用来标记字段ID的属性
	private static Map<String, ParserInter> parserMap = new HashMap<String, ParserInter>();

	/**
	 * 表单解释
	 * 
	 * @param html
	 *            ：需要解释的包含解释器的Html
	 * @param param
	 *            ：解释器参数
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public static String parse(String html, ParserParam param) {
		if (parserMap.isEmpty()) {
			init();
		}
		
		Document doc = Jsoup.parseBodyFragment(html);
		runMustParser(doc, param);
		while (true) {
			Elements es = doc.select("[" + PARSE_TARGET + "]");//选择蕴含解释器的html
			if (es.isEmpty()) {//当找不到时才结束，因为有可能出现在解释器解释后的html里还有解释器的情况
				break;
			}
			for (Element e : es) {
				String parserName = e.attr(PARSE_TARGET);
				ParserInter parser = parserMap.get(parserName);
				if (parser != null) {
					parser.doParse(param, e);
				}
				e.removeAttr(BpmFormDefHtmlParser.PARSE_TARGET);//解释完就删除标记
			}
		}

		return doc.body().toString();
	}

	private static synchronized void init() {
		try {
			Map<String, Object> map = AppUtil.getImplInstance(ParserInter.class);
			if (BeanUtils.isNotEmpty(map)) {
				for (String key : map.keySet()) {
					ParserInter pi = (ParserInter) map.get(key);
					parserMap.put(pi.getName(), pi);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取解释器的map主要是为了方便解释器间的相互调用
	 * 
	 * @param name
	 * @return ParserInter
	 * @exception
	 * @since 1.0.0
	 */
	public static ParserInter getParser(String name) {
		return parserMap.get(name);
	}

	/**
	 * 必须运行的解释器
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private static void runMustParser(Document doc, ParserParam param) {
		try {
			Map<String, Object> map = AppUtil.getImplInstance(MustRunParser.class);
			if (BeanUtils.isNotEmpty(map)) {
				for (String key : map.keySet()) {
					MustRunParser mrp = (MustRunParser) map.get(key);
					mrp.parse(param, doc);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
