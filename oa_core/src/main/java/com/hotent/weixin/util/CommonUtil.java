package com.hotent.weixin.util;

import com.hotent.core.page.PageList;
import com.hotent.weixin.model.ListModel;

public class CommonUtil {
	
	public static ListModel getListModel(PageList list){
		ListModel model=new ListModel();
		
		model.setRowList(list);
		
		PageList pageList=(PageList)list;
		
		model.setTotal(pageList.getTotalPage());
		
		return model;
	}

}
