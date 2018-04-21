package com.hotent.makshi.service.common;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.platform.service.util.ServiceUtil;


@Service
public class FlowToEntityService {
	
	@Resource
	private SysFileService sysFileService;
	
	/**
	 * 将流程文件json解析，得到fileid，查询file，复制记录，复制文件，返回新json
	 * @param fileJson
	 * @return
	 */
	public String flowToEntityFile(String fileJson){
		try {
			if(null==fileJson || fileJson.length()==0){
				return null;
			}
			JSONArray jsonArray = JSONArray.parseArray(fileJson);
			// 附件保存路径
			String attachPath=ServiceUtil.getBasePath();
			String newFileJson = "";
			for (Object obj:jsonArray) {
				JSONObject json=JSONObject.parseObject(obj.toString());
				String fileId=json.getString("id");
				if(!StringUtil.isEmpty(fileId)){
					SysFile file = sysFileService.getById(Long.parseLong(fileId));
	//				SysFile newFile = new SysFile();
					if(file!=null){
						String oriPath = attachPath + File.separator+ file.getFilePath();
						String newPath =oriPath.replace("flow", "entity");
						if(oriPath.equals(newPath)){
							continue;
						}
						FileUtil.createFolder(newPath, true);
						boolean success = FileUtil.copyFile(attachPath + File.separator+file.getFilePath(), newPath);
						if(!success){
							throw new RuntimeException("附件保存失败");
						}
						file.setFileId(UniqueIdUtil.genId());
						file.setTypeId(6l);
						file.setFilePath(file.getFilePath().replace("flow", "entity"));
						file.setCreatetime(new Date());
						file.setFileType("实体附件");
						sysFileService.add(file);
						if(StringUtil.isEmpty(newFileJson)){
							newFileJson=fileJson.replace(fileId, file.getFileId().toString());
						}else{
							newFileJson=newFileJson.replace(fileId, file.getFileId().toString());
						}
						
						
					}
				}
			}
			return newFileJson;
		} catch (Exception e) {
			throw new RuntimeException("附件同步失败",e.getCause());
		}
		}
}
