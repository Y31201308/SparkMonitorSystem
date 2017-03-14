package com.wncg.news.analysis.monitor.web.controller;

import com.wncg.news.analysis.monitor.core.event.Request;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.persistence.service.TrainSetService;
import com.wncg.news.analysis.monitor.web.model.TrainingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TrainSetController {

    @Autowired
    private TrainSetService trainSetService;

    @RequestMapping("/querytTrainset")
    public String queryTrainSet(@RequestParam(value="pageSize", required=false) Integer pageSize,
                                @RequestParam(value="pageNo", required=false) Integer pageNumberParam,
                                Model model){
        if (pageSize == null)
            pageSize = Request.DEFAULT_EVERY_PAGE_COUNT;
        if (pageNumberParam == null)
            pageNumberParam = Request.DEFAULT_PAGE_NUM;
        Request<TrainingSet> request = new Request<>(pageSize , pageNumberParam);

        Response<TrainingSet> response = trainSetService.queryTrainSet(request);

        System.out.println("response" + response);

        model.addAttribute("pageSize" , pageSize);
        model.addAttribute("totalCount" , response.getTotalCount());
        model.addAttribute("totalPage" , response.getTotalPage());
        model.addAttribute("trainsetList" , response.getData());

        return "training_set";
    }

}
