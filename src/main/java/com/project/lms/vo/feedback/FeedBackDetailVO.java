package com.project.lms.vo.feedback;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.project.lms.entity.CommentInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedBackDetailVO {

    private String writer;
    private String regDt;
    private String title;
    private String content;
    private List<CommentInsertVO> comment;
    
    public String getRegDt(){
        return regDt;
    }

}
