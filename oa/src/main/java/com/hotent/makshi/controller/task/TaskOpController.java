package com.hotent.makshi.controller.task;

import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.service.bpm.TaskOpinionService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cat on 2017/11/8.
 */
@Controller
@RequestMapping("/makshi/task/taskop/")
public class TaskOpController extends BaseController {
    @Resource
    private TaskOpinionService taskOpinionService;


    /**
     * 根据RUNID取历史的审批数据，每一个审批人的取最新的一次审批数据。
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getopbyrunid")
    @ResponseBody
    public Map<String,JSONArray> getOpinionByRunid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long runId = RequestUtil.getLong(request, "runId", 0L);
        if (runId.equals(0L)) {
            return null;
        }
        List<TaskOpinion> list = taskOpinionService.getByRunId(runId);
        return listToJson4ApprOp(list);
    }


    /**
     * 将LIST转换为JSON，且，相同的getTaskKey返回getEndTimeStr大的一个;如果同一个节点有多人审批，则不同的人返回该审批人审批的最新时间的内容
     * @param list
     * @return
     */
    private JSONObject listToJson4ApprOp(List<TaskOpinion> list) {
        JSONObject json = new JSONObject();

        if(list != null && list.size() > 0) {
            JSONArray jsAr;
            Map<String, JSONObject> map = new HashMap<String, JSONObject>();

            //同一个人审的，取最新时间的审批意见
            SimpleDateFormat sf = new SimpleDateFormat(DateUtils.FORMAT_LONG);
            for(TaskOpinion task:list) {
                if(task == null || task.getTaskKey() == null || task.getTaskKey().length() == 0 ) {
                    continue;
                }

                if(task.getCheckStatus().intValue() == TaskOpinion.STATUS_INIT.intValue()
                        || task.getCheckStatus().intValue() == TaskOpinion.STATUS_CHECKING.intValue()) {
                    continue;
                }

                if(!map.containsKey(task.getTaskKey())) {
                    map.put(task.getTaskKey(), taskToJson(task));
                } else if((map.containsKey(task.getTaskKey()))) {

                    JSONObject matJson = map.get(task.getTaskKey());

                    //没有jsonarray
                    if (matJson.containsKey("items")) {
                        JSONArray jsArr =  (JSONArray) matJson.get("items");
                        JSONObject jso;
                        boolean added = false;

                        for (int i = 0; i < jsArr.size(); i++) {
                            jso = (JSONObject) jsArr.get(i);

                            if(jso != null && jso.containsKey("exeFullname")
                                    && (jso.get("exeFullname") + "").equals(task.getExeFullname())) {

                                added = true;
                                //同样名字取最新时间的。
                                if((task.getEndTimeStr().compareTo(jso.get("endTime") + "")) > 0) {
                                    jsArr.element(i, taskToJson(task));
                                }
                                break;
                            }
                        }

                        if(!added) {
                            jsArr.add(taskToJson(task));
                        }

                    //名字相同
                    } else if((matJson.get("exeFullname") + "").equals(task.getExeFullname())){
                        //且时间更加新
                        if((task.getEndTimeStr().compareTo(matJson.get("endTime") + "")) > 0) {
                            map.put(task.getTaskKey(), taskToJson(task));
                        } else {
                            continue;
                        }
                    //名字不同
                    } else {
                        jsAr = new JSONArray();
                        jsAr.add(matJson);
                        jsAr.add(taskToJson(task));

                        JSONObject jsTemp = new JSONObject();
                        jsTemp.put("items", jsAr);
                        map.put(task.getTaskKey(), jsTemp);
                    }
                }
            }

            json = JSONObject.fromObject(map);
        }
        return  json;
    }

    /**
     * 把TASK对象转换为JSON
     * @param task
     * @return
     */
    private JSONObject taskToJson(TaskOpinion task) {
        if(task == null)
            return null;
        JSONObject temp = new JSONObject();
        temp.put("opinionId", task.getOpinionId());
        temp.put("taskName", task.getTaskName());
        temp.put("exeFullname", task.getExeFullname());
        temp.put("opinion", task.getOpinion());
        temp.put("endTime", task.getEndTimeStr());

        return temp;
    }
}
