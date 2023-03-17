package com.project.lms.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.security.provider.JwtTokenProvider;
import com.project.lms.vo.LoginVO;
import com.project.lms.vo.MailVO;
import com.project.lms.vo.MemberLoginResponseVO;
import com.project.lms.vo.MemberResponseVO;
import com.project.lms.vo.member.MemberSearchIdVO;
import com.project.lms.vo.member.MemberSearchPwdVO;
import com.project.lms.vo.member.UpdateMemberVO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MemberSecurityService {
    private final JavaMailSender mailSender;
    private final MemberInfoRepository memberInfoRepository;
    private final AuthenticationManagerBuilder authBulider;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private static final String FROM_ADDRESS = "rudwns0401@gmail.com";
    private final RedisService redisService;
    

    public Boolean pwdCheck(String rawPwd, String encodePwd){ 
        if(passwordEncoder.matches(rawPwd, encodePwd)){
            return true;
        } else{
            return false;
        }
    }
    @Transactional
    public MemberLoginResponseVO securityLogin(LoginVO login) {
        // 로그인 시 입력한 아이디 값으로 DB에 저장된 아이디를 찾고
        MemberInfoEntity loginUser = memberInfoRepository.findByMiId(login.getId());
        // 찾은 아이디가 없거나(null) 입력한 비밀번호가 일치하지 않을 때 오류 메시지 출력
        if(loginUser == null || !passwordEncoder.matches(login.getPwd(), loginUser.getMiPwd())){
            return MemberLoginResponseVO.builder().status(false).message("아이디 또는 비밀번호 오류입니다.").cod(HttpStatus.NOT_FOUND).build();
        }
        // 일치하는 아이디가 있더라도 사용 불가능한 상태이면 오류 메시지 출력
        else if (!loginUser.isEnabled()) {
            return MemberLoginResponseVO.builder().status(true).message("이용정지된 사용자입니다.").cod(HttpStatus.BAD_REQUEST).build();
        }
        // 입력한 아이디와 비밀번호를 통해서 인증 과정을 거쳐서
        UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginUser.getMiId(), loginUser.getMiPwd());
        System.out.println(authenticationToken);
        
        Authentication authentication =
        authBulider.getObject().authenticate(authenticationToken);
        // 토큰 발급

        String refreshToken = tokenProvider.generateToken(authentication).getRefreshToken(); //리프레쉬토큰 변수 저장

        redisService.setValues(refreshToken, loginUser.getMiId()); //redis에 refresh토큰 저장

        MemberLoginResponseVO response = MemberLoginResponseVO.builder().status(true).message("로그인 성공").token(tokenProvider.
                        generateToken(authentication)).cod(HttpStatus.OK).name(loginUser.getMiName()).role(loginUser.getMiRole()).build();
        return response;
        
    }

    public MemberResponseVO updateMember(UpdateMemberVO data, UserDetails userDetails) {
        MemberInfoEntity entity = memberInfoRepository.findByMiId(userDetails.getUsername());
        String pattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{8,16}$";
        if(entity==null) { // 멤버 정보가 없을 시 (null)
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("해당 회원이 존재하지 않습니다.")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        if(!pwdCheck(data.getMiPwd(), entity.getMiPwd())){ // 비밀번호 불일치
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("비밀번호가 일치하지않습니다.")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        if(data.getMiPwd().length() > 16 || data.getMiPwd().length() > 8) { // 비밀번호 길이 제한
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("비밀번호는 8~16자로 입력해주세요.")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        else if (
            data.getMiPwd().replaceAll(" ", "").length() == 0 || // 비밀번호 공백문자 사용 제한
            !Pattern.matches(pattern, data.getMiPwd())
            ) {
                MemberResponseVO m = MemberResponseVO.builder()
                .status(false)
                .message("비밀번호에 공백문자를 사용할 수 없습니다.")
                .code(HttpStatus.BAD_REQUEST)
                .build();
                return m;
            }
        else if(data.getMiPwd() == null || data.getMiPwd().equals("")) {
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("비밀번호를 입력해주세요")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        else{
            entity.updatePwd(passwordEncoder.encode(data.getChangeMiPwd()));
            memberInfoRepository.save(entity);
            MemberResponseVO m = MemberResponseVO.builder()
            .status(true).message("회원 수정이 완료되었습니다.")
            .code(HttpStatus.ACCEPTED)
            .build();
            return m;
        }
    }

    public Map<String, Object> searchMemberId(MemberSearchIdVO data) { //아이디찾기
    Map<String ,Object> resultMap = new LinkedHashMap<String, Object>();
    // 사용자 이름, 생일 , 이메일 받아서 리스트에 있는 것과 비교하여 해당 전화번호에 맞는 아이디 찾기
    MemberInfoEntity User = memberInfoRepository.findByMiNameAndMiBirthAndMiEmail(data.getName(), data.getBirth(), data.getEmail());
    // 해당 정보를 통해서 찾는 유저가 없을 때는 메세지 출력
    if(User == null) {
      resultMap.put("status", false);
      resultMap.put("message", "해당하는 정보가 없습니다");
      resultMap.put("code", HttpStatus.BAD_REQUEST);
    }

    else{
      // 찾는 유저가 있을 때는 유저의 아이디를 보여줌
      resultMap.put("status", true);
      resultMap.put("message", "고객님의 아이디를 찾았습니다");
      resultMap.put("code", HttpStatus.OK);
      resultMap.put("UserId", User.getMiId());
    }
    return resultMap;
  }
    public MailVO searchMemberPwd(MemberSearchPwdVO data) { 
    // 사용자 이름, 생일 , 이메일 받아서 리스트에 있는 것과 비교하여 해당 전화번호에 맞는 아이디 찾기
    MemberInfoEntity User = memberInfoRepository.findByMiIdAndMiNameAndMiEmail(data.getId(), data.getName(), data.getEmail());
    // 랜덤 비밀번호를 생성해서 저장
    String str = getTempPassword();
     MailVO mail = new MailVO();
    if(User == null) {
      // 찾는 유저정보가 없으면 메시지 출력
     mail.setMsg("등록된 계정이 없습니다.");
     mail.setCode(HttpStatus.BAD_REQUEST);
     return mail;
    }
    else {
      // 찾는 유저가 있으면 해당 유저의 메일 주소를 가져오고
        mail.setAddress(User.getMiEmail());
        // 메일 제목을 설정
        mail.setTitle(User.getMiName()+"님의 임시비밀번호 안내 이메일 입니다.");
        // 임시비밀번호를 메일 내용에 넣고
        mail.setMessage("안녕하세요. 임시비밀번호 안내 관련 이메일 입니다." + "[" + User.getMiName() + "]" +"님의 임시 비밀번호는 "
        + str + " 입니다.");
        // 완료되었다는 메시지 출력하고
        mail.setMsg("임시 비밀번호가 등록된 메일로 발송되었습니다.");
        // 임시 비밀번호를 암호화하여 위에서 찾은 유저의 비밀번호를 임시 비밀번호로 변경해준다
        mail.setCode(HttpStatus.OK);
      User.setMiPwd(passwordEncoder.encode(str));
      // 변경 후에 저장
      memberInfoRepository.save(User);
    return mail; 
    }
  }
    public void mailSend(MailVO mailDto){
        System.out.println("이메일 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        // 메일 받는 사람
        message.setTo(mailDto.getAddress());
        // 메일 보내는 사람
        message.setFrom(FROM_ADDRESS);
        // 메일 제목
        message.setSubject(mailDto.getTitle());
        // 메일 내용
        message.setText(mailDto.getMessage());
        // message.setReplyTo(FROM_ADDRESS);
        // 위의 내용을 종합하여 메일을 발송
        mailSender.send(message);
    }
    public String getTempPassword(){// 랜덤 비밀번호 생성
     int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();
    String generatedString = random.ints(leftLimit, rightLimit + 1)
                                   .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                                   .limit(targetStringLength)
                                   .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                   .toString();
                                   return generatedString;
      }
}
 