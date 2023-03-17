package com.project.lms.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.error.ErrorResponse;
import com.project.lms.service.ClassService;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.member.ClassStudentListVO;
import com.project.lms.vo.response.ClassResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
@Tag(description = "반 변경 API입니다.", name = "반")
public class ClassAPIController {
    private final ClassService cService;
    @Operation(summary = "반 변경")
    @PostMapping(value = "/{stuSeq}/{classSeq}")
    public ResponseEntity<ClassResponseVO> updateClass(@Parameter(name = "stuSeq", description = "학생 번호")@PathVariable Long stuSeq, 
    @Parameter(name = "classSeq", description = "변경할 반 번호") @PathVariable Long classSeq) {
        return new ResponseEntity<>(cService.updateClass(stuSeq,classSeq), HttpStatus.OK);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "토큰이 들어오지 않았거나, 회원의 등급이 선생님이 아닐때 접근이 차단됩니다. 토큰을 넣었는데 403에러가뜨면 로그인한 회원의 분류를 확인해주세요", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "해당 토큰에서 회원을 찾을 수 없습니다. 엑세스토큰을 확인해주세요 or 담당된 반이 없는 선생님의 계정이거나 조회할 컨텐츠가 없습니다.(반에 소속된 학생 데이터가 아직 없음)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "200", description = "조회성공", content = @Content(array=@ArraySchema(schema = @Schema(implementation = ClassStudentListVO.class))))})
    @Operation(summary = "선생님의 반 소속 학생 조회", description ="일단 회원번호와 이름만 표기되게했습니다. 추가로 필요한정보가 있으면 말씀해주세요", security = @SecurityRequirement(name = "bearerAuth"),
        parameters = {
            @Parameter(in = ParameterIn.QUERY
                                , description = "페이지번호(0부터 시작), 입력안하면 0페이지 조회"
                                , name = "page"
                                , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
            @Parameter(in = ParameterIn.QUERY
                                , description = "입력안해도됨. 기본 한 페이지 당 8개 씩"
                                , name = "size"),
            @Parameter(in = ParameterIn.QUERY
                                , description = "입력안해도됨. 기본 최신 가입순 정렬"
                                , name = "sort")
        })
    @GetMapping("/student")
    @Secured("ROLE_TEACHER") //선생님만 조회가능하도록 권한 설정. 선생님이 아니라면 접속 차단(403에러)
    public ResponseEntity<Page<ClassStudentListVO>> getClassMember(
            @AuthenticationPrincipal UserDetails userDetails, 
            @Parameter(hidden=true) @PageableDefault(size=8, sort="csSeq",direction = Sort.Direction.DESC) Pageable page){
        Page<ClassStudentListVO> result = cService.classMemberFind(userDetails, page);
        
        return new ResponseEntity<Page<ClassStudentListVO>>(result, HttpStatus.OK);
    }
    
}
