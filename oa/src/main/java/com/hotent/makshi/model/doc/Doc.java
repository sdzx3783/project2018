package com.hotent.makshi.model.doc;

import java.util.Date;

public class Doc {
	public final static Integer IS_LEAF_N=1; //不是叶子节点
	public final static Integer IS_LEAF_Y=0; //是叶子节点

	public final static String IS_PARENT_N="false"; //不是父类节点
	public final static String IS_PARENT_Y="true"; //是父类节点
	
	private Long docid;

    private Long orgid;

    private String docname;

    private Long docsupid;

    private Integer doctype;

    private String path;
    private String pathname;

    private String writeorgs;

    private String writeorgsID;

    private String readorgs;

    private String readorgsID;

    private String writeuser;

    private String writeuserID;

    private String readuser;

    private String readuserID;

    private Long creatorid;

    private Date createtime;

    private Long updateid;

    private Date updatetime;

    private Integer isdelete;
    private Short isRoot=0;
    //树展开
  	private String open="true";
  	// 是否叶子结点(0否,1是),主要用于数据库保存
  	private Integer isLeaf;
  	// 是否父类,主要用于树的展示时用
  	private String isParent;
  	
  	private Boolean isRead;//判断是否有进入目录权限（如果没有，弹出无权限对话框）
  	
  	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
		if(isLeaf!=null&&isLeaf==0){
			this.isParent=IS_PARENT_N;
		}else if(isLeaf!=null&&isLeaf>0){
			this.isParent=IS_PARENT_Y;
		}else{
			this.isParent=null;
		}
	}
	public String getIsParent() {
		if(this.isLeaf==null)return IS_PARENT_Y;
		else
		return this.isLeaf>0?IS_PARENT_Y:IS_PARENT_N;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
		if(isParent!=null&&isParent.equals(IS_PARENT_N)){
			this.isLeaf=IS_LEAF_Y;
		}else if(isParent!=null&&isParent.equals(IS_PARENT_Y)){
			this.isLeaf=IS_LEAF_N;
		}else{
			this.isLeaf=null;
		}
	}
	public Short getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(Short isRoot) {
		this.isRoot = isRoot;
	}
    public Long getDocid() {
        return docid;
    }

    public void setDocid(Long docid) {
        this.docid = docid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname == null ? null : docname.trim();
    }

    public Long getDocsupid() {
        return docsupid;
    }

    public void setDocsupid(Long docsupid) {
        this.docsupid = docsupid;
    }

    public Integer getDoctype() {
        return doctype;
    }

    public void setDoctype(Integer doctype) {
        this.doctype = doctype;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getWriteorgs() {
        return writeorgs;
    }

    public void setWriteorgs(String writeorgs) {
        this.writeorgs = writeorgs == null ? null : writeorgs.trim();
    }


    public String getReadorgs() {
        return readorgs;
    }

    public void setReadorgs(String readorgs) {
        this.readorgs = readorgs == null ? null : readorgs.trim();
    }

    public String getWriteuser() {
        return writeuser;
    }

    public void setWriteuser(String writeuser) {
        this.writeuser = writeuser == null ? null : writeuser.trim();
    }


    public String getReaduser() {
        return readuser;
    }

    public void setReaduser(String readuser) {
        this.readuser = readuser == null ? null : readuser.trim();
    }


    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getUpdateid() {
        return updateid;
    }

    public void setUpdateid(Long updateid) {
        this.updateid = updateid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

	public String getWriteorgsID() {
		return writeorgsID;
	}

	public void setWriteorgsID(String writeorgsID) {
		this.writeorgsID = writeorgsID;
	}

	public String getReadorgsID() {
		return readorgsID;
	}

	public void setReadorgsID(String readorgsID) {
		this.readorgsID = readorgsID;
	}

	public String getWriteuserID() {
		return writeuserID;
	}

	public void setWriteuserID(String writeuserID) {
		this.writeuserID = writeuserID;
	}

	public String getReaduserID() {
		return readuserID;
	}

	public void setReaduserID(String readuserID) {
		this.readuserID = readuserID;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	
    
}