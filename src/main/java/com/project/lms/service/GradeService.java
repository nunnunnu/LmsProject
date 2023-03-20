package com.project.lms.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.error.custom.NotFoundStudent;
import com.project.lms.error.custom.NotFoundSubject;
import com.project.lms.error.custom.NotFoundTest;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.GradeVO;
import com.project.lms.vo.MapVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final SubjectInfoRepository subRepo;        
    private final StudentInfoRepository stuRepo;
    private final TestInfoRepository tesRepo;
    private final TeacherInfoRepository teaRepo;
    private final GradeInfoRepository graRepo;
    public MapVO addGradeInfo(GradeVO data) {
		Map<String, Object> map = new LinkedHashMap<>();
		TeacherInfo tea = teaRepo.findById(data.getTeacher())
		.orElseThrow(()->new NotFoundMemberException());
		SubjectInfoEntity sub = subRepo.findById(data.getSubject())
		.orElseThrow(()->new NotFoundSubject());
		StudentInfo stu = stuRepo.findById(data.getStudent())
		.orElseThrow(()->new NotFoundStudent());
		TestInfoEntity tes = tesRepo.findById(data.getTest())
		.orElseThrow(()->new NotFoundTest());

		GradeInfoEntity entity = new GradeInfoEntity(null, sub, stu, tea, data.getGrade(), tes);
		graRepo.save(entity);
		return MapVO.builder().message("성적 입력 완료").code(HttpStatus.ACCEPTED).status(true).build(); 
	}
}
