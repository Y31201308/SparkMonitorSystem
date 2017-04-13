package com.wncg.news.analysis.monitor.web.controller;

import com.wncg.news.analysis.monitor.core.event.*;
import com.wncg.news.analysis.monitor.core.matadata.EventResult;
import com.wncg.news.analysis.monitor.core.matadata.EventStatusType;
import com.wncg.news.analysis.monitor.core.matadata.RequestFailedException;
import com.wncg.news.analysis.monitor.core.persistence.service.TrainSetService;
import com.wncg.news.analysis.monitor.web.model.TrainSet;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TrainSetController {

    @Autowired
    private TrainSetService trainSetService;

    @RequestMapping("/queryTrainSetByPage")
    public String queryTrainSetByPage(@RequestParam(value="pageSize", required=false) Integer pageSize,
                                @RequestParam(value="pageNo", required=false) Integer pageNumberParam,
                                Model model){
        if (pageSize == null)
            pageSize = QueryNewsRequest.DEFAULT_EVERY_PAGE_COUNT;
        if (pageNumberParam == null)
            pageNumberParam = QueryNewsRequest.DEFAULT_PAGE_NUM;
        TrainSetRequest request = new TrainSetRequest(pageSize , pageNumberParam);

        Response<TrainSet> response = trainSetService.queryTrainSetByPage(request);

        model.addAttribute("pageSize" , pageSize);
        model.addAttribute("totalCount" , response.getTotalCount());
        model.addAttribute("totalPage" , response.getTotalPage());
        model.addAttribute("trainsetList" , response.getData());
        model.addAttribute("currentPageNum", response.getCurrentPageNum());

        return "training_set";
    }

    @RequestMapping("/addTrainSet")
    public String addTrainSet(@RequestParam(value="trainContext", required=false) String trainContext,
                              @RequestParam(value="labelType", required=false) String labelType,
                              final RedirectAttributes model){

        if (trainContext == null && labelType == null) {
            return "add_train_set";
        }

        try {
            TrainSet trainSet = new TrainSet();
            trainSet.setContent(trainContext);
            trainSet.setAccidentType(AccidentType.getAccidentTypeByName(labelType));
            trainSet.setCreateTime(new DateTime());

            TrainSetRequest request = new TrainSetRequest();
            request.setTrainSet(trainSet);

            Response response = trainSetService.addTrainSet(request);
            if(response.getStatus() == EventStatusType.NOK){
                model.addFlashAttribute("eventResult",  new EventResult(response.getStatus(), "添加训练集失败："+response.getResultMessage()));
            }else {
                model.addFlashAttribute("eventResult", new EventResult(response.getStatus(), "添加训练集成功：" + response.getResultMessage()));
            }
        }catch (RequestFailedException e){
            model.addFlashAttribute("eventResult",  new EventResult(EventStatusType.NOK, "添加训练集失败："+e.getMessage()));
        }

        return "redirect:/queryTrainSetByPage";
    }

    @RequestMapping("/deleteTrainSet")
    public String deleteTrainSet(@RequestParam(value = "trainSetId", required = true)String trainSetId,
                               final RedirectAttributes redirectAttributes){
        System.out.println("trainSetId: " + trainSetId);
        OptionTrainSetRequest request = new OptionTrainSetRequest();
        request.setTrainSetId(trainSetId);

        Response response = trainSetService.deleteTrainSet(request);
        if(response.getStatus() == EventStatusType.NOK){
            redirectAttributes.addFlashAttribute("eventResult",  new EventResult(response.getStatus(), "添加训练集失败："+response.getResultMessage()));
        }else {
            redirectAttributes.addFlashAttribute("eventResult", new EventResult(response.getStatus(), "添加训练集成功：" + response.getResultMessage()));
        }
        return "redirect:/queryTrainSetByPage";

    }

    @RequestMapping("/queryTrainSetById")
    public String queryTrainSetById(@RequestParam("trainSetId")String trainSetId, Model model){
        OptionTrainSetRequest request = new OptionTrainSetRequest();
        request.setTrainSetId(trainSetId);

        Response response = trainSetService.queryTrainSetById(request);
        if (response.getStatus() == EventStatusType.NOK){
            model.addAttribute("eventResult",  new EventResult(response.getStatus(), "查询训练集失败："+response.getResultMessage()));
            model.addAttribute("trainSet", new TrainSet());
        }else {
            model.addAttribute("trainSet", response.getData().get(0));
        }
        return "edit_train_set";
    }

    @RequestMapping("/editTrainSet")
    public String editTrainSet(@RequestParam("trainSetId")String trainSetId,
                               @RequestParam("trainContent")String trainContent,
                               @RequestParam("labelType")String labelType,
                               final RedirectAttributes redirectAttributes){

        System.out.println("trainSetId==="+trainSetId);
        System.out.println("trainContent==="+trainContent);
        System.out.println("labelType==="+labelType);

        TrainSet trainSet = new TrainSet();
        trainSet.setId(trainSetId);
        trainSet.setContent(trainContent);
        trainSet.setAccidentType(AccidentType.getAccidentTypeByName(labelType));
        OptionTrainSetRequest request = new OptionTrainSetRequest();
        request.setTrainSet(trainSet);

        Response response = trainSetService.editTrainSet(request);
        if (response.getStatus() == EventStatusType.NOK){
            redirectAttributes.addFlashAttribute("eventResult",  new EventResult(response.getStatus(), response.getResultMessage()));
        }else {
            redirectAttributes.addFlashAttribute("eventResult",  new EventResult(response.getStatus(), "编辑训练集成功"));
        }
        redirectAttributes.addAttribute("trainSetId", trainSetId);
        return "redirect:/queryTrainSetById";
    }

}
