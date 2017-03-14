package com.wncg.news.analysis.monitor.core.persistence.service;

import com.wncg.news.analysis.monitor.core.event.Request;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.persistence.repository.TrainSetRepository;
import com.wncg.news.analysis.monitor.web.model.TrainingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("trainSetService")
public class TrainSetServiceHander implements TrainSetService{

    @Autowired
    private TrainSetRepository trainSetRepository;


    @Override
    public Response queryTrainSet(Request request) {
        List<TrainingSet> data = trainSetRepository.queryTrainSet(request.getPageSize(), request.getCurrentPageNum());
        int count = trainSetRepository.queryTrainSetCount().intValue();

//        System.out.println("count :" + count);

        Response<TrainingSet> response = new Response<>();

        response.setData(data);
        response.setPageSize(request.getPageSize());
        response.setCurrentPageNum(request.getCurrentPageNum());
        response.setTotalCount(count);


        return response;
    }

    @Override
    public Response addTrainSet(Request request) {
        return null;
    }

    @Override
    public Response editTrainSet(Request request) {
        return null;
    }

    @Override
    public Response deleteTrainSet(Request request) {
        return null;
    }
}
