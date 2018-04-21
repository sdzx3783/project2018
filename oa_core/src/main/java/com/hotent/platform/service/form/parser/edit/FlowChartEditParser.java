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

import net.sf.json.JSONObject;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.springframework.stereotype.Service;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.util.RequestContext;
import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.service.form.parser.util.FieldRight;
import com.hotent.platform.service.form.parser.util.ParserParam;

/**
 * <pre>
 * 描述：
 * 构建组：bpm33
 * 作者：aschs
 * 邮箱:liyj@jee-soft.cn
 * 日期:2015-12-11-下午2:49:51
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class FlowChartEditParser extends EditAbstractParser {

	@Override
	public Object parse(ParserParam param, Element element) {
		Element emt = new Element(Tag.valueOf("iframe"), element.baseUri());
		String ctx=RequestContext.getHttpServletRequest().getContextPath();
		Object instanceId = param.getVar("instanceId");
		Object actDefId=param.getVar("actDefId");
		if(BeanUtils.isNotEmpty(instanceId)){
			emt.attr("src",ctx+"/platform/bpm/processRun/processImage.ht?actInstId="+instanceId);
		}else if(BeanUtils.isNotEmpty(actDefId)){
			emt.attr("src",ctx+"/platform/bpm/bpmDefinition/flowImg.ht?actDefId="+actDefId);
		}else{
			return null;
		}
		emt.attr("name","flowchart");
		emt.attr("id","flowchart");
		emt.attr("marginwidth","0");
		emt.attr("marginheight","0");
		emt.attr("frameborder","0");
		emt.attr("scrolling","no");
		emt.attr("width","100%");
		element.after(emt);
		element.remove();
		return null;
	}
}
