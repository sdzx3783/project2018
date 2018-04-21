package com.hotent.platform.service.ats;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.dao.ats.AtsAttenceCalculateSetDao;
import com.hotent.platform.model.ats.AtsAttenceCalculateSet;

/**
 * <pre>
 * 对象功能:考勤计算设置 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zxh
 * 创建时间:2015-06-03 14:46:19
 * </pre>
 */
@Service
public class AtsAttenceCalculateSetService extends
		BaseService<AtsAttenceCalculateSet> {
	@Resource
	private AtsAttenceCalculateSetDao dao;

	public AtsAttenceCalculateSetService() {
	}

	@Override
	protected IEntityDao<AtsAttenceCalculateSet, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 保存 考勤计算设置 信息
	 * 
	 * @param atsAttenceCalculateSet
	 */
	public void save(AtsAttenceCalculateSet atsAttenceCalculateSet) {
		Short type = atsAttenceCalculateSet.getType();
		Long id = 1L;
		AtsAttenceCalculateSet set = getDefault();
		if (BeanUtils.isEmpty(set)) {
			set = new AtsAttenceCalculateSet();
			set.setId(id);
			if (type == 1) {
				set.setSummary(atsAttenceCalculateSet.getDetail());
			} else {
				set.setDetail(atsAttenceCalculateSet.getDetail());
			}
			this.add(set);
		} else {
			if (type == 1) {
				set.setSummary(atsAttenceCalculateSet.getDetail());
			} else {
				set.setDetail(atsAttenceCalculateSet.getDetail());
			}
			this.update(set);
		}
	}

	public AtsAttenceCalculateSet getDefault() {
		return dao.getById(1L);
	}

}
