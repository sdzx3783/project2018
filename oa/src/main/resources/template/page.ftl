<style>
    /**
     * 该页面独享的分页样式
     */
	.oa-paging-wrap{
		padding:10px;
		border: 1px solid #eceff8;
		border-top: 0;
	}
	.oa-paging-select-wrap{
		display: inline-block;
        padding: 2px 5px;
        border: 1px solid #dadfed;
        -webkit-border-radius: 3px;
                border-radius: 3px;
    }
	.oa-paging-select{
		border: 0;
        padding: 0;
        outline: none;
        width: 100%;
        height: 18px;
        line-height: 18px;
	}
	.oa-paging-input-wrap{
		display: inline-block;
        padding: 2px 5px;
        border: 1px solid #dadfed;
        -webkit-border-radius: 3px;
                border-radius: 3px;
	}
	.oa-paging-input{
		text-align: center;
        border: 0;
        padding: 0;
        outline: none;
        width: 100%;
        height: 18px;
        line-height: 18px;
    }
</style>

<#setting number_format="0">
	<div id="oa_list_paging" class="oa-paging-wrap">
		<button type="button" class="oa-button-label" onclick="first('${tableIdCode}')" title="首页">首页</button> 
		<button type="button" class="oa-button-label" onclick="previous('${tableIdCode}');" title="上一页">上一页</button>
		<button type="button" class="oa-button-label" onclick="next('${tableIdCode}')" title="下一页">下一页</button>
		<button type="button" class="oa-button-label" onclick="last('${tableIdCode}')" title="尾页">尾页</button>
		<a class="oa-button-label" href="javascript: window.location.reload();">刷新</a>
		
		<!-- 跳页输入框 -->
		<div class="oa-paging-input-wrap oa-w30">
			<input value="${pageBean.currentPage}" maxlength="3" class="oa-paging-input" type="text" tableidcode="${tableIdCode}" id="navNum${tableIdCode}" name="navNum"/>
		</div>
		<span>/${pageBean.totalPage}页</span>
		<button type="button" class="oa-button-label" onclick="jumpTo('${tableIdCode}');">跳转</button>
		<!-- 刷新按钮 -->

		<#if (showPageSize)>
			<span>每页记录</span>
			<div class="oa-paging-select-wrap oa-w50">
				<select id="pageSize" name="pageSize" onchange="changePageSize(this,'${tableIdCode}');" class="oa-paging-select">
					<option value="5" <#if pageBean.pageSize==5> selected="selected" </#if>>5</option>
					<option value="10" <#if pageBean.pageSize==10> selected="selected" </#if>>10</option>
					<option value="15" <#if pageBean.pageSize==15> selected="selected" </#if>>15</option>
					<option value="20" <#if pageBean.pageSize==20> selected="selected" </#if>>20</option>
					<option value="50" <#if pageBean.pageSize==50> selected="selected" </#if>>50</option>
					<option value="100" <#if pageBean.pageSize==100> selected="selected" </#if>>100</option>
				</select>
			</div>
		</#if>

		<#if (showExplain) >
			<span>显示记录从${pageBean.first+1}到${pageBean.last}，总数${pageBean.totalCount}条</span>
		</#if>

		<input type="hidden" id="currentPage${tableIdCode}" name="currentPage" value="${pageBean.currentPage}"/>
		<input type="hidden" id="totalPage${tableIdCode}" name="totalPage" value="${pageBean.totalPage}"/>
		<input type="hidden" id="oldPageSize${tableIdCode}" name="oldPageSize" value="${pageBean.pageSize}"/>
		<a id="_nav${tableIdCode}" href="${baseHref}" style="display:none" ></a>
	</div>
	