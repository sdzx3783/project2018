package com.hotent.makshi.service.autonum;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.autonum.AutoNumberDao;
import com.hotent.makshi.model.autonum.AutoNumber;

@Service
public class AutoNumberService extends BaseService<AutoNumber> {

	@Resource
	private AutoNumberDao autoNumberDao;

	@Override
	protected IEntityDao<AutoNumber, Long> getEntityDao() {
		return autoNumberDao;
	}

	public String getNo(String name, String alias, String uninque_no) {
		Map<String, Object> param = new HashMap<>();
		param.put("uninque_no", uninque_no);
		param.put("alias", alias);
		AutoNumber auto = (AutoNumber) autoNumberDao.getOne("getNo", param);
		if (null == auto) {
			auto = new AutoNumber();
			auto.setId(UniqueIdUtil.genId());
			auto.setName(name);
			auto.setAlias(alias);
			auto.setUninque_no(uninque_no);
			auto.setCurvalue(1);
			autoNumberDao.add(auto);
			return 1 + "";
		}
//		else {
//			param = new HashMap<>();
//			param.put("id", auto.getId());
//			autoNumberDao.update("incr", param);
//		}
		return (auto.getCurvalue() + 1) + "";
	}

}
