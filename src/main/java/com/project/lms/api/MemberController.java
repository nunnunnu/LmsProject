package com.project.lms.api;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.error.ErrorResponse;
import com.project.lms.error.NotValidExceptionResponse;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.service.MemberSecurityService;
import com.project.lms.service.MemberService;
import com.project.lms.vo.LoginVO;
import com.project.lms.vo.MailVO;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.MemberLoginResponseVO;
import com.project.lms.vo.member.ClassStudentListVO;
import com.project.lms.vo.member.MemberJoinVO;
import com.project.lms.vo.member.MemberSearchIdVO;
import com.project.lms.vo.member.MemberSearchPwdVO;
import com.project.lms.vo.member.RefreshTokenVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(description = "회원 관련 API입니다.", name = "회원")
public class MemberController {
    private final MemberService mService;
    private final MemberSecurityService memberSecurityService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "406", description = "회원가입 실패. response의 err메세지 확인", content = @Content(schema = @Schema(implementation = NotValidExceptionResponse.class))),
        @ApiResponse(responseCode = "400", description = "type에러(대소문자는 상관없습니다. 오타확인해주세요) or 과목번호 에러 or 반 번호 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "424", description = "선생님 가입중일 시 과목번호 오류(일치하는 과목이 없습니다.), 직원 가입중일시 부서나 직급정보 누락(오타나도 에러납니다), 학생 가입중일시 반번호 누락", content = @Content(schema = @Schema(implementation = MapVO.class))) })
    @Operation(summary = "회원가입", description ="입력받은 타입 정보(student, teacher, employee, master)에 따라 회원가입이 진행됩니다.")
    @PutMapping("/join/{type}")
    public ResponseEntity<MapVO> joinMember(
            @Parameter(description ="가입할 계정 타입(student, teacher, employee, master). 대소문자 상관없이 일치하는 타입으로 가입됩니다.") @PathVariable String type, 
            @RequestBody @Valid MemberJoinVO data, //VO에 건 유효성 검사를 위해 @Valid 어노테이션을 사용
            BindingResult bindingResult //VO의 유효성검사때문에 발생한 에러를 담음
        ){

        return new ResponseEntity<>(mService.joinMember(data, type, bindingResult), HttpStatus.OK);
    }
    @Operation(summary = "로그인", description ="아이디,비밀번호(id,pwd)을 입력 받아 DB와 일치하는 유저가 있을때 로그인 성공")
     @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "탈퇴한 회원이거나 사용 불가능한 아이디로 로그인 시 발생하는 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "아이디 혹은 비밀번호가 일치하지 않을때 발생하는 오류 ", content = @Content(schema = @Schema(implementation = NotFoundMemberException.class))),
        @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = MemberLoginResponseVO.class)))})
    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseVO> postMemberLogin(@RequestBody LoginVO login) {
        MemberLoginResponseVO response = memberSecurityService.securityLogin(login);
        return new ResponseEntity<>(response,response.getCod());
    }
    
    @GetMapping("/refresh")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "로그인 후 급받은 리프레쉬토큰이 아니거나 만료된 리프레쉬 토큰입니다. 에러나면 로그아웃시키고 로그인페이지로 이동시켜주세요 ", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "200", description = "발급 성공", content = @Content(schema = @Schema(implementation = MapVO.class)))})
    @Operation(summary = "토큰 재발급", description ="로그인할때 받은 리프레쉬 토큰으로 엑세스 토큰을 재발급 받는 기능입니다. 엑세스 토큰이 만료되었을때 재발급받아주세요. 만약 여기서도 에러가 뜬다면 로그아웃처리해주시면 됩니다")
    public ResponseEntity<Object> accessToken(@RequestBody RefreshTokenVO refresh){
        Map<String, Object> map = mService.accessToken(refresh.getRefresh());
        
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
        
    }

    @PostMapping("/id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "아이디찾기 성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "일치하는 회원정보가 없으면 오류입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @Operation(summary = "아이디 찾기", description ="이름,생년월일,이메일(name,birth,email)을 입력 받아 일치하는 정보의 아이디 출력")
    public ResponseEntity<Object> searchMemberId(MemberSearchIdVO data){
        Map<String, Object> map = memberSecurityService.searchMemberId(data);
        return new ResponseEntity<Object>(map, (HttpStatus)map.get("code"));
    }
    @Operation(summary = "비밀번호 찾기", description ="아이디,이름,이메일(id,name,email)을 입력 받아 일치하는 유저의 등록된 메일로 임시 비밀번호 발급")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "비밀번호 찾기 성공 유저가 등록한 메일로 임시 비밀번호가 발송됩니다.", content = @Content(schema = @Schema(implementation = MailVO.class))),
        @ApiResponse(responseCode = "400", description = "일치하는 회원정보가 없으면 오류입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/pwd")
        public ResponseEntity<MailVO> searchMemberPwd(MemberSearchPwdVO data){
        MailVO mail = memberSecurityService.searchMemberPwd(data);
        // System.out.println(mail);
        memberSecurityService.mailSend(mail);
        return new ResponseEntity<MailVO>(mail, mail.getCode());
    }
    
}