package com.hotent.makshi.model.doc;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.hotent.core.util.StringUtil;

public class DocFile {
    private Long dfid;

    private Long docid;

    private String title;

    private Integer version;

    private String filenum;

    private Integer status;

    private String keyword;

    private String creator;

    private String creatorid;

    private Date createtime;

    private String updateor;

    private String updateorid;

    private Date updatetime;

    private String file;
    
    private Integer isdelete;
    
    private Integer filecount;
    
    private String content;
    
    private String docname;
    

    public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	public Long getDfid() {
        return dfid;
    }

    public void setDfid(Long dfid) {
        this.dfid = dfid;
    }

    public Long getDocid() {
        return docid;
    }

    public void setDocid(Long docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFilenum() {
        return filenum;
    }

    public void setFilenum(String filenum) {
        this.filenum = filenum == null ? null : filenum.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid == null ? null : creatorid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdateor() {
        return updateor;
    }

    public void setUpdateor(String updateor) {
        this.updateor = updateor == null ? null : updateor.trim();
    }

    public String getUpdateorid() {
        return updateorid;
    }

    public void setUpdateorid(String updateorid) {
        this.updateorid = updateorid == null ? null : updateorid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public Integer getFilecount() {
		String filesJson = getFile();
		if(StringUtil.isEmpty(filesJson)){
			return 0;
		}
		JSONArray jsonArray = JSONArray.parseArray(filesJson);
		int size = jsonArray.size();
		return size;
	}

	public void setFilecount(Integer filecount) {
		
		this.filecount = filecount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}