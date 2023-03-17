package com.project.lms;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.vo.grade.StudentClassGradeVO;

@SpringBootTest
public class GradeTest {
    @Autowired private GradeInfoRepository gRepo;
    @Autowired private TestInfoRepository tRepo;
    @Autowired private StudentInfoRepository sRepo;
    @Autowired private ClassInfoRepository cRepo;

    @Test
    public void test(){
        TestInfoEntity test = tRepo.findById(5L).orElse(null);
        // List<SameGrade> list = gRepo.sameGrade(test);
        // List<SameGraderesponse> result = new ArrayList<>();
        // for(SameGrade s : list){
        //     String[] seqs = s.getStudent().split(",");
        //     for(String seq : seqs){
        //         StudentInfo student = sRepo.findById(Long.parseLong(seq)).orElse(null);
        //         List<GradeInfoEntity> entities = gRepo.findByTestAndStudent(test, student);
        //         // result.add(new SameGraderesponse(entities,s.getTotalSum(), student));
        //     }
        // }

        List<StudentClassGradeVO> list = gRepo.studentClassChange(test);
        Integer totalStudent = sRepo.countByMiStatus(true);
        System.out.println(totalStudent);
        
        List<ClassInfoEntity> classInfo = cRepo.findAllByOrderByCiRating();
        Integer percent = (int) Math.ceil((double)totalStudent/classInfo.size());

        for(int i=1;i<=list.size();i++){
            StudentClassGradeVO s = list.get(i-1);
            for(int j=1;j<=classInfo.size();j++){
                if(i<=percent*j && i>=percent*j-1){
                    s.changeClassAndStatusSetting(classInfo.get(j-1).getCiName());
                }
            }
        }
        System.out.println(list);
    }
}
