package com.project.lms.vo.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateFeedBackVO {
    private String title;
    private String modDt;
    private String content;
 
    
    public String getModDt(){
        return modDt;
    }
}
