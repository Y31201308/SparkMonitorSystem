package com.wncg.news.analysis.monitor.core.persistence.service;

import com.wncg.news.analysis.monitor.core.event.OptionTrainSetRequest;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.event.TrainSetRequest;
import com.wncg.news.analysis.monitor.core.matadata.RequestFailedException;
import com.wncg.news.analysis.monitor.core.persistence.repository.TrainSetRepository;
import com.wncg.news.analysis.monitor.web.model.TrainSet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("trainSetService")
public class TrainSetServiceHander implements TrainSetService{

    @Autowired
    private TrainSetRepository trainSetRepository;


    @Override
    public Response queryTrainSetByPage(TrainSetRequest request) {
        Response<TrainSet> response = new Response<>();
        try {
            List<TrainSet> data = trainSetRepository.queryTrainSetByPage(request.getPageSize(), request.getCurrentPageNum());
            int count = trainSetRepository.queryTrainSetCount().intValue();

            response.setData(data);
            response.setPageSize(request.getPageSize());
            response.setCurrentPageNum(request.getCurrentPageNum());
            response.setTotalCount(count);
        }catch (Exception e){
            response.fail("查询训练集列表失败：" + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response queryTrainSetById(OptionTrainSetRequest request) {
        Response<TrainSet> response = new Response<>();
        try{
            if (StringUtils.isBlank(request.getTrainSetId()))
                throw new RequestFailedException("未接受到训练集ID");

            TrainSet trainSet = trainSetRepository.queryTrainSetById(request.getTrainSetId());

            if (trainSet == null)
                throw new RequestFailedException("通过训练集ID未获取到任何训练集");

            response.setData(trainSet);
        }catch (RequestFailedException e){
            response.fail("查询训练集失败："+e.getMessage());
        }catch (Exception e){
            response.fail("查询训练集失败："+e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response addTrainSet(TrainSetRequest request) {
        TrainSet trainSet = request.getTrainSet();
        Response response = new Response();
        try{
            if (StringUtils.isBlank(trainSet.getContent()))
                throw new RequestFailedException("训练集内容不能为空");

            if (trainSet.getLabelLev() == null)
                throw new RequestFailedException("训练集标签不能为空");

            trainSetRepository.addTrainSet(trainSet);
        }catch (RequestFailedException e){
            response.fail("添加训练集失败："+e.getMessage());
        }catch (Exception e){
            response.fail("添加训练集失败："+e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response editTrainSet(OptionTrainSetRequest request) {
        Response response = new Response();
        try {
            TrainSet trainSet = request.getTrainSet();
            if (trainSet == null)
                throw new RequestFailedException("训练集不能为空");

            if (trainSet.getId() == null)
                throw new RequestFailedException("训练集的ID号不能为空");

            if (trainSet.getContent() == null)
                throw new RequestFailedException("训练集的内容不能为空");

            if (trainSet.getLabelLev() == null && trainSet.getAccidentType() == null)
                throw new RequestFailedException("训练集的标签号不能为空");

            if (trainSet.getLabelLev() == null)
                trainSet.setLabelLev(trainSet.getAccidentType().getLabelTypeId());

            TrainSet oldTrainSet = trainSetRepository.queryTrainSetById(trainSet.getId());

            if (oldTrainSet == null)
                throw new RequestFailedException("通过训练集ID未获取到任何训练集");

            System.out.println("oldTrainSet====" + oldTrainSet);
            System.out.println("trainSet====" + trainSet);

            trainSetRepository.updateTrainSet(oldTrainSet, trainSet);
        }catch (RequestFailedException e){
            response.fail("编辑训练集失败：" + e.getMessage());
        }catch (Exception e){
            response.fail("编辑训练集失败：" + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response deleteTrainSet(OptionTrainSetRequest request) {
        String trainSetId = request.getTrainSetId();
        Response response = new Response();
        try{
            if (trainSetId == null)
                throw new RequestFailedException("未获取到训练集的id");

            TrainSet trainSet = trainSetRepository.queryTrainSetById(trainSetId);
            System.out.println("trainSet== "+ trainSet);
            if (trainSet == null)
                throw new RequestFailedException("根据提供的训练集Id未获取到指定的训练集");

            trainSetRepository.deleteTrainSet(trainSet);

        }catch (RequestFailedException e){
            response.fail("删除训练集失败：" + e.getMessage());
        }catch (Exception e){
            response.fail("删除训练集失败：" + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
