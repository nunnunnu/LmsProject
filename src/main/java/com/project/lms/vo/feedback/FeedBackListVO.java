package com.project.lms.vo.feedback;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.lms.entity.feedback.FeedbackInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
public class FeedBackListVO {
    private Long no;
    private String title;
    @JsonFormat(pattern ="yyyy-MM-dd")
    private LocalDateTime regDt;
    private String writer;


    public LocalDateTime getRegDt(){
        return regDt;
    }

    public FeedBackListVO(){

    }
    public FeedBackListVO(FeedbackInfo data){
        this.no = data.getFiSeq();
        this.title = data.getFiTitle();
        this.regDt = data.getCreatedDate();
        this.writer = data.getTeacher().getMiName();
    }
}
