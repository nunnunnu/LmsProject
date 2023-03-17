package com.project.lms.api;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.FeedBackService;
import com.project.lms.vo.feedback.FeedBackResponseVO;
import com.project.lms.vo.feedback.FeedBackVO;
import com.project.lms.vo.feedback.ShowFeedBackDetailVO;
import com.project.lms.vo.feedback.ShowFeedBackVO;
import com.project.lms.vo.feedback.UpdateFeedBackResponseVO;
import com.project.lms.vo.feedback.UpdateFeedBackVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(description = "피드백 API입니다.", name = "피드백")
public class FeedBackAPIController {
    private final FeedBackService fService;

    @Operation(summary = "피드백 조회", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/list")
    public ResponseEntity<ShowFeedBackVO> showFeedBack(@AuthenticationPrincipal UserDetails userDetails, Pageable page) {
        return new ResponseEntity<>(fService.showFeedBack(userDetails.getUsername(), page), HttpStatus.OK);
    }

    @Operation(summary = "피드백 상세 조회", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{fiSeq}")
    public ResponseEntity<ShowFeedBackDetailVO> showFeedBackDetail(@Parameter(name = "fiSeq", description = "상세 조회할 글 번호")
    @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long fiSeq) {
        return new ResponseEntity<>(fService.showFeedBackDetail(userDetails.getUsername(), fiSeq), HttpStatus.OK);
    }
    
    @Operation(summary = "피드백 작성", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{stuSeq}")
    @Secured("ROLE_TEACHER") //선생님만 조회가능하도록 권한 설정. 선생님이 아니라면 접속 차단(403에러)
    public ResponseEntity<FeedBackResponseVO> putFeedBack(@Parameter(name = "stuSeq", description = "담당한 반의 학생 번호")@PathVariable Long stuSeq, @RequestBody FeedBackVO data,
     @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(fService.putFeedBack(userDetails.getUsername(), stuSeq, data), HttpStatus.OK);
    }

    @Operation(summary = "피드백 수정", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/update/{fiSeq}")
    public ResponseEntity<UpdateFeedBackResponseVO> updateFeedBack(@PathVariable Long fiSeq, @RequestBody UpdateFeedBackVO data, @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(fService.updateFeedBack(userDetails.getUsername(),fiSeq, data), HttpStatus.OK);
    }

    @Operation(summary = "피드백 삭제", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/delete/{fiSeq}")
    public ResponseEntity<FeedBackResponseVO> deleteFeedBack(@PathVariable Long fiSeq, @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(fService.deleteFeedBack(userDetails.getUsername(), fiSeq),HttpStatus.OK);
    }

}
