package com.wncg.news.analysis.monitor.web.controller;

import com.wncg.news.analysis.monitor.core.event.DeleteNewsRequest;
import com.wncg.news.analysis.monitor.core.event.QueryNewsRequest;
import com.wncg.news.analysis.monitor.core.event.Response;
import com.wncg.news.analysis.monitor.core.event.UpdateNewsRequest;
import com.wncg.news.analysis.monitor.core.matadata.EventResult;
import com.wncg.news.analysis.monitor.core.matadata.EventStatusType;
import com.wncg.news.analysis.monitor.core.persistence.service.NewsService;
import com.wncg.news.analysis.monitor.web.model.NewsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/queryNewsByPage")
    public String queryNewsByPage(@RequestParam(value="pageSize", required=false) Integer pageSize,
                            @RequestParam(value="pageNo", required=false) Integer pageNumberParam,
                            Model model){
        if (pageSize == null)
            pageSize = QueryNewsRequest.DEFAULT_EVERY_PAGE_COUNT;
        if (pageNumberParam == null)
            pageNumberParam = QueryNewsRequest.DEFAULT_PAGE_NUM;

        QueryNewsRequest request = new QueryNewsRequest(pageSize , pageNumberParam);

        Response<NewsDetails> response = newsService.queryNewsByPage(request);

        model.addAttribute("pageSize" , pageSize);
        model.addAttribute("totalCount" , response.getTotalCount());
        model.addAttribute("totalPage" , response.getTotalPage());
        model.addAttribute("newsList" , response.getData());
        model.addAttribute("currentPageNum", response.getCurrentPageNum());

        return "news_details";
    }

    @RequestMapping("/deleteNews")
    public String deleteNews(@RequestParam(value="newsUrl", required=true) String newsUrl,
                             final RedirectAttributes redirectAttributes){
        DeleteNewsRequest request = new DeleteNewsRequest();
        request.setNewsUrl(newsUrl);
        Response response = newsService.deleteNews(request);
        if(response.getStatus() == EventStatusType.NOK){
            redirectAttributes.addFlashAttribute("eventResult",  new EventResult(response.getStatus(), "删除数据失败："+response.getResultMessage()));
        }else {
            redirectAttributes.addFlashAttribute("eventResult", new EventResult(response.getStatus(), "删除数据成功：" + response.getResultMessage()));
        }
        return "redirect:/queryNewsByPage";
    }

    @RequestMapping("/queryNewsByUrl")
    public String queryNewsByUrl(@RequestParam(value="newsUrl", required=true) String newsUrl, Model model){
        System.out.println("newsUrl====" + newsUrl);
        QueryNewsRequest request = new QueryNewsRequest();
        request.setNewsUrl(newsUrl);
        Response<NewsDetails> response = newsService.queryNewsById(request);

        model.addAttribute("newsDetails" , response.getData().get(0));
        return "edit_news";
    }

    @RequestMapping("/editNews")
    public String editNews(NewsDetails newsDetails, final RedirectAttributes redirectAttributes){
        UpdateNewsRequest request = new UpdateNewsRequest();
        request.setNewsDetails(newsDetails);
        Response response = newsService.updateNews(request);
        if(response.getStatus() == EventStatusType.NOK){
            redirectAttributes.addFlashAttribute("eventResult",  new EventResult(response.getStatus(), response.getResultMessage()));
        }else {
            redirectAttributes.addFlashAttribute("eventResult", new EventResult(response.getStatus(), response.getResultMessage()));
        }
        redirectAttributes.addAttribute("newsUrl", newsDetails.getNewsUrl());
        return "redirect:/queryNewsByUrl";
    }

}
