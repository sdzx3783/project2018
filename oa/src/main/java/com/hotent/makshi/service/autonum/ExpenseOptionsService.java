package com.hotent.makshi.service.autonum;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.autonum.ExpenseOptionsDao;
import com.hotent.makshi.model.autonum.ExpenseOptions;

@Service
public class ExpenseOptionsService extends BaseService<ExpenseOptions> {

	@Resource
	private ExpenseOptionsDao ExpenseOptionsDao;

	@Override
	protected IEntityDao<ExpenseOptions, Long> getEntityDao() {
		return ExpenseOptionsDao;
	}

	public void save(ExpenseOptions expenseOptions) {
		Long id = expenseOptions.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			expenseOptions.setId(id);
			this.add(expenseOptions);
		} else {
			this.update(expenseOptions);
		}
	}

}
