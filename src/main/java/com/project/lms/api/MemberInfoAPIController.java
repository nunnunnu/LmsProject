// package com.project.lms.api;

// import com.project.lms.vo.member.UpdateMemberVO;
// import com.project.lms.vo.MemberResponseVO;
// import com.project.lms.service.MemberInfoService;

// import org.springframework.http.HttpStatus;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @Tag(name = "멤버정보 관리", description = "멤버정보 CRUD API")
// @RestController
// @RequestMapping("/api/member")
// @RequiredArgsConstructor
// public class MemberInfoAPIController {
//     private final MemberInfoService memberInfoService;
//     @Operation(summary = "회원정보 수정")
//     @PostMapping(value = "/update")
//     public ResponseEntity<MemberResponseVO> updateMember(@Parameter(name = "seq", description = "수정할 회원 번호") @PathVariable Long seq, @RequestBody UpdateMemberVO data) {
//         return new ResponseEntity<>(memberInfoService.updateMember(data, seq), HttpStatus.OK);
//     }
// }
