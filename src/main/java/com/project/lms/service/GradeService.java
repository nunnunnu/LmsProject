package com.project.lms.service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.error.custom.NotFoundStudent;
import com.project.lms.error.custom.NotFoundSubject;
import com.project.lms.error.custom.NotFoundTest;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.GradeVO;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.grade.AddGradeVO;
import com.project.lms.vo.grade.PutGradeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeService {
	private final MemberInfoRepository mRepo;
    private final SubjectInfoRepository subRepo;        
    private final StudentInfoRepository stuRepo;
    private final TestInfoRepository tesRepo;
    private final TeacherInfoRepository teaRepo;
    private final GradeInfoRepository graRepo;
    public MapVO addGradeInfo(GradeVO data) {
		TeacherInfo tea = teaRepo.findById(data.getTeacher())
		.orElseThrow(()->new NotFoundMemberException());
		SubjectInfoEntity sub = subRepo.findById(data.getSubject())
		.orElseThrow(()->new NotFoundSubject());
		StudentInfo stu = stuRepo.findById(data.getStudent())
		.orElseThrow(()->new NotFoundStudent());
		TestInfoEntity tes = tesRepo.findById(data.getTest())
		.orElseThrow(()->new NotFoundTest());

		GradeInfoEntity entity = new GradeInfoEntity(null, sub, stu, tea, data.getAddGradeVO(), tes);
		graRepo.save(entity);
		return MapVO.builder().message("성적 입력 완료").code(HttpStatus.ACCEPTED).status(true).build(); 
	}

	    public MapVO putGradeInfo(AddGradeVO data,UserDetails details) {
		TeacherInfo tea = teaRepo.findByMiId(details.getUsername());
		YearMonth year = data.getYearmonth();//해당 월의 첫째 날
		LocalDate first = year.atDay(1);  //해당 월의 마지막 날
		LocalDate last = year.atEndOfMonth();  
		TestInfoEntity tes = tesRepo.findbyStartAndEnd(first, last);

		List<SubjectInfoEntity> subject= subRepo.findAll();

		SubjectInfoEntity reading = subject.get(0);
		SubjectInfoEntity vocabulary = subject.get(1);
		SubjectInfoEntity grammar = subject.get(2);
		SubjectInfoEntity listening = subject.get(3);
		List<GradeInfoEntity> result = new ArrayList<>();

			// if(subject.get(0).getSubName().equals("읽기")){
			// 	SubjectInfoEntity reading = subject.get(0);
			// } 
			// else if(subject.get(1).getSubName().equals("어휘")){
			// 	SubjectInfoEntity vocabulary = subject.get(1);
			// }
			// else if(subject.get(2).getSubName().equals("문법")){
			// 	SubjectInfoEntity grammar = subject.get(2);
			// }
			// else if(subject.get(3).getSubName().equals("문법")) {
			// 	SubjectInfoEntity listening = subject.get(3);
			// }
		

		for(int i = 0; i<data.getAddGradeVO().size(); i++ ){
		StudentInfo stu = stuRepo.findById(data.getAddGradeVO().get(i).getSeq()).orElse(null);
        GradeInfoEntity entity = new GradeInfoEntity(null, reading, stu, tea, data.getAddGradeVO().get(i).getReading(), tes);
        GradeInfoEntity entity2 = new GradeInfoEntity(null, vocabulary, stu, tea, data.getAddGradeVO().get(i).getVocabulary(), tes);
        GradeInfoEntity entity3 = new GradeInfoEntity(null, grammar, stu, tea, data.getAddGradeVO().get(i).getGrammar(), tes);
        GradeInfoEntity entity4 = new GradeInfoEntity(null, listening, stu, tea, data.getAddGradeVO().get(i).getListening(), tes);
		result.add(entity);
		result.add(entity2);
		result.add(entity3);
		result.add(entity4);
		}
		graRepo.saveAll(result);
		
		// GradeInfoEntity entity = new GradeInfoEntity(null, sub, stu, tea, data.getAddGradeVO(), tes);
		// graRepo.save(entity);
		return MapVO.builder().message("성적 입력 완료").code(HttpStatus.ACCEPTED).status(true).build();
}
}
