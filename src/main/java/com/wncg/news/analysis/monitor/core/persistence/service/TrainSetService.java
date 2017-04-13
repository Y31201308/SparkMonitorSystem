package com.wncg.news.analysis.monitor.core.persistence.service;

import com.wncg.news.analysis.monitor.core.event.OptionTrainSetRequest;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.event.TrainSetRequest;

public interface TrainSetService {

    Response queryTrainSetByPage(TrainSetRequest request);

    Response queryTrainSetById(OptionTrainSetRequest request);

    Response addTrainSet(TrainSetRequest request);

    Response editTrainSet(OptionTrainSetRequest request);

    Response deleteTrainSet(OptionTrainSetRequest request);

}
