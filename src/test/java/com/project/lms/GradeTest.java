package com.project.lms;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.vo.grade.SameGrade;
import com.project.lms.vo.grade.SameGraderesponse;

@SpringBootTest
public class GradeTest {
    @Autowired private GradeInfoRepository gRepo;
    @Autowired private TestInfoRepository tRepo;
    @Autowired private StudentInfoRepository sRepo;

    @Test
    public void test(){
        TestInfoEntity test = tRepo.findById(5L).orElse(null);
        List<SameGrade> list = gRepo.sameGrade(test);
        List<SameGraderesponse> result = new ArrayList<>();
        for(SameGrade s : list){
            String[] seqs = s.getStudent().split(",");
            for(String seq : seqs){
                StudentInfo student = sRepo.findById(Long.parseLong(seq)).orElse(null);
                List<GradeInfoEntity> entities = gRepo.findByTestAndStudent(test, student);
                // result.add(new SameGraderesponse(entities,s.getTotalSum(), student));
            }
        }
    }
}
