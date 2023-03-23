package com.project.lms.repository.custom;

import java.util.List;

import com.project.lms.entity.QClassInfoEntity;
import com.project.lms.entity.QClassStudentEntity;
import com.project.lms.entity.QGradeInfoEntity;
import com.project.lms.entity.QSubjectInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.QStudentInfo;
import com.project.lms.vo.grade.StudentClassGradeVO;
import com.querydsl.core.BooleanBuilder;
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

    @Override
    public List<StudentClassGradeVO> studentClassChange(TestInfoEntity test) {
        return queryfactory
                .select(Projections.fields( //DTO의 필드명과 일치하는 컬럼을 매핑
                                StudentClassGradeVO.class, //최종 반환 타입
                                QStudentInfo.studentInfo.miSeq.as("seq"), //학생번호
                                QStudentInfo.studentInfo.miName.as("name"), //학생 이름
                                QStudentInfo.studentInfo.miBirth.as("birth"), //학생 생년월일
                                ExpressionUtils.as(JPAExpressions //select절 서브쿼리. 
                                        .select(QGradeInfoEntity.gradeInfoEntity.grade.sum()) //그룹화한 후 점수의 합계
                                        .from(QGradeInfoEntity.gradeInfoEntity)        
                                        .where(QGradeInfoEntity.gradeInfoEntity.test.eq(test), //파라미터로 받은 시험정보와 일치
                                            QGradeInfoEntity.gradeInfoEntity.student.miSeq.eq(QStudentInfo.studentInfo.miSeq) //원 쿼리에서 select절에 반환된 회원정보와 일치하는 정보만 조회
                                        )
                                        .groupBy(QGradeInfoEntity.gradeInfoEntity.student) //학생으로 그룹화
                                , "total") 
                                ,QClassInfoEntity.classInfoEntity.ciName.as("originClass")
                                )
                            )
                .from(QStudentInfo.studentInfo)
                .join(QClassStudentEntity.classStudentEntity).on(QStudentInfo.studentInfo.miSeq.eq(QClassStudentEntity.classStudentEntity.student.miSeq)) //학생의 기존 반 정보를 가져오기위해 연결테이블 조인
                .join(QClassInfoEntity.classInfoEntity).on(QClassStudentEntity.classStudentEntity.classInfo.ciSeq.eq(QClassInfoEntity.classInfoEntity.ciSeq)) //반의 이름을 가져오기위해 반테이블 조인
                .orderBy(new OrderSpecifier<>(Order.DESC, Expressions.path(Double.class, "total"))) //별칭으로 사용한 total으로 정렬하기 위해 사용
                .fetch(); //리스트로 조회
    }
}
