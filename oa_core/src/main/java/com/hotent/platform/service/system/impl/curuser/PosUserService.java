package com.hotent.platform.service.system.impl.curuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import com.hotent.core.model.CurrentUser;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.service.system.ICurUserService;
import com.hotent.platform.service.system.PositionService;


public class PosUserService implements ICurUserService {
	
	@Resource
	PositionService positionService;

	@Override
	public List<Long> getByCurUser(CurrentUser currentUser) {
		
		List<Position> positions= positionService.getByUserId(currentUser.getUserId());
		List<Long> list=new ArrayList<Long>();
		for(Position pos:positions){
			list.add(pos.getPosId());
		}
		return list;
	}

	@Override
	public String getKey() {
		return "pos";
	}

	@Override
	public String getTitle() {
		return "岗位授权";
	}



	

}
