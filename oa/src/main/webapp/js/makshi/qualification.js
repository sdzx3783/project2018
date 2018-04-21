             function changeUserId(){
                 $("#taker").on("change",function(){
                	 $("#borrowType").parent("td").find("span").remove();
                	 $("#realName").remove();
                	 getBorrowInfo(null);});
             }; 
             function changeTye(){
                 $("#borrowType").on("change",function(){
                	 $("#borrowType").parent("td").find("span").remove();
                	 $("#realName").remove();
                	 getBorrowInfo(null);});
             }
             function changeRealName(obj){
            	 getBorrowInfo(obj);
             }
             function getBorrowInfo(reUserId){
                var userName = $("input[name='m:certificate_borrow:fullname']").val();
                var userId=$("#taker").prev().val();
                if(!userId || userId.length==0){
                	userId = $("#realName").val();
                }
                if(reUserId && reUserId.length>0){
                	userId = reUserId;
                }
                var pointName = "m:certificate_borrow:steal_name";
                var type =  $("#borrowType").val();
                if(!type||type==0){
                	$("#steal_name_").val("-1");
                	$("#borrowId").val("");
                	$("#effictiveDate").val("");
                    return false;
                }
/*                if(!userId){
                     alert("请选择持证人！"); 
                    return false;                       
                }*/
                setBorrowParams(type,userId,userName,pointName);
             }
            //选择借阅证书类型
            function setBorrowParams(type,userId,userName,pointName){
            	if(!userId || userId.length==0){
            		$.post("/makshi/contract/contractBorrowApplication/getDuplicateName.ht",{userName:userName,type:type,userId:userId},function(data){
            			 if(data.ok==1){
            				 var html = "<option>请选择</option>";
            				 for(var i=0;i<data.data.length;i++){
            					 if(data.data[i].userId==0){
            						 html = html+'<option value=0>'+data.data[i].name+'(外)</option>';
            					 }else{
            						 html = html+'<option value='+data.data[i].userId+'>'+data.data[i].name+'('+data.data[i].account+')'+'</option>';
            					 }
            				 };
            				$("#borrowType").parent("td").append( 
            				"<span style='padding: 10px'>请选择具体持证人</span><select id='realName' onchange='changeRealName(this.value)' class='oa-new-select' >" +html+"</select>"
            				);
                         	return false;
                         }else{
                        	 userId = data.userId;
                        	 $("#steal_name_").html("<option value='-1'>-请选择-</option>");
                 			 $("#effictiveDate").val("");
                             getQualificationInfo(userId,userName,type,pointName);
                         }
            		});
            	}else{
            		getQualificationInfo(userId,userName,type,pointName);	
            	}	
                };
                //获取用户的证书信息
                var noHtml = '<input class="oa-new-input" type="text" readonly = "readonly" name="m:PracticeSteal:steal_name" value="无" validate="{&quot;maxlength&quot;:&quot;50&quot;}">';
            function getQualificationInfo(userId,userName,type,pointName){
                $.post("/makshi/contract/contractBorrowApplication/getInfoById.ht",{userId:userId,userName:userName,type:type},
                        function(data){
                                $("#borrowId").val("");
                                var result_=data;
                                if(result_.ok!=1){
                                	$("#steal_name").html(noHtml);
                                	$.ligerDialog.warn("无可借阅证书或印章","提示信息");
                                	return false;
                                }
                                var qualification = result_.data;
                                var selHtml='<option value="-1">-请选择-</option>';
                                if(qualification!=null && qualification!="" && qualification.length>0){
                                     for(var i=0;i<qualification.length;i++){
                                    	 if(type==4){
                                    		 if(qualification[i].name==1 &&  qualification[i].borrower!=null && qualification[i].borrower.length>0){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value=-1>三类人员安全生产考核合格证(已借出)</option>';
                                    		 }else 
                                    		 if(qualification[i].name==2 &&  qualification[i].borrower!=null && qualification[i].borrower.length>0){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value=-1>深圳市监理员(已借出)</option>';	 
                                    		 }else
                                    		 if(qualification[i].name==3 &&  qualification[i].borrower!=null &&   qualification[i].borrower.length>0){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value=-1>深圳市监理工程师(已借出)</option>';	
                                    		 }else
                                    		 if(qualification[i].name==4 &&  qualification[i].borrower!=null &&  qualification[i].borrower.length>0){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value=-1>水利部监理工程师信用手册(已借出)</option>';	 
                                    		 }else
                                    		 if(qualification[i].name==5 &&  qualification[i].borrower!=null &&   qualification[i].borrower.length>0){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value=-1>建设部监理工程师信用手册(已借出)</option>';
                                    		 }else
                                    		 if(qualification[i].name==6 &&  qualification[i].borrower!=null &&   qualification[i].borrower.length>0){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value=-1>深圳市档案员(已借出)</option>';	
                                    		 }else 
                                    		 if(qualification[i].name==1){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value="三类人员安全生产考核合格证">三类人员安全生产考核合格证</option>';
                                    		 }
                                    		 if(qualification[i].name==2){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value="深圳市监理员">深圳市监理员</option>';
                                    		 }
                                    		 if(qualification[i].name==3){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value="深圳市监理工程师">深圳市监理工程师</option>';
                                    		 }
                                    		 if(qualification[i].name==4){
                                    			selHtml+='<option data-num='+qualification[i].certifateId+' value="水利部监理工程师信用手册">水利部监理工程师信用手册</option>';
                                    		 }
                                    		 if(qualification[i].name==5){
                                    			selHtml+='<option data-num='+qualification[i].certifateId+' value="建设部监理工程师信用手册">建设部监理工程师信用手册</option>';
                                    		 }
                                    		 if(qualification[i].name==6){
                                    			 selHtml+='<option data-num='+qualification[i].certifateId+' value="深圳市档案员">深圳市档案员</option>';	 
                                    		 }
                                    	 }
	                                	 if(type==3){
		                                		 if(qualification[i].borrower!=null && qualification[i].borrower.length>0){ 
		                                        	 selHtml+='<option data-date="'+getdate(qualification[i].effictivedate)+'" data-num="'+qualification[i].certifateId+'" value=-1>'+qualification[i].name+'(已借出)</option>';
		                                		 }else{
		                                			 selHtml+='<option data-date="'+getdate(qualification[i].effictivedate)+'" data-num='+qualification[i].certifateId+' value="'+qualification[i].name+'">'+qualification[i].name+'</option>';
	                                		 }
	                                	 }
	                                	 if(type==1||type==2){
	                                		  if(qualification[i].borrower && qualification[i].borrower.length>0){ 
	                                			 selHtml+='<option data-num='+qualification[i].certifateId+' value=-1 >'+qualification[i].name+'(已借出)</option>';
	                                		  }else{
	                                         	selHtml+='<option data-num='+qualification[i].certifateId+' value="'+qualification[i].name+'">'+qualification[i].name+'</option>';
	                                		 }
	                                	 }
	                                	 }
                                var selHtml_='<select id="steal_name_" onchange="selchange();" class="oa-new-select" name=\"'+pointName+'\" right="w" val="">'+
                                selHtml+
                                '</select>' ;
                                    $("#steal_name").html(selHtml_);
                                    
                                }else{
                                	if(type==5||type==3){
                                		$("#steal_name").html(noHtml);
                                		$.ligerDialog.warn("无可借阅印章","提示信息");
                                		 return false;
                                	}
                                	$("#steal_name").html(noHtml);
                                	$.ligerDialog.warn("无可借阅证书或印章","提示信息");
                                    return false;
                                }
                                $("input[name='m:PracticeSteal:project_name']").focus();
                                }
                    );
            }
            
           //处理证书编号
           function selchange(){
                var sealId = $("#steal_name_").val();
                var type=$("#borrowType").val();
                if(-1==sealId){
                    $("#borrowId").val("");
                    $("#effictiveDate").val("");
                    return false;}
                $("#steal_name_ option").each(function(){
                	if($(this).attr("selected")=="selected"){
		                $("#borrowId").val($(this).attr("data-num"));
		                if(3==type){
		                	var effictiveDate = $(this).attr("data-date");
		                	if(effictiveDate){
		                		setAutoPropeties("effictiveDate",$(this).attr("data-date"),false,null,true,false,true);return;
		                	}
		                	$("#effictiveDate").val("");
		                }
                    } 
                });
            }
           /**
            * 转入、转出相关
            */
           //设置证书类型
           var obj = {
        		   common:["建设部监理工程师","建设部造价工程师","一级建造师","二级建造师","水利部总监","水利部监理工程师","水利部造价工程师","水利部监理员","注册设备监理工程师","注册安全工程师",],
        		   construct:["一级结构工程师","二级结构工程师","咨询工程师(投资)","投资项目管理师"],
        		   mapping:["测绘师"],
        		   tenders:["招标师"],
        		   information:["信息监理工程师","系统集成项目管理工程师"],
        		   equipment:["注册公用设备工程师"]
           };
           var certificationType = {
        		   common:1,construct:2,mapping:3,tenders:4,information:5,equipment:6
           };
          $(function(){
        	  $("#qualificationType").on("change",function(){
        		  var certificationName = $(this).val();
        		  var certificatoinKind = checkType(certificationName);
        		  if(certificatoinKind){
        			  $("input[name='m:personal_qualification:type']").val(certificatoinKind);
        			  //获取员工编号和姓名
        			  var userName = $("#fullname").val();
        			  var account = $("#account").val();
        			  if(!userName || userName.length==0){
        				  //未输入姓名
        				  alert("未输入姓名！");return false;
        			  }
        			  //通过姓名和证书名称进行信息查询和赋值
        			  setQualificationInfo(null,userName,certificationName);
        		  }else{
        			  $.ligerDialog.warn("该证书无对应的类型","提示信息"); 
        			  $("#qualificationType").val("");
        		  }
        		  });
          });
           function checkType(name){
        	   for(var key in obj){
        		   for(var i = 0;i<obj[key].length ; i++){
        			   if(name==obj[key][i]){
        				   return certificationType[key];
        			   }
        		   }
        	   }
           }
           //输入资格证书编号查询信息
           function changeQualificationInfo(){
           $("#qualificationId").on("change",function(){
               var id = $(this).val();
               setQualificationInfo(id);
           });
           }
           function setQualificationInfo(obj,userName,certificateType){
               if(!userName){
                   return false;
               };
               var defId = $("input[name='defId']").val();
               var userId="";
               if(defId==10000001130191){
            	   userId=$("input[name='curUserId']").val();
               }
               $.ajax({
                     type : "POST", 
                     url:"/platform/bpm/bpmFormQuery/doQuery.ht?",
                     data:{alias:"qualificationInfo",querydata:"{\"F_userId\":\""+userId+"\",\"F_name\":\""+userName+"\",\"F_certificate_type\":\""+certificateType+"\"}",page:1,pagesize:10}, 
                     dataType: "json",
                     success:function(data){ 
                         if(data!=null && data.list!=null && data.list.length>0){
                             var rowData=data.list[0];
                             var switchs = rowData.switchs;
                             var name = rowData.f_name;
                             var applicant = $("#fullname").val();
                             //转入流程
                             if(defId==10000001130191){
                                 if(1==switchs){
                                     $.ligerDialog.warn("该证书已转入!","提示信息"); 
                                     $("#qualificationId").val("");
                                     return false;
                                 }
                             }
                             //转出
                             if(defId==10000001130259){
                                 if("0"==switchs){
                                     $.ligerDialog.warn("该证书已转出!","提示信息");
                                     $("#qualificationId").val("");
                                     return false;
                                 }
                                 if(name.trim()!=applicant.trim()){
                                	 $.ligerDialog.warn("该证书不在申请人名下！","提示信息");
                                	 $("#qualificationId").val("");
                                     return false;
                                 }
                                 $("input[name='m:personal_qualification:userId']").val(rowData.f_userId);
                             }
                           //证书类型
                             var cerName = $("#mpersonal_qualificationcertificate_type_id").val();
                             if(cerName!=rowData.f_certificate_type){
                            	 $.ligerDialog.warn("证书编号和证书类型不匹配,请重新选择证书类型！","提示信息");
                            	 $("#qualificationId").val("");
                            	 return false;
                             }
                             // $(".dicComboTree.l-text-field").off("click");
                              //$(".l-trigger").off("click");
                             //Account
                             setAutoPropeties("account",rowData.f_account,false,false,false,false,true);
                             //毕业时间   
                             setAutoPropeties("graduate_date",rowData.f_graduation_date,false,null,true,true,false);
                             //证书编号
                             setAutoPropeties("qualificationId",rowData.f_certificate_id,false,false,false,false,true);
                             //名族
                             //setAutoPropeties("f_nation",rowData.f_nation,false);
                             //资格证书发证日期    
                             setAutoPropeties("certificateDate",rowData.f_certificate_date,false,null,true,true,false);
                             //执业编号
                             setAutoPropeties("certifiedId",rowData.f_certified_id,false);
                            //证书专业
                             setAutoPropeties("certificateMajor",rowData.f_certificate_major,false);
                             //资格证书签发单位
                             setAutoPropeties("sendUnit",rowData.f_send_unit,false);
                             //备注
                             setAutoPropeties("remark",rowData.f_remark,false);
                             //附件
                             setAttachmentInfo("attachmentHtml",rowData.f_attachment);
                             if(rowData.f_attachment&&rowData.f_attachment.length>0){
                            	$("#attachmentHtml").append('<input type="hidden" name="m:personal_qualification:attachment"  value='+rowData.f_attachment+'>');
                             }
                             setAttachmentInfo("registAttachmentHtml",rowData.f_regist_attachment);
                             if(rowData.f_regist_attachment&&rowData.f_regist_attachment.length>0){
                            	 $("#registAttachmentHtml").append('<input type="hidden" name="m:m:personal_qualification:regist_attachment"  value='+rowData.f_regist_attachment+'>');
                             }
                            /*  setType(rowData.f_get_type); */
                            // 是否已注册
                            setAutoPropeties("isregist",rowData.f_isregist,true,rowData.f_isregist);
                            //注册证书编号f_certificate_regist_id
                             setAutoPropeties("certificateRegistId",rowData.f_certificate_regist_id,false);
                            //注册号id="certificateRegistId"
                             setAutoPropeties("registId",rowData.f_regist_id,false);
                            //注册证书发证日期
                             setAutoPropeties("getDate",rowData.f_get_date,false,null,true);
                            //注册证书有效日期
                            setAutoPropeties("lastEffecticeDate",rowData.f_last_effectice_date,false,null,true);
                            //注册专业
                             setAutoPropeties("registMajor",rowData.f_regist_major,false);
                            //第二注册专业
                             setAutoPropeties("secondmajor",rowData.f_secondmajor,false);
                            //第三注册专业
                             setAutoPropeties("thirdmajor",rowData.f_thirdmajor,false);
                            //执业印章号
                            setAutoPropeties("sealId",rowData.f_seal_id,false);
                            //印章有效期
                            setAutoPropeties("effictiveDate",rowData.f_effictivedatea,false,null,true);
                            //注册证书发证单位
                            setAutoPropeties("registSendUnit",rowData.f_regist_send_unit,false);
                            //最新注册类别
                            setOption("registType",rowData.f_lasted_regist_type);
                            //继续教育完成情况
                            setAutoPropeties("keepEduStatus",rowData.f_keep_edu_status,false);
                            //最新注册日期
                            setAutoPropeties("lastedRegistDate",rowData.f_lasted_regist_date,false,null,true);
                            //备注
                            setAutoPropeties("registRemark",rowData.f_regist_remark,false);
                            //总学时 reg_period
                            setAutoPropeties("reg_period",rowData.f_reg_period,false);
                            //必修课学时
                            setAutoPropeties("reg_elective",rowData.f_reg_elective,false);
                            //选修课学时
                            setAutoPropeties("reg_compulsory",rowData.f_reg_compulsory,false);
                            //职称等级
                            var data = [
                                        rowData.f_positional_degree_one,
                                        rowData.f_positional_degree_two,
                                        rowData.f_positional_degree_three,
                                        ];
                            var majordata = [
                                               rowData.f_positional_major_one,
                                               rowData.f_positional_major_two,
                                               rowData.f_positional_major_three,
                                               ];
                            $("tr.degree").each(function(index, item){
                               var _index = index;
                               $(this).find("select").each(function(index, item){
                                   $(item).val(data[_index]);
                                   if(data[_index] && data[_index]!=null && data[_index].length>0){
                                	   $(item).attr("disabled","disabled");
                                	   $(item).parents("tr.degree").find("input").val(majordata[_index]);
                                	   $(item).parents("tr.degree").find("input").attr("readonly",true);
                                   }
                               }); 
                               
                            });
                            if(rowData.f_isregist){
                            $("input[type='radio']").each(function(){
                                if($(this).val()==rowData.f_isregist){
                                    $(this).attr("checked",true);
                                }
                            });
                            }
                         }else{
                             //转出
                             if(defId==10000001130259){
	                              $.ligerDialog.warn("未查询到该证书编号","提示信息"); 
	                              $("#qualificationId").val("");
                             }
                             //转入
                             if(defId==10000001130191){
	                             return;
                            }
                         }
                     },
                  error:function(){
                      $.ligerDialog.warn("未查询到该证书编号","提示信息"); 
                      $("#qualificationId").val("");
                  }
                 });
           } 

           //个人执业印章使用申请
           function getSealByUsrId(){
               var userId=$("#holder").prev().val();
               var name =$("#holder").val();
               setBorrowParams(3,userId,name,"m:PracticeSteal:steal_name");
           }
           function stealNameChange(obj){
               var value=$(obj).find("option:selected").val();
               $("#steal_name_value").val(value);
               var num=$(obj).find("option:selected").attr("data-num"); 
               $("#num").val(num);
           }
           
           function setAttachmentInfo(id,obj){
          	 var listHtml = '';
          	 var attachmentObjs = JSON.parse(obj);
          	 if(attachmentObjs.length>0){
          		 for(var i = 0 ; i < attachmentObjs.length ; i ++ ){
                  	 var attachmentObj = attachmentObjs[i];
                  	 listHtml = listHtml +
                  		  '<li>'+
                            '			<a  target="_blank" href="/platform/system/sysFile/file_'+attachmentObj.id+'.ht"  title='+attachmentObj.name+'>'+attachmentObj.name+'</a>'+
                			  '</li>';
                   } 
               }
          	 var attachmentHtml = 
                   '		<ul>'+listHtml+
					 '		</ul>';
                   $("#"+id).html(attachmentHtml);
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

/*           function getQualificationInfoByUsrId(){
               var userId=$("input[name='curUserId']").val();
               var date=new Date();
               var t=date.getTime();
                $.ajax({
                    type : "POST", 
                    url:"/makshi/title/vocationQualification/getByUserId.ht?t="+t,
                    data:{refid:userId},
                    dataType: "json",
                    success:function(data){ 
                       if(data!=null && data.result==1){
                           var qualification=eval("("+data.message+")");
                           var selHtml='<option value="-1">--------请选择--------</option>';
                            if(qualification!=null && qualification!="" && qualification.length>0){
                                for(var i=0;i<qualification.length;i++){
                                    selHtml+='<option data-num='+qualification[i].num+' value="'+qualification[i].name+'">'+qualification[i].name+'</option>';
                                }
                               $("#steal_name").html(selHtml);                             
                            }
                       }
                    }
                });
           }*/