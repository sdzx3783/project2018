package com.hotent.makshi.service.housereimburse;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.housereimburse.HouseReimburseDao;
import com.hotent.makshi.model.housereimburse.HouseReimburse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service
public class HouseReimburseService extends BaseService<HouseReimburse> {
	
	@Resource
	private HouseReimburseDao dao;

	public HouseReimburseService() {
	}

	@Override
	protected IEntityDao<HouseReimburse, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 流程处理器方法 用于处理业务数据
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd) throws Exception {
		Map data = cmd.getFormDataMap();
		if (BeanUtils.isNotEmpty(data)) {
			String json = data.get("json").toString();
			HouseReimburse houseReimburse = getHouseReimburse(json);
			if (StringUtil.isEmpty(cmd.getBusinessKey())) {
				Long genId = UniqueIdUtil.genId();
				houseReimburse.setId(genId);
				this.add(houseReimburse);
			} else {
				houseReimburse.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(houseReimburse);
			}
			cmd.setBusinessKey(houseReimburse.getId().toString());
		}
	}

	/**
	 * 根据json字符串获取HouseReimburse对象
	 * 
	 * @param json
	 * @return
	 */
	public HouseReimburse getHouseReimburse(String json) {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if (StringUtil.isEmpty(json))
			return null;
		JSONObject obj = JSONObject.fromObject(json);
		HouseReimburse houseReimburse = (HouseReimburse) JSONObject.toBean(obj, HouseReimburse.class);
		return houseReimburse;
	}
}
