package com.hotent.makshi.model.fontsize;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:公文字号 Model对象
 */
public class FontSize extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3492361425277636180L;
	//主键
	protected Long id;
	/**
	 *公文类型
	 */
	protected String  type;
	/**
	 *发文字号
	 */
	protected String  font_size;
	/**
	 * 发文年份
	 * @return
	 */
	protected String dispatch_year;
	/**
	 * 类型名称
	 * @returnff
	 */
	protected String typeName;
	
	
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDispatch_year() {
		return dispatch_year;
	}
	public void setDispatch_year(String dispatch_year) {
		this.dispatch_year = dispatch_year;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 公文类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setFont_size(String font_size) 
	{
		this.font_size = font_size;
	}
	/**
	 * 返回 发文字号
	 * @return
	 */
	public String getFont_size() 
	{
		return this.font_size;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof FontSize)) 
		{
			return false;
		}
		FontSize rhs = (FontSize) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.type, rhs.type)
		.append(this.font_size, rhs.font_size)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.type) 
		.append(this.font_size) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("type", this.type) 
		.append("font_size", this.font_size) 
		.toString();
	}

}