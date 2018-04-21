             function changePolitical(){
                 $("#politicalUserName").on("change",function(){
                    var name = $(this).val();
                   setPoliticalInfo(name);
                 });
            }
            function getAppliPoliticalInfo(){
            	var name = $("#politicalUserName").val();
            	if(!name){
            		return;
            	}
            	setPoliticalInfo($("#politicalUserName").val());
            }
            //党员档案
            function setPoliticalInfo(userName){
                    $.ajax({
                            type : "POST", 
                            url:"/platform/bpm/bpmFormQuery/doQuery.ht?",
                            data:{alias:"political_info",querydata:"{\"F_user_name\":\""+userName+"\"}",page:1,pagesize:10},
                            dataType: "json",
                            success:function(data){ 
                                if(data!=null && data.list!=null && data.list.length>0){
                                    var rowData=data.list[0];
                                    setOption("political_sex",rowData.sex);
                                    setAutoPropeties("belongBranch",rowData.f_now_branch,false);
                                   // setAutoPropeties("payMonth",rowData.f_pay_date,false,null,true);
                                }else{
                                    setOption("political_sex");
                                }
                            }
                        });
            }