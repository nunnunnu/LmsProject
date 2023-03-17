package com.project.lms.vo.feedback;

import java.time.LocalDateTime;
import java.util.Date;

import com.project.lms.entity.feedback.FeedbackInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@Builder
public class FeedBackListVO {
    private Long no;
    private String title;
    private String regDt;
    private String writer;


    public String getRegDt(){
        return regDt;
    }

    public FeedBackListVO(){

    }
    // public FeedBackListVO(FeedbackInfo data){
    //     this.no = data.getFiSeq();
    //     this.title = data.getFiTitle();
    //     this.regDt = data.getCreatedDate();
    //     this.writer = data.getTeacher().getMiName();
    // }
}
