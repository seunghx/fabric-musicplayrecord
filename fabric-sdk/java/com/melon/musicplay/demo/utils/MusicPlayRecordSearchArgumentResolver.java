package com.melon.musicplay.demo.utils;

import com.melon.musicplay.demo.model.MusicPlayRecordSearchRequest;
import com.melon.musicplay.demo.model.MusicPlayRecordSearchRequest.MusicPlayRecordSearchRequestWithDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MusicPlayRecordSearchArgumentResolver implements HandlerMethodArgumentResolver {

    @Value("${application.date.format}")
    private String dateFormat;
    private DateTimeFormatter dateFormatter;

    @PostConstruct
    private void initDateTimeFormat(){
        dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if(parameter.getParameterType() == MusicPlayRecordSearchRequest.class)
            return true;
        
        return false;
    }

    @Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                
            SearchType searchType = SearchType.valueOf(webRequest.getParameter("searchType"));
            
            switch(searchType){
                case SEARCH_BY_MUSIC:
                case SEARCH_BY_USER:
                    return createSearchRequest(webRequest, searchType);
                case SEARCH_BY_MUSIC_WITH_DATE:
                case SEARCH_BY_USER_WITH_DATE:
                    return createSearchWithDateRequest(webRequest, searchType);
                default :
                    return createSearchRequest(webRequest, searchType);
            }
    }

    private MusicPlayRecordSearchRequest createSearchRequest(WebRequest webRequest, SearchType searchType){
        MusicPlayRecordSearchRequest searchRequest = new MusicPlayRecordSearchRequest();
        searchRequest.setSearchType(searchType);
        setPageSize(searchRequest, webRequest);
        setBookmark(searchRequest, webRequest);
        setSearchKeyword(searchRequest, webRequest);

        return searchRequest;
    }

    private MusicPlayRecordSearchRequestWithDate createSearchWithDateRequest(WebRequest webRequest,SearchType searchType){
        MusicPlayRecordSearchRequestWithDate searchRequest = new MusicPlayRecordSearchRequestWithDate();
        searchRequest.setSearchType(searchType);
        setPageSize(searchRequest, webRequest);
        setBookmark(searchRequest, webRequest);
        setSearchKeyword(searchRequest, webRequest);
        setDateTimeString(searchRequest, webRequest);

        return searchRequest;
    }

    private <T extends MusicPlayRecordSearchRequest> void setPageSize(T searchRequest, WebRequest webRequest){
        String pageSize = webRequest.getParameter("pageSize");

        if(!StringUtils.isEmpty(pageSize) || isNumber(pageSize))
            searchRequest.setPageSize(pageSize);
    }

    private <T extends MusicPlayRecordSearchRequest> void setBookmark(T searchRequest, WebRequest webRequest){
        String bookmark = webRequest.getParameter("bookmark");

        if(!StringUtils.isEmpty(bookmark)){
            searchRequest.setBookmark(bookmark);
        }
    }

    private <T extends MusicPlayRecordSearchRequest> void setSearchKeyword(T searchRequest, WebRequest webRequest){
        String searchKeyword = webRequest.getParameter("searchKeyword");

        searchRequest.setSearchKeyword(searchKeyword);
    }

    private <T extends MusicPlayRecordSearchRequestWithDate> void setDateTimeString(
                                                    T searchRequest, WebRequest webRequest){
        String dateFormat = webRequest.getParameter("date");
        if(StringUtils.isEmpty(dateFormat)){
            dateFormat = dateFormatter.format(LocalDate.now());
        }
        searchRequest.setDate(dateFormat);
    }

    private boolean isNumber(String numberString){
        try{
            Integer.parseInt(numberString);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    //custom validator 생성
}