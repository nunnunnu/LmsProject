package com.project.lms.vo.member;

import com.project.lms.entity.member.StudentInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "반 소속 학생 리스트")
public class ClassStudentListVO {
    @Schema(description = "학생 번호")
    private Long seq;
    @Schema(description = "학생 이름")
    private String name;

    public ClassStudentListVO(StudentInfo stu){
        this.seq = stu.getMiSeq();
        this.name = stu.getMiName();
    }
}
