package com.project.lms.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailVO {
    @JsonIgnore
    private String address;
    @JsonIgnore
    private String title;
    @JsonIgnore
    private String message;
    private String msg;
    
}
