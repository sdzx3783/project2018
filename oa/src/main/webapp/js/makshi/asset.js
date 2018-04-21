            //设置资产信息
             function autoLoadingAssetyList(obj){
                var asset_id = obj;
                var userName=$("input[name='curUserName']").val();
                if(!asset_id){return false;};
                var selectAssetById = "selectAssetById";
                var date=new Date();
                var t=date.getTime();
                $.ajax({
                      type : "POST", 
                      url:"/platform/bpm/bpmFormQuery/doQuery.ht?t="+t,
                      data:{alias:selectAssetById,querydata:"{F_asset_id:\""+asset_id+"\"}",page:1,pagesize:10},
                      dataType: "json",
                      success:function(data){ 
                          if(data!=null && data.list!=null && data.list.length>0){
                              var rowData=data.list[0];
                              var carePerson = rowData.f_care_person;
                              if(userName!=carePerson){
                                  alert("对不起，您不具有该资产");
                                  $("#assetId").val("");
                                  return false;
                              };
                              var isAandonment = rowData.f_state;
                              if(1==isAandonment){
                                  alert("对不起，该资产已报废!");
                                  $("#assetId").val("");
                                  return false;
                              }
                              setAutoPropeties("assetName",rowData.f_asset_name,false);
                              setAutoPropeties("specification",rowData.f_specification,false);
                              setAutoPropeties("getDate",rowData.f_get_date,false,null,true);
                              setAutoPropeties("number",rowData.f_number,false);
                              setType(rowData.f_get_type);
                          }else{
                              alert("固定资产编号不正确,请重新输入!");
                              $("#assetId").val("");
                          }
                      },
                   error:function(){
                       alert("固定资产编号不正确,请重新输入");
                       $("#assetId").val("");
                   }
                  });
            } 
            
             function setType(obj){
                 if(obj==0){
                   setAutoPropeties("getType","领用旧设备",false);
                 }
                 if(obj==1){
                   setAutoPropeties("getType","委托办公室购买",false);
                 }
                 if(obj==2){
                   setAutoPropeties("getType","项目部自行购买",false);
                 }
             }  
          