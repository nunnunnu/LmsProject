package com.project.lms.vo.member;

import java.util.List;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberListResponseVO {
    @Schema(description = "메세지") 
    private String message;
    @Schema(description = "총 페이지 수") 
    private Integer totalPage;
    @Schema(description = "현재 페이지") 
    private Integer page;
    @Schema(description = "상태값") 
    private Boolean status;
    @Schema(description = "코드") 
    private HttpStatus code;
    @Schema(description = "회원 리스트") 
    private List<MemberVO> memberList;
}
