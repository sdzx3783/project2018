package com.hotent.makshi.service.assettransfer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.assettransfer.AssetTransfer;
import com.hotent.makshi.dao.assettransfer.AssetTransferDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class AssetTransferService extends WfBaseService<AssetTransfer>
{
	@Resource
	private AssetTransferDao dao;
	@Resource
	private BpmService bpmService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public AssetTransferService()
	{
	}
	
	@Override
	protected IEntityDao<AssetTransfer,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<AssetTransfer> getAll(QueryFilter queryFilter){
		List<AssetTransfer> assetTransferList=super.getAll(queryFilter);
		return assetTransferList;
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
			AssetTransfer assetTransfer=getAssetTransfer(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				assetTransfer.setId(genId);
				this.add(assetTransfer);
			}else{
				assetTransfer.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(assetTransfer);
			}
			cmd.setBusinessKey(assetTransfer.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取AssetTransfer对象
	 * @param json
	 * @return
	 */
	public AssetTransfer getAssetTransfer(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		AssetTransfer assetTransfer = (AssetTransfer)JSONObject.toBean(obj, AssetTransfer.class);
		return assetTransfer;
	}
	/**
	 * 保存 资产移交表 信息
	 * @param assetTransfer
	 */

	@WorkFlow(flowKey="asset_transfer",tableName="asset_transfer")
	public void save(AssetTransfer assetTransfer) throws Exception{
		Long id=assetTransfer.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			assetTransfer.setId(id);
		    this.add(assetTransfer);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(assetTransfer.getId().toString(), null , true,  "asset_transfer");
		}
		else{
		    this.update(assetTransfer);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
