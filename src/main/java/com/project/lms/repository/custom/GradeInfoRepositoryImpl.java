package com.project.lms.repository.custom;

import java.util.List;

import com.project.lms.entity.QClassInfoEntity;
import com.project.lms.entity.QClassStudentEntity;
import com.project.lms.entity.QGradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.QStudentInfo;
import com.project.lms.vo.grade.StudentClassGradeVO;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class GradeInfoRepositoryImpl implements GradeInfoRepositoryCustom { 
    private final JPAQueryFactory queryfactory;

    public GradeInfoRepositoryImpl(EntityManager em) {
        this.queryfactory = new JPAQueryFactory(em);
    }

//     select si.mi_seq, mi.mi_name , mi.mi_birth ,
// 	ifnull((select sum(gi.gi_grade) from grade_info gi where gi.gi_test_seq =5 and gi_mi_seq1 = si.mi_seq  GROUP by gi.gi_mi_seq1),0) as totalSum	
// from student_info si 
// join member_info mi on si.mi_seq = mi.mi_seq 
// order by totalSum desc
    @Override
    public List<StudentClassGradeVO> studentClassChange(TestInfoEntity test) {
        return queryfactory
                .select(Projections.fields(StudentClassGradeVO.class,
                                QStudentInfo.studentInfo.miSeq.as("seq"),
                                QStudentInfo.studentInfo.miName.as("name"),
                                QStudentInfo.studentInfo.miBirth.as("birth"),
                                ExpressionUtils.as(JPAExpressions
                                        .select(QGradeInfoEntity.gradeInfoEntity.grade.sum())
                                        .from(QGradeInfoEntity.gradeInfoEntity)        
                                        .where(QGradeInfoEntity.gradeInfoEntity.test.eq(test),
                                            QGradeInfoEntity.gradeInfoEntity.student.miSeq.eq(QStudentInfo.studentInfo.miSeq)
                                        )
                                        .groupBy(QGradeInfoEntity.gradeInfoEntity.student)
                                , "total") 
                                ,QClassInfoEntity.classInfoEntity.ciName.as("originClass")
                                )
                            )
                .from(QStudentInfo.studentInfo)
                .join(QClassStudentEntity.classStudentEntity).on(QStudentInfo.studentInfo.miSeq.eq(QClassStudentEntity.classStudentEntity.student.miSeq))
                .join(QClassInfoEntity.classInfoEntity).on(QClassStudentEntity.classStudentEntity.classInfo.ciSeq.eq(QClassInfoEntity.classInfoEntity.ciSeq))
                .orderBy(new OrderSpecifier<>(Order.DESC, Expressions.path(Double.class, "total")))
                .fetch();
    }
}
