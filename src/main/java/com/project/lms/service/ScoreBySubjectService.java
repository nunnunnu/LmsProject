package com.project.lms.service;
import java.util.LinkedList;
import java.util.List;

import com.project.lms.entity.member.StudentInfo;
import org.springframework.stereotype.Service;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.vo.request.ScoreListBySubjectVO;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreBySubjectService {
    private StudentInfoRepository studentInfoRepository;
    private GradeInfoRepository gradeInfoRepository;


    public ScoreListBySubjectResponseVO getSubjectList(String id) {
		StudentInfo memEntity = studentInfoRepository.findByMiId(id);
		List<GradeInfoEntity> stuList = gradeInfoRepository.findByStudent(memEntity);


		List<ScoreListBySubjectVO> voList = new LinkedList<>();
		for (GradeInfoEntity grade : stuList) {
			ScoreListBySubjectVO vo = ScoreListBySubjectVO.builder()
					.subjectName(grade.getSubject().getSubName())
					.grade(grade.getGrade()).build();
			voList.add(vo);
		}
		ScoreListBySubjectResponseVO result = ScoreListBySubjectResponseVO.builder().message("성공").status(true)
				.scoreList(voList).build();
		return result;
	}
}
