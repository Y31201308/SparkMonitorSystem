package com.wncg.news.analysis.monitor.core.persistence.service;

import com.wncg.news.analysis.monitor.core.event.Request;
import com.wncg.news.analysis.monitor.core.event.Response;

public interface TrainSetService {

    Response queryTrainSet(Request request);

    Response addTrainSet(Request request);

    Response editTrainSet(Request request);

    Response deleteTrainSet(Request request);

}
