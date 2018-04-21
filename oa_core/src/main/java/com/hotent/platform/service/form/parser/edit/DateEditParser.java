/**
 * 描述：TODO
 * 包名：com.hotent.platform.service.form.parser
 * 文件名：DanhwbkParser.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2015-12-11-下午2:49:51
 *  2015广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.platform.service.form.parser.edit;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.springframework.stereotype.Service;

import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.service.form.parser.util.FieldRight;
import com.hotent.platform.service.form.parser.util.ParserParam;

/**
 * <pre>
 * 描述：日期控件
 * 构建组：bpm33
 * 作者：aschs
 * 邮箱:liyj@jee-soft.cn
 * 日期:2015-12-11-下午2:49:51
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class DateEditParser extends EditAbstractParser {
	@Override
	public Object parse(ParserParam param, Element element) {
		BpmFormData data = param.getBpmFormData();
		Map<String, Object> permission = param.getPermission();
		Map<String, Object> external = handleExternal(element);
		String ename = getElementName(element, data);
		String val = getValue(element, data);
		String right = getRight(element, permission);
		String format = ((Map) external.get("dbType")).get("format").toString();

		if (FieldRight.W.equals(right) || FieldRight.B.equals(right)) {//编辑
			Element inputE = element.select("input").get(0);
			inputE.val(val);
			inputE.attr("name", ename);
			inputE.attr("validate", "{'date':true}");
			if(ename.contains("month")){
				inputE.attr("validate", "{'date':false}");
			}
			
			inputE.attr("class", "Wdate");
			inputE.attr("datefmt", format);
			if(FieldRight.B.equals(right)){
				addRequred(inputE);
			}
			element.after(inputE);
		} else if (FieldRight.R.equals(right)) {//只读
			element.after(val);
		}else if (FieldRight.RP.equals(right)) {//只读提交
			element.after(val);
			//创建隐藏字段
			Element emt = new Element(Tag.valueOf("input"), element.baseUri());
			emt.attr("type","hidden");
			emt.val(val);
			emt.attr("name", ename);
			element.after(emt.toString());
		} else {//没有就代表隐藏
		}

		element.remove();
		return null;
	}
}
