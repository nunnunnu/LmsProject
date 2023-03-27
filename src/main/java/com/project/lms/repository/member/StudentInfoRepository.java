package com.project.lms.repository.member;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.StudentInfo;

public interface StudentInfoRepository extends JpaRepository<StudentInfo ,Long> {
    StudentInfo findByMiId(String id);

    Integer countByMiStatus(Boolean status);

    List<StudentInfo> findByMiSeqIn(long[] seqs);
    
    //상위 10% 학생을 구하기 위한 쿼리문
    // Select  gi_mi_seq1  from grade_info gi where gi_test_seq =6 group by gi_mi_seq1 order by sum(gi.gi_grade) desc limit 10[위 쿼리문의 총 응시자수 /10한 값]
    // select gi.gi_mi_seq1  from student_info si join grade_info gi on gi.gi_mi_seq1 = si.mi_seq where gi.gi_test_seq =6 group by gi_mi_seq1 order by sum(gi.gi_grade) DESC limit 10
    @Query("SELECT s FROM StudentInfo s JOIN GradeInfoEntity g on g.student = s.miSeq WHERE g.test = :test GROUP BY s ORDER BY SUM(g.grade) DESC limit :cut")
    List<StudentInfo> findTop10List(@Param("test") TestInfoEntity test, @Param("cut") Integer cut); // test에 대한 학생들의 총점을 계산하여, 총점이 높은 순으로 상위 10개의 학생정보(StudentInfo)를 조회

    Page<StudentInfo> findByMiNameContaining(String name, Pageable pageable);
}
