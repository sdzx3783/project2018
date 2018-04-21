package com.hotent.makshi.service.doc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.doc.DocDao;
import com.hotent.makshi.model.doc.Doc;

@Service
public class DocService extends BaseService<Doc> {
	@Resource
	private DocDao dao;
	
	public DocService(){
		
	}
	
	@Override
	protected IEntityDao<Doc, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	
	/**
	 * 保存  文档目录
	 * @param partyFiles
	 */

	public void save(Doc doc) throws Exception{
		Long id=doc.getDocid();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			doc.setDocid(id);
		    this.add(doc);
		}
		else{
		    this.update(doc);
		}
	}
	
	
	public List<Doc> getDocsByOrg(Long orgid,Long supid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgid", orgid);
		params.put("supid", supid);
		List<Doc> docList = dao.getBySqlKey("getDocsByOrg", params);
		return docList;
	}
	
	public List<Doc> getDocBySupId(Long supid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("supid", supid);
		List<Doc> docList = dao.getBySqlKey("getDocBySupId", params);
		return docList;
	}
	/**
	 * 获取门户文档下的所有目录(门户图片除外)
	 * @param params
	 * @return
	 */
	public List<Doc> getDocForPortal(Map<String,Object> params){
		params.put("docname", "门户文档");
		params.put("docsupid",0);
		params.put("isdelete", 0);
		params.put("orgid",0);
		params.put("doctype", 0);
		List<Doc> docList = dao.getBySqlKey("getDocByParam", params);
		if(docList==null || docList.size()==0){
			return null;
		}
		Doc temp = docList.get(0);
		params.clear();
		params.put("docsupid", temp.getDocid());
		params.put("isdelete", 0);
		params.put("orgid",0);
		params.put("doctype", 1);
		List<Doc> list = dao.getBySqlKey("getDocByParam", params);
		if(list!=null && list.size()>0){
			Iterator<Doc> iterator = list.iterator();
			while(iterator.hasNext()){
				if(iterator.next().getDocname().equals("门户图片")){
					iterator.remove();
				}
			}
		}
		return list;
	}
	
	public List<Doc> getDocByParam(Map<String,Object> params){
		List<Doc> list = dao.getBySqlKey("getDocByParam", params);
		return list;
	}
	/**
	 * 获取深水咨询下一级名称为"门户文档"的目录文档：
	 * @return
	 */
	public Doc getCommonTopDoc(){
		Map<String,Object> params=new HashMap<>();
		params.put("docname", "门户文档");
		params.put("docsupid",0);
		params.put("isdelete", 0);
		params.put("orgid",0);
		params.put("doctype", 0);
		List<Doc> docList = dao.getBySqlKey("getDocByParam", params);
		if(docList==null || docList.size()==0){
			return null;
		}
		return docList.get(0);
	}
	
	/**
	 * 根据部门id获取部门文档目录
	 * @return
	 */
	public Doc getCommonOrgDoc(Long orgId){
		Map<String,Object> params=new HashMap<>();
		params.put("docname", "部门文档");
		params.put("docsupid",0);
		params.put("isdelete", 0);
		params.put("orgid",orgId);
		params.put("doctype", 0);
		List<Doc> docList = dao.getBySqlKey("getDocByParam", params);
		if(docList==null || docList.size()==0){
			return null;
		}
		return docList.get(0);
	}
	
	/**
	 * 获取深水咨询下一级名称为"门户文档"的目录文档 的 指定名称的子目录(文档类型)文档
	 * @return
	 */
	public Doc getCommonDocByName(String name){
		Doc topDoc = getCommonTopDoc();
		if(topDoc==null){
			return null;
		}else{
			Map<String,Object> params=new HashMap<>();
			params.put("docsupid", topDoc.getDocid());
			params.put("isdelete", 0);
			params.put("orgid",0);
			params.put("doctype", 1);
			List<Doc> list = dao.getBySqlKey("getDocByParam", params);
			for (Doc doc : list) {
				if(name.equals(doc.getDocname())){
					return doc;
				}
			}
			return null;
		}
	}
	
	/**
	 * 根据部门id获取名称为部门文档下指定名称的子目录(文档类型)文档
	 * @return
	 */
	public Doc getCommonOrgDocByName(Long orgId,String name){
		Doc topDoc = getCommonOrgDoc(orgId);
		if(topDoc==null){
			return null;
		}else{
			Map<String,Object> params=new HashMap<>();
			params.put("docsupid", topDoc.getDocid());
			params.put("isdelete", 0);
			params.put("orgid",orgId);
			params.put("doctype", 1);
			List<Doc> list = dao.getBySqlKey("getDocByParam", params);
			for (Doc doc : list) {
				if(name.equals(doc.getDocname())){
					return doc;
				}
			}
			return null;
		}
	}

	public Doc getbyDocName(String docName) {
		List<Doc> list = dao.getBySqlKey("getbyDocName", docName);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
