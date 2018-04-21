package com.hotent.platform.service.share;

import com.hotent.core.db.datasource.DbContextHolder;
import com.hotent.core.util.AppUtil;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.service.system.SysDataSourceService;

public class DataSourceHelper {

	/**
	 * 注意这个别名sysDataSource的别名
	 * 
	 * @param alias
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void setDataSource(String alias) {
		SysDataSourceService sysDataSourceService = AppUtil.getBean(SysDataSourceService.class);
		SysDataSource sysDataSource = sysDataSourceService.getByAlias(alias);
		if (sysDataSource == null)
			return;
		DbContextHolder.setDataSource(alias, sysDataSource.getDbType());
		//System.out.println(alias+" 被设置为当前数据源");
	}

}
