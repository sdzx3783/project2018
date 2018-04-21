/**
 * Created by anthony on 2017/11/9.
 */
var apprOp = {
    //直接提交的流程，不用写弹出来的意见。需要对textarea赋ID，对应相应的流程节点。
    //dirSubmitProcs的KEY值为流程的formKey
    dirSubmitProcs: {
        seal_cancel: '',
        webartcontribute: ''
    },

    //页面类型
    pageTypes:{
        taskToStart: ''
    },

    //是否是新的这种，在dirSubmitProcs中定义。如果是，则返回true
    ifFormKeyInTheMap: function(key) {
        if(key!=null  && this.dirSubmitProcs.hasOwnProperty(key)) {
            return true;
        }
        return false;
    },

    ifShowAssMng: function() {
        if($('#td-textchmng').length > 0 && $('#chosemngtoshow').length > 0 && $('#chosemngtoshow').text().length == 0) {
            $('#td-textchmng').text('');
        }
    }
}

$(function(){

    apprOp.ifShowAssMng();

    //取审批意见
    if($("#dirSubDisOp").length > 0 && apprOp.dirSubmitProcs.hasOwnProperty($("#dirSubDisOp").val())) {
        var src = "/makshi/task/taskop/getopbyrunid.ht?runId=" + $("#runId").val();

        var currentNode=$("input[name='currentNode']").val();
        if ($('#opn_' + currentNode).length > 0) {
            //将当前流程节点的TEXTAREA置空
            $('#opn_' + currentNode).text('');
        }
        $.ajax({
            type:"get",
            url:src,
            success:function(data){
                if(data != null) {

                    var showContent;
                    var idPre = 'tdShowOpn_';
                    $.each(data, function(key, item) {

                        //这种页面的直接跳过，则continue
                        if(currentNode == key && apprOp.pageTypes.hasOwnProperty($('#pageType').val())) {

                            return true;
                        }

                        //没有这种特定设置的标签，则continue 或者设置了 dataOrig 直接continue
                        if ($('#' + idPre + key).length <= 0 || $('#' + idPre + key).attr('class').indexOf('dataOrig') > -1) {
                            return true;
                        }

                        // showContent = exclUndef(item.opinion) + ' ' + exclUndef(item.exeFullname) + '——' + exclUndef(item.endTime);
                        // $('#' + idPre + key).text(showContent);

                        if(item['items']) {
                            showContent = '';
                            $.each(item['items'], function(eachItemKey, eachItem) {
                                showContent += ( exclUndef(eachItem.opinion) + ' ' + exclUndef(eachItem.exeFullname) + '——' + exclUndef(eachItem.endTime) + '</br>');
                            });

                            $('#' + idPre + key).html(showContent);
                        } else {
                            showContent = exclUndef(item.opinion) + ' ' + exclUndef(item.exeFullname) + '——' + exclUndef(item.endTime);
                            $('#' + idPre + key).text(showContent);
                        }
                    })
                }
            }
        });
    }
});

var exclUndef = function (str) {
    if(str == null || typeof(str) == 'undefined')
        str = '';
    return str;
}