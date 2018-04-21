package com.hotent.weixin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.page.PageList;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.dao.system.SysReadRecodeDao;
import com.hotent.platform.model.system.SysBulletin;
import com.hotent.platform.model.system.SysBulletinColumn;
import com.hotent.platform.model.system.SysReadRecode;
import com.hotent.platform.service.system.SysBulletinColumnService;
import com.hotent.platform.service.system.SysBulletinService;
import com.hotent.weixin.model.ListModel;
import com.hotent.weixin.model.RowModel;

@Controller
@RequestMapping("/weixin/bulletin/")
public class BulletinController {
	
	@Resource
	private FreemarkEngine freemarkEngine;

	@Resource
	private SysBulletinService sysBulletinService;
	@Resource
	private SysBulletinColumnService bulletinColumnService;
	@Resource
	private SysReadRecodeDao sysReadRecodeDao;
	

	@RequestMapping("detail")
	@ResponseBody
	public SysBulletin bulletinDetail(HttpServletRequest request, HttpServletResponse response) {
		Long bulletinId=RequestUtil.getLong(request, "bulletinId", 0L);
		SysBulletin bulletin=sysBulletinService.getById(bulletinId);
		Long userId=ContextUtil.getCurrentUserId();
		
		if(!sysReadRecodeDao.hasRead(bulletinId, userId)){
			sysReadRecodeDao.add(new SysReadRecode(bulletinId,userId,"Bulletin",bulletin.getColumnid()));
		}
		
		return bulletin;
	}
	
	@RequestMapping("getListByColumn")
	@ResponseBody
	public ListModel getListByColumn(HttpServletRequest request, HttpServletResponse response) {
		QueryFilter filter=new QueryFilter(request, true);
		Long columnId=RequestUtil.getLong(request, "columnid", 0L);
		Long userId=ContextUtil.getCurrentUserId();
		
		filter.addFilter("userId", userId);
		
		SysBulletinColumn bulletinColumn=bulletinColumnService.getById(columnId);
		
		PageList<SysBulletin> pageList=sysBulletinService.getByColumnId(filter);
		
		ListModel model=new ListModel();
		model.setTitle(bulletinColumn.getName());
		
		List list=new ArrayList();
		
		for(SysBulletin bulletin:pageList){
			RowModel m=new RowModel(bulletin.getSubject(), bulletin.getId().toString(), TimeUtil.getDateTimeString(bulletin.getCreatetime())  );
			list.add(m);
		}
		
		model.setRowList(list);
		
		model.setTotal(pageList.getTotalPage());
		
		return model;
	}
	
	@RequestMapping("columns")
	@ResponseBody
	public List<SysBulletinColumn> getColumns(){
		List<SysBulletinColumn> bulletinColumns= bulletinColumnService.getColumn();
		return bulletinColumns;
	}

}
