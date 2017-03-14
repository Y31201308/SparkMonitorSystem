package com.wncg.news.analysis.monitor.web.controller;

import com.wncg.news.analysis.monitor.core.event.Request;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.persistence.service.NewsService;
import com.wncg.news.analysis.monitor.web.model.NewsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/queryNews")
    public String queryNews(@RequestParam(value="pageSize", required=false) Integer pageSize,
                            @RequestParam(value="pageNo", required=false) Integer pageNumberParam,
                            Model model){
        if (pageSize == null)
            pageSize = Request.DEFAULT_EVERY_PAGE_COUNT;
        if (pageNumberParam == null)
            pageNumberParam = Request.DEFAULT_PAGE_NUM;
        Request<NewsDetails> request = new Request<>(pageSize , pageNumberParam);

        Response<NewsDetails> response = newsService.queryNews(request);

        model.addAttribute("pageSize" , pageSize);
        model.addAttribute("totalCount" , response.getTotalCount());
        model.addAttribute("totalPage" , response.getTotalPage());
        model.addAttribute("newsList" , response.getData());

        return "news_details";
    }
}
