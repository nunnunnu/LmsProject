package com.project.lms.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {
    String id;
    String pwd;
}
