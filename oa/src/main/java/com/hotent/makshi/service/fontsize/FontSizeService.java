package com.hotent.makshi.service.fontsize;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.fontsize.FontSize;
import com.hotent.makshi.dao.fontsize.FontSizeDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class FontSizeService extends BaseService<FontSize>
{
	@Resource
	private FontSizeDao dao;
	
	public FontSizeService()
	{
	}
	
	@Override
	protected IEntityDao<FontSize,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd)throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			FontSize fontSize=getFontSize(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				fontSize.setId(genId);
				this.add(fontSize);
			}else{
				fontSize.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(fontSize);
			}
			cmd.setBusinessKey(fontSize.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取FontSize对象
	 * @param json
	 * @return
	 */
	public FontSize getFontSize(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		FontSize fontSize = (FontSize)JSONObject.toBean(obj, FontSize.class);
		return fontSize;
	}
	/**
	 * 保存 公文字号 信息
	 * @param fontSize
	 */

	public void save(FontSize fontSize) throws Exception{
		Long id=fontSize.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			fontSize.setId(id);
		    this.add(fontSize);
		}
		else{
		    this.update(fontSize);
		}
	}
	
	public FontSize selectFontSize(String type){
		return (dao.getBySqlKey("getByTypes",type)).get(0);
		
	}
}
