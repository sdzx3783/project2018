package com.hotent.platform.controller.picture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.platform.service.system.SysTypeKeyService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.util.ServiceUtil;

/**
 * 
 * <pre> 
 * 描述：表单编辑器中的上传图片按钮对应的图片处理
 * 构建组：bpmx33
 * 作者：lqf
 * 邮箱:13507732754@189.cn
 * 日期:2016-7-27-下午4:21:39
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/platform/picture/pictureHandler/")
public class PictureHandlerController
{
	/**
	 * 
	 * 上传图片
	 * @param request
	 * @param response
	 * @throws IOException 
	 * void
	 */
	//附件保存类型
	private String saveType = ServiceUtil.getSaveType();
	public static final String PREX_URL="weixin/system/sysFile/file_";//用weixin开头 是为了让app能访问到图片
	public static final String SUFFIX_URL=".ht";
	
	public static final String PICFILE_TYPEKEY="富文本图片上传";
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysTypeKeyService sysTypeKeyService;
	@Resource
	private SysFileService sysFileService;
	private static Logger logger=Logger.getLogger(PictureHandlerController.class);
	@RequestMapping("upload")
	public void uploadPicture(MultipartHttpServletRequest request,HttpServletResponse response) throws IOException{
		String path = "";
		/*Map<String, MultipartFile> files = request.getFileMap();
		Iterator<MultipartFile> it = files.values().iterator();
		
		String attachPath = AppUtil.getRealPath("/attachFiles/temp");
		while (it.hasNext()) {
			MultipartFile f = it.next();
			path = createFilePath(attachPath + File.separator,f.getOriginalFilename());
			FileUtil.writeByte(path, f.getBytes());
		}
		response.getWriter().print(path);*/
		
		try {
			long userId = ContextUtil.getCurrentUserId(); // 获取当前用户的id
			SysUser appUser = null;
			if (userId > 0) {
				appUser = sysUserService.getById(userId);
			}
			// 附件保存路径
			String attachPath=ServiceUtil.getPictureBasePath();
			
			Map<String, MultipartFile> files = request.getFileMap();
			Iterator<MultipartFile> it = files.values().iterator();

			while (it.hasNext()) {
				Long fileId = UniqueIdUtil.genId();
				MultipartFile f = it.next();
				String oriFileName = f.getOriginalFilename();
				String extName = FileUtil.getFileExt(oriFileName);
				String fileName = fileId + "." + extName;
				// 开始写入物理文件
				String filePath = "";
				SysFile sysFile = new SysFile();
				
				filePath = createFilePath(attachPath + File.separator + appUser.getAccount(), fileName);
				// 开始写入物理文件
				if(saveType.contains("database")){       //二进制流动保存到数据库中
					sysFile.setFileBlob(f.getBytes());
				}else{
					FileUtil.writeByte(filePath, f.getBytes());
				}
				// end 写入物理文件
				// 向数据库中添加数据
				
				sysFile.setFileId(fileId);
				// 附件名称
				sysFile.setFileName(oriFileName.lastIndexOf('.')==-1?
						oriFileName:oriFileName.substring(0, oriFileName.lastIndexOf('.')));

				sysFile.setFilePath(filePath.replace(attachPath + File.separator,""));  //保存相对路径
				// 附件类型
				sysFile.setTypeId(sysTypeKeyService.getByKey(GlobalType.CAT_FLOW).getTypeId());
				sysFile.setFileType(PICFILE_TYPEKEY);
				// 上传时间
				sysFile.setCreatetime(new java.util.Date());
				// 扩展名
				sysFile.setExt(extName);
				// 字节总数
				sysFile.setTotalBytes(f.getSize());
				// 说明
				sysFile.setNote(FileUtil.getSize(f.getSize()));
				// 当前用户的信息
				if (appUser != null) {
					sysFile.setCreatorId(appUser.getUserId());
					sysFile.setCreator(appUser.getUsername());
				} else {
					sysFile.setCreator(SysFile.FILE_UPLOAD_UNKNOWN);
				}
				// 总的字节数
				sysFile.setDelFlag(SysFile.FILE_NOT_DEL);
				sysFileService.add(sysFile);
				path=PREX_URL+fileId+SUFFIX_URL;
			}
			response.getWriter().println(path);
		}catch(Exception e) {
			logger.error("图片上传出错",e);
			response.getWriter().println(path);
		}
	}
	
	/**
	 * 创建文件目录
	 * 
	 * @param tempPath
	 * @param fileName
	 *            文件名称
	 * @return 文件的完整目录
	 */
	private String createFilePath(String tempPath, String fileName) {
		File one = new File(tempPath);
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR); // 当前年份
		Integer month = cal.get(Calendar.MONTH) + 1; // 当前月份
		one = new File(tempPath +  File.separator + year +  File.separator + month);
		if (!one.exists()) {
			one.mkdirs();
		}
		String extName = FileUtil.getFileExt(fileName);//文件后缀名
		return one.getPath() + File.separator + System.currentTimeMillis() + "." + extName;
	}
}
