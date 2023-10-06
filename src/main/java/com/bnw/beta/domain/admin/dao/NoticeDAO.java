package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeFileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface NoticeDAO {
    
    //게시글 등록
    Long insert(NoticeDTO noticeDTO);

    //파일 등록
    void fileUpload(NoticeFileDTO noticeFileDTO);

    //게시글 상세정보 조회
    NoticeDTO detail(Long notice_no);

    //파일 상세정보 조회
    List<NoticeFileDTO> getNoticeFiles(Long notice_no);

    //게시글 수정
    int update(NoticeDTO noticeDTO);

    //파일 삭제
    List<NoticeFileDTO> deleteFile(Long notice_no);

    //게시글 목록 조회
    List<NoticeDTO> noticeList(NoticeDTO noticeDTO);



}
