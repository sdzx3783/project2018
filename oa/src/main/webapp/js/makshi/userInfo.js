
function autoSetUserInfo(){
                 var userId=$("input[name='curUserId']").val();
                 var alias="common_user_info_search";
                 var date=new Date();
                 var t=date.getTime();
                $.ajax({
                    type : "POST", 
                    url:"/platform/bpm/bpmFormQuery/doQuery.ht?t="+t,
                    data:{alias:alias,querydata:"{userId:"+userId+"}",page:1,pagesize:10},
                    dataType: "json",
                    success:function(data){ 
                        if(data!=null && data.list!=null && data.list.length>0){
                        	var defId = $("input[name='defId']").val();
                            var rowData=data.list[0];
                            setAutoPropeties("userDefId",rowData.userid,true);
                            setAutoPropeties("userId",rowData.userid,false);
                            setAutoPropeties("applicant",rowData.fullname,true,rowData.userid);
                            if(defId ==10000001130259){
                            	editGetInfo(rowData);
                            	return;
                            };
                            setAutoPropeties("account",rowData.account,false);
                            setAutoPropeties("fullname",rowData.fullname,false);
                            setAutoPropeties("f_sjdh",rowData.f_sjdh,false);
                            setAutoPropeties("positional",rowData.positional,false);
                            if(defId==10000001130191||defId==10000003950846){
                            	setAutoPropeties("education",rowData.education,false,false,false,false,true);
                            	setAutoPropeties("f_major",rowData.f_major,false,false,false,false,true);
                            	setAutoPropeties("graduate_school",rowData.graduate_school,false,false,false,false,true);
                            }else{
                            	setAutoPropeties("f_major",rowData.f_major,false);
                            	setAutoPropeties("education",rowData.education,false);
                            	setAutoPropeties("graduate_school",rowData.graduate_school,false);
                            	
                            }
                            setAutoPropeties("identification_id",rowData.identification_id,false);
                            setAutoPropeties("nation",rowData.nation,false);
                            setAutoPropeties("address",rowData.home_address,false);
                            setAutoPropeties("livingAddress",rowData.address,false);
                            setAutoPropeties("tel",rowData.mobile,false);
                            setAutoPropeties("birth",rowData.birthday,false,null,true);
                            setAutoPropeties("entryDate",rowData.entrydate,false,null,true);
                            setAutoPropeties("start_work_time",rowData.startworktime,false,null,true,true);
                            setAutoPropeties("posName",rowData.posname,true,rowData.posid);
                            setAutoPropeties("orgName",rowData.orgname,true,rowData.orgid);
                            $("#orgNameInput").val(rowData.orgname);
                            if(rowData.birthday){
                                getAge(rowData.birthday);
                            }
                            if(rowData.marriage_state){
                                setOption("marriage_state",rowData.marriage_state);
                            }
                            setSex(rowData.sex,false);
                            setFormalDate("formalDate",rowData.entrydate);
                            setOption("political_sex",rowData.sex);
                            setAutoPropeties("qq",rowData.qq);
                            setAutoPropeties("wechart",rowData.wechart);
                           // setAttachment(rowData.f_attachment);
                         //   $("#qualificationAttrachment").val(rowData.f_attachment);
                        }
                    }
                });
                
            }

			$(function(){
				certificationOutGetInfo();
			});
			function certificationOutGetInfo(){	
            $(".oa-new-input.outaccount").on("change",function(){
            	var account = $(this).val();
            	$.post("/makshi/qualification/personalQualification/getUserIdByAccount.ht",{account:account},function(data){
            		 if(data!=null && data!=''){
            			 var userId = data.userId;
            			 $.post("/platform/bpm/bpmFormQuery/doQuery.ht",{alias:"common_user_info_search",querydata:"{userId:"+userId+"}",page:1,pagesize:10},function(data){
            				 if(data!=null && data.list!=null && data.list.length>0){
            					 var rowData=data.list[0];
            					 editGetInfo(rowData);
            				 }
            			 });
            		 }else{
            				$.ligerDialog.warn("未查询到该员工编号","提示信息"); 
            				$(".oa-new-input.outaccount").val('');
            		 }
            	});
            });
			}
			
			function getProjectinfo(taskId){
				if(taskId && taskId.length>0){
					$.post("/makshi/project/project/getProjectinfo.ht",{'taskId':taskId},function(data){
						setAutoPropeties("projectName",data.projectName,false,false,false,true,false);
						setAutoPropeties("stageName",data.stageName,false,false,false,true,false);
						setAutoPropeties("taskName",data.taskName,false,false,false,true,false);
						setAutoPropeties("projectClassifyName",data.projectClassifyName,false,false,false,true,false);
						setAutoPropeties("projectContractNo",data.projectContractNo,false,false,false,true,false);
						setAutoPropeties("projectTaskId",data.projectTaskId,false,false,false,true,false);
						setProjectChargerSelector(data.charger,data.chargerID);
					});
				}
			}
			function setProjectChargerSelector(charger,chargerID){
				if(charger && charger.length>0 && chargerID && chargerID.length>0){
					/*$("input[name='m:construction_drawing_check:projectCharger']").val(charger);
					$("input[name='m:construction_drawing_check:projectChargerID']").val(chargerID);
					$("input[name='m:design_supervision_plan:examiner']").val(charger);
					$("input[name='m:design_supervision_plan:examinerID']").val(chargerID);
					$("input[name='m:drainage_monthly:projectCharger']").val(charger);
					$("input[name='m:drainage_monthly:projectChargerID']").val(chargerID);*/
					$("input[name='m:water_soil_keep_plan_report:charger']").val(charger)[0].style = "border:0 !important;";
					$("input[name='m:water_soil_keep_plan_report:chargerID']").val(chargerID);
					$("a[name='m:water_soil_keep_plan_report:charger']").hide();
					$("input[name='m:water_soil_keep_plan_approval:charger']").val(charger)[0].style = "border:0 !important;";
					$("input[name='m:water_soil_keep_plan_approval:chargerID']").val(chargerID);
					$("a[name='m:water_soil_keep_plan_approval:charger']").hide();
					
					$("input[name='m:hj_Regulatory_review:charger']").val(charger);
					$("input[name='m:hj_Regulatory_review:chargerID']").val(chargerID);
					$("input[name='m:hj_Supervision_monthly_report:project_leader']").val(charger);
					$("input[name='m:hj_Supervision_monthly_report:Project_leaderID']").val(chargerID);
					$("input[name='m:hj_Review_annual_report:Project_leader']").val(charger);
					$("input[name='m:hj_Review_annual_report:Project_leaderID']").val(chargerID);
					$("input[name='m:hj_Regulatory_scheme:project_leader']").val(charger);
					$("input[name='m:hj_Regulatory_scheme:Project_leaderID']").val(chargerID);
					
				}
			}
			
			function editGetInfo(rowData){
				 setAutoPropeties("fullname",rowData.fullname,false,false,false,false,true);
				 setAutoPropeties("nation",rowData.nation,false,false,false,false,true);
				 setAutoPropeties("identification_id",rowData.identification_id,false,false,false,false,true);
				 setAutoPropeties("graduate_school",rowData.graduate_school,false,false,false,false,true);
             	 setAutoPropeties("education",rowData.education,false,false,false,false,true);
             	 setAutoPropeties("f_major",rowData.f_major,false,false,false,false,true);
             	setSex(rowData.sex,true);
			}
			
			function setSpanInfo(id,val,dtfmt){
				if(val!=null &&  val!=""){
					if(dtfmt){
                        val = val.replace(/-/g,"/");
                        var date=new Date(val);
                        date=date.format("yyyy-MM-dd");
                         $("#"+id).text(date);
                    }else{
                        $("#"+id).text(val);
                    }
				}
			}
            function setAutoPropeties(id,val,isSelect,idValue,dtfmt,readonly,edit,textarea){
                if($("#"+id).length>0){
                    if(val!=null &&  val!=""){
                        if(dtfmt){
                            val = val.replace(/-/g,"/");
                            var date=new Date(val);
                            date=date.format("yyyy-MM-dd");
                             $("#"+id).val(date);
                        }else{
                            $("#"+id).val(val);
                        }
                        if(!edit){
                        	$("#"+id).attr("class","none-border-text");
                        	$("#"+id).attr("data-class","");
                        	$("#"+id).attr("readonly",true);
                        }
                        if(isSelect){//是否是选择器类型的插件
                            if($("#"+id).next().length>0){
                                $("#"+id).next().hide();
                            }
                            if($("#"+id).prev().length>0){
                                $("#"+id).prev().val(idValue);
                            }
                            
                        }
                    }
                    if(readonly){
                        $("#"+id).attr("readonly",true);
                    }
                }
            }
            
            function getAge(brith){
                var aDate=new Date();   
                var thisYear=aDate.getFullYear();
                brith=brith.substr(0,4);
                age=(thisYear-brith);
                setAutoPropeties("birthday",age,false);
                } ;
            //处理下拉框
            function setOption(id,val,isDisable){
                 if(val!=null &&  val!=""){
                $("#"+id+" option").each(function(){
                    if($(this).val()==val){
                        $(this).attr("selected","true");
                        if(!isDisable){
                        	$("#"+id).attr("disabled","disabled");
                        }
                    }
                });
                 }else{
                     $("#"+id).attr("disabled",false);
                 }
            }
            //转正日期
            function setFormalDate(id,entrydate){
            	 entrydate = dateFormat(entrydate);
                 var edate = new Date(entrydate);
                 edate.setMonth(edate.getMonth()+6);
                 $("#"+id).val(getdate(edate));
            }
            //日期转换
            function dateFormat(date){
            	date = date.replaceAll("-","/");
            	return date;
            }
            
            
            function getdate(date) {
            	if(null==date){
            		return '';
            	}
            	if("number"==(typeof date)){
            		var date = new Date(date);
            	}
            	if("string"==(typeof date)){
            		var date = new Date(dateFormat(date));
            	}
                var y = date.getFullYear();  
                var m = date.getMonth() + 1;  
                m = m < 10 ? '0' + m : m;  
                var d = date.getDate();  
                d = d < 10 ? ('0' + d) : d;  
                return y + '-' + m + '-' + d; 
            }
            
            
            
            
            function setSex(obj,edit){
                if(obj==0){
                    setAutoPropeties("sex","女",false,false,false,false,edit);
                }
                if(obj==1){
                    setAutoPropeties("sex","男",false,false,false,false,edit);
                }
            } 