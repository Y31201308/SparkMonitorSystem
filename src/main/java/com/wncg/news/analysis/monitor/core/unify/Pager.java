package com.wncg.news.analysis.monitor.core.unify;

import java.util.Collections;
import java.util.List;

public class Pager<T> {

	public static final int DEFAULT_EVERY_PAGE_COUNT = 50;
	public static final int EVERY_PAGE_MAX_COUNT = 500;
    public static final int EVERY_PAGE_MIN_COUNT = 10;
    public static final int DEFAULT_PAGE_SIZE = 1;
	
	private int pageSize = DEFAULT_EVERY_PAGE_COUNT;
	
	private int currentPageNum = DEFAULT_PAGE_SIZE;
	
	private List<T> data;
	
	public Pager(List<T> data, int pageSize , int pageNum){ 
		
        if (data == null || data.isEmpty()) {  
            throw new IllegalArgumentException("data must be not empty!");  
        }  
        
        if(pageSize < EVERY_PAGE_MIN_COUNT){
        	this.pageSize = EVERY_PAGE_MIN_COUNT;
        }else if(pageSize > EVERY_PAGE_MAX_COUNT){
            this.pageSize = EVERY_PAGE_MAX_COUNT;
        }else{
        	this.pageSize = pageSize;
        }
        
        if(pageNum < DEFAULT_PAGE_SIZE){
        	this.currentPageNum = DEFAULT_PAGE_SIZE;
        }else{
        	this.currentPageNum = pageNum;
        }
  
        this.data = data;  
    }  
	
	public List<T> getPagedList() {
		
        int fromIndex = (currentPageNum - 1) * pageSize;  
        if (fromIndex >= data.size()) {  
            return Collections.emptyList();  
        }  
  
        int toIndex = currentPageNum * pageSize;  
        if (toIndex >= data.size()) {  
            toIndex = data.size();  
        }  
        
        return data.subList(fromIndex, toIndex);  
    }  
  
    public int getPageSize() {
        return pageSize;
    }
  
    public List<T> getData() {
        return data;
    }

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	
}
