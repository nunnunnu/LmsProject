package com.project.lms.vo.member;

import java.time.LocalDate;

import com.project.lms.entity.member.MemberInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVO {
    @Schema(description = "회원 고유번호") 
    Long seq;
    @Schema(description = "회원 타입") 
    String role;
    @Schema(description = "회원 명") 
    String name;
    @Schema(description = "회원 생년월일") 
    LocalDate birth;
    @Schema(description = "회원 이메일") 
    String email;
    @Schema(description = "회원 등록일") 
    LocalDate regDt;


    public MemberVO(MemberInfoEntity entity) {
        this.seq = entity.getMiSeq();
        this.role = entity.getMiRole().toString();
        this.name = entity.getMiName();
        this.birth = entity.getMiBirth();
        this.email = entity.getMiEmail();
        this.regDt = entity.getMiRegDt();
    }
    
    
}
