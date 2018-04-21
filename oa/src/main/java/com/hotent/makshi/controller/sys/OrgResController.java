package com.hotent.makshi.controller.sys;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.utils.OrgResUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.service.system.ResourcesService;

import java.util.Arrays;

/**
 * 对象功能:合同基本信息 控制器类
 */
@Controller
@RequestMapping("/makshi/sys/orgres/")
public class OrgResController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ResourcesService resourcesService;

	/**
	 * 跳转到部门管理
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("orgreslist")
	@Action(description = "获取目录")
	public ModelAndView orgreslist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id", 10000010330001l);
		List<Resources> resList = resourcesService.getOrgResByParentId(id);
		for (Resources res : resList) {
			if(Arrays.asList(OrgResUtils.secondToFirstMenu).contains(res.getResName())){
				if(res.getIsDisplayInMenu()==0){
					res.setResChoose(false);
				}else{
					res.setResChoose(true);
				}
			}else{
				String resOrgIds = res.getResOrgIds();
				if (StringUtil.isEmpty(resOrgIds)) {
					res.setResChoose(false);
					continue;
				}
				String[] resOrg = resOrgIds.split(",");
				for (String org : resOrg) {
					if ((id + "").equals(org)) {
						res.setResChoose(true);
						break;
					}
				}

			}
			
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/makshi/sys/orgreslist.jsp");
		mv.addObject("resList", resList).addObject("orgId", id);
		return mv;
	}

	@RequestMapping("setOrgRes")
	@Action(description = "设置部门资源")
	public void setOrgRes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = RequestUtil.getLong(request, "orgId", -1l);
		long resId = RequestUtil.getLong(request, "resId", -1l);
		boolean checked = RequestUtil.getBoolean(request, "checked", false);
		Resources resources = resourcesService.getById(resId);
		if (resources != null) {
			if(Arrays.asList(OrgResUtils.secondToFirstMenu).contains(resources.getResName())){
				if (checked) {
					resources.setIsDisplayInMenu((short)1);
				}else{
					resources.setIsDisplayInMenu((short)0);
				}
			}else{
				String resOrgIds = resources.getResOrgIds();
				if (checked) {
					if (StringUtil.isEmpty(resOrgIds)) {
						resources.setResOrgIds(orgId + "");
					} else {
						resources.setResOrgIds(resOrgIds + "," + orgId);
					}

				} else {
					if (resOrgIds.contains("," + orgId)) {
						resources.setResOrgIds(resOrgIds
								.replaceAll("," + orgId, ""));
					} else if (resOrgIds.contains("" + orgId)) {
						resources
								.setResOrgIds(resOrgIds.replaceAll("" + orgId, ""));
					}
				}
			}
			
			resourcesService.update(resources);
		}

	}

	/**
	 * 跳转到门户管理
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("portalList")
	@Action(description = "获取目录")
	public ModelAndView portalList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/makshi/sys/portallist.jsp");
		return mv;
	}

	/**
	 * 文件下载
	 * 
	 * @Description:
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("download")
	public String downloadFile(@RequestParam("fileName") String fileName,
			HttpServletRequest request, HttpServletResponse response) {
		if (fileName != null) {
			String realPath = request.getServletContext().getRealPath(
					"WEB-INF/view/makshi/portal/");
			File file = new File(realPath, fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition",
						"attachment;fileName=" + fileName);// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					// TODO: handle exception
					log.error("错误信息",e);
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							log.error("错误信息",e);
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							log.error("错误信息",e);
						}
					}
				}
			}
		}
		return null;
	}

	/*
	 * 采用spring提供的上传文件的方法
	 */
	@RequestMapping("upload")
	public void upload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next()
						.toString());
				if (file != null) {
					String realPath = request.getServletContext().getRealPath(
							"WEB-INF/view/makshi/portal/");
					String path = realPath+"\\" + file.getOriginalFilename();
					// 上传
					file.transferTo(new File(path));
				}

			}
		}
		response.getWriter().print("上传成功");
	}

}