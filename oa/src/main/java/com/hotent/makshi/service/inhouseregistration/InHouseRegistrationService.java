package com.hotent.makshi.service.inhouseregistration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.inhouseregistration.InHouseRegistrationDao;
import com.hotent.makshi.dao.inuserinfo.UserInfoDao;
import com.hotent.makshi.model.inhouseregistration.InHouseRegistration;
import com.hotent.makshi.model.inuserinfo.UserInfo;

@Service
public class InHouseRegistrationService extends BaseService<InHouseRegistration> {
	@Resource
	private InHouseRegistrationDao dao;

	@Resource
	private UserInfoDao userInfoDao;

	public InHouseRegistrationService() {
	}

	@Override
	protected IEntityDao<InHouseRegistration, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 根据外键删除子表记录
	 * 
	 * @param id
	 */
	private void delByPk(Long id) {
		userInfoDao.delById(id);
	}

	/**
	 * 删除数据 包含相应子表记录
	 * 
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for (Long id : lAryId) {
			delByPk(id);
			// dao.delById(id);
		}
	}

	/**
	 * 添加数据
	 * 
	 * @param inHouseRegistration
	 * @throws Exception
	 */
	public void addAll(InHouseRegistration inHouseRegistration) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("houseId", inHouseRegistration.getHouse_id());
		InHouseRegistration his = (InHouseRegistration) dao.getOne("getByHouseId", map);
		if (null != his) {
			inHouseRegistration.setId(his.getId());
			super.update(inHouseRegistration);
		} else
			super.add(inHouseRegistration);
		addSubList(inHouseRegistration);
	}

	/**
	 * 更新数据
	 * 
	 * @param inHouseRegistration
	 * @throws Exception
	 */
	public void updateAll(InHouseRegistration inHouseRegistration) throws Exception {
		super.update(inHouseRegistration);
		delByPk(inHouseRegistration.getId());
		addSubList(inHouseRegistration);
	}

	/**
	 * 添加子表记录
	 * 
	 * @param inHouseRegistration
	 * @throws Exception
	 */
	public void addSubList(InHouseRegistration inHouseRegistration) throws Exception {
		// 需要删除历史记录，然后插入新的记录。
		userInfoDao.delByMainId(inHouseRegistration.getId());
		List<UserInfo> userInfoList = inHouseRegistration.getUserInfoList();
		if (BeanUtils.isNotEmpty(userInfoList)) {

			for (UserInfo userInfo : userInfoList) {
				userInfo.setRefId(inHouseRegistration.getId());
				Long id = UniqueIdUtil.genId();
				userInfo.setId(id);
				userInfoDao.add(userInfo);
			}
		}
	}

	/**
	 * 根据外键获得员工入住信息列表
	 * 
	 * @param id
	 * @return
	 */
	public List<UserInfo> getUserInfoList(Long id) {
		return userInfoDao.getByMainId(id);
	}

	public List<UserInfo> getUserInfoListByParam(HashMap param) {
		return userInfoDao.getByParam(param);
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
			InHouseRegistration inHouseRegistration = getInHouseRegistration(json);
			if (StringUtil.isEmpty(cmd.getBusinessKey())) {
				Long genId = UniqueIdUtil.genId();
				inHouseRegistration.setId(genId);
				this.addAll(inHouseRegistration);
			} else {
				inHouseRegistration.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(inHouseRegistration);
			}
			cmd.setBusinessKey(inHouseRegistration.getId().toString());
		}
	}

	/**
	 * 根据json字符串获取InHouseRegistration对象
	 * 
	 * @param json
	 * @return
	 */
	public InHouseRegistration getInHouseRegistration(String json) {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if (StringUtil.isEmpty(json))
			return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("userInfoList", UserInfo.class);
		InHouseRegistration inHouseRegistration = (InHouseRegistration) JSONObject.toBean(obj, InHouseRegistration.class, map);
		return inHouseRegistration;
	}

	/**
	 * 保存 租房入住人员登记表 信息
	 * 
	 * @param inHouseRegistration
	 */

	public void save(InHouseRegistration inHouseRegistration) throws Exception {
		Long id = inHouseRegistration.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			inHouseRegistration.setId(id);
			this.addAll(inHouseRegistration);
		} else {
			this.updateAll(inHouseRegistration);
		}
	}
}
