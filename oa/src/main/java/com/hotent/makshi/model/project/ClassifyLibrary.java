package com.hotent.makshi.model.project;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ClassifyLibrary implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1517239960043108318L;
	public final static Integer IS_LEAF_N = 1; // 不是叶子节点
	public final static Integer IS_LEAF_Y = 0; // 是叶子节点

	public final static String IS_PARENT_N = "false"; // 不是父类节点
	public final static String IS_PARENT_Y = "true"; // 是父类节点
	private Long id;

    private String name;

    private String charger;

    private String chargerID;

    private String org;

    private Long orgID;

    private Integer isused;

    private Integer isdelete;

    private Date ctime;

    private Long cuser;

    private Date utime;

    private Long uuser;
    
    private Integer order;

    private Long pronum;//附加字段 项目数
    private String admin;//附加字段 管理人
    private Long stagenum;//附加字段 阶段数
    private Long tasknum;//附加字段 任务数
    private Map<Long,Integer> orderMap;//任务排序字段集合或者分类库排序字段集合
    protected Long supid;
	/**
	 * 路径
	 */
	protected String path;
	/**
	 * 路径名称
	 */
	protected String pathname;
	
	private Short isRoot = 0;
	// 树展开
	private String open = "true";
	// 是否叶子结点(0否,1是),主要用于数据库保存
	private Integer isLeaf;
	// 是否父类,主要用于树的展示时用
	private String isParent;
    
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
    public Long getSupid() {
		return supid;
	}
    
	public void setSupid(Long supid) {
		this.supid = supid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Map<Long, Integer> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<Long, Integer> orderMap) {
		this.orderMap = orderMap;
	}

	public Long getPronum() {
		return pronum;
	}

	public void setPronum(Long pronum) {
		this.pronum = pronum;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public Long getStagenum() {
		return stagenum;
	}

	public void setStagenum(Long stagenum) {
		this.stagenum = stagenum;
	}

	public Long getTasknum() {
		return tasknum;
	}

	public void setTasknum(Long tasknum) {
		this.tasknum = tasknum;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getChargerID() {
        return chargerID;
    }

    public void setChargerID(String chargerID) {
        this.chargerID = chargerID;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Long getOrgID() {
        return orgID;
    }

    public void setOrgID(Long orgID) {
        this.orgID = orgID;
    }

    public Integer getIsused() {
        return isused;
    }

    public void setIsused(Integer isused) {
        this.isused = isused;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Long getCuser() {
        return cuser;
    }

    public void setCuser(Long cuser) {
        this.cuser = cuser;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public Long getUuser() {
        return uuser;
    }

    public void setUuser(Long uuser) {
        this.uuser = uuser;
    }
}