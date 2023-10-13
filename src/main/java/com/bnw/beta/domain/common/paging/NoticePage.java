package com.bnw.beta.domain.common.paging;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NoticePage {

    private int total;
    private List<NoticeDTO> noticeList;
    private List<NoticeDTO> topNoticeList; // 상단 노출 게시글 목록
    private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;
    private int startIndex;
    private int listCnt;
    private String searchType;
    private String keyword = "";

    public NoticePage(int total, int currentPage, int size, List<NoticeDTO> noticeList,List<NoticeDTO> topNoticeList, String searchType, String keyword){
        this.total=total;
        this.currentPage=currentPage;
        this.noticeList=noticeList;
        this.topNoticeList=topNoticeList;
        this.searchType = searchType;
        this.keyword = keyword;
        if(total==0) {
            totalPages=0;
            startPage=0;
            endPage=0;
        }else {

            totalPages=total/size;
            if(total%size>0) {
                totalPages++;
            }

            int modVal = currentPage%5;
            startPage=currentPage/5*5+1;
            if(modVal==0)  startPage=startPage-5;

            endPage=startPage+4;
            if(endPage>totalPages) endPage=totalPages;

            startIndex = (currentPage - 1) * size;
        }
    }
}