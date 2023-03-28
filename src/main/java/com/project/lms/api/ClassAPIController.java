package com.project.lms.api;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
import com.project.lms.vo.grade.StudentClassGradeVO;
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
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
@Tag(description = "반 변경 API입니다.", name = "반")
public class ClassAPIController {
    private final ClassService cService;
    @Operation(summary = "반 변경", security = @SecurityRequirement(name = "bearerAuth"))
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

    @GetMapping("/excel")
    @Operation(summary = "변경 반 엑셀파일 다운로드", description ="모든 회원의 가장 최신 시험의 성적을 조회해서 반을 다시 나눕니다. 엑셀파일로 다운로드 가능합니다. 총점이 0인 학생은 미응시자입니다. 작업을 위해 토큰을 제외했으나 나중에 토큰이 필요하게될수도있습니다.")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        Workbook workbook = new HSSFWorkbook(); //사용을 위해 POI를 gradle에 의존성 추가함. 엑셀파일 생성
        Sheet sheet = workbook.createSheet("sheet1"); //시트 생성. 생성될 시트 이름
        int rowNo = 0; //데이터를 추가할 행 번호. 행이 1 증가하면 다음 행으로 이동했다는 의미임

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6)); //0행에서 0행까지, 0열에서 6열까지 병합
        
        CellStyle titleStyle = workbook.createCellStyle(); //제목 스타일 설정
        titleStyle.setAlignment(HorizontalAlignment.CENTER); //가운데정렬
        Font titleFont = workbook.createFont(); //폰트설정
        titleFont.setFontHeightInPoints((short) 18); //크기를 18로
        titleStyle.setFont(titleFont);  //서식에 폰트서식 추가

        CellStyle headStyle = workbook.createCellStyle(); //제목행 스타일 설정
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex()); //배경색
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //색상 채우기 
        Font font = workbook.createFont(); //폰트 정보
        headStyle.setBorderTop(BorderStyle.THIN); //테두리 위쪽
        headStyle.setBorderBottom(BorderStyle.THIN); //테두리 아래쪽
        headStyle.setBorderLeft(BorderStyle.THIN); //테두리 왼쪽
        headStyle.setBorderRight(BorderStyle.THIN); //테두리 오른쪽
        headStyle.setFont(font); //폰트 서식 추가
        
        CellStyle cellStyle = workbook.createCellStyle(); //본문 스타일 설정
        cellStyle.setBorderTop(BorderStyle.THIN); //테두리 위쪽
        cellStyle.setBorderBottom(BorderStyle.THIN); //테두리 아래쪽
        cellStyle.setBorderLeft(BorderStyle.THIN); //테두리 왼쪽
        cellStyle.setBorderRight(BorderStyle.THIN); //테두리 오른쪽

        Row title = sheet.createRow(rowNo++); //제목 설정후 행 번호 증가
        title.createCell(0).setCellValue("학생 반 현황"); //제목 입력
        title.getCell(0).setCellStyle(titleStyle); //행의 스타일 설정
        
        Row headerRow = sheet.createRow(rowNo++); //헤더 정보 입력
        headerRow.createCell(0).setCellValue("회원번호"); //0열에 데이터 입력
        headerRow.createCell(1).setCellValue("이름"); //1열에 데이터 입력
        headerRow.createCell(2).setCellValue("생년월일"); //2열에 데이터 입력
        headerRow.createCell(3).setCellValue("합계점수"); //3열에 데이터 입력
        headerRow.createCell(4).setCellValue("기존 반"); //4열에 데이터 입력
        headerRow.createCell(5).setCellValue("변경 반"); //5열에 데이터 입력
        headerRow.createCell(6).setCellValue("상태"); //6열에 데이터 입력

        for(int i=0; i<=6; i++){
            headerRow.getCell(i).setCellStyle(headStyle); 
        } //각 헤더에 스타일 적용

        List<StudentClassGradeVO> list = cService.changeClassList(); //본문에 입력할 데이터를 가져옴
        for (StudentClassGradeVO data : list) {
            Row row = sheet.createRow(rowNo++); //본문 설정 후 행 추가. for문을 돌때마다 다음행으로 이동됨
            row.createCell(0).setCellValue(data.getSeq()); //0열에 데이터 입력
            row.createCell(1).setCellValue(data.getName()); //1열에 데이터 입력
            row.createCell(2).setCellValue(data.getBirth().toString()); //2열에 데이터 입력
            row.createCell(3).setCellValue(data.getTotal()); //3열에 데이터 입력
            row.createCell(4).setCellValue(data.getOriginClass()); //4열에 데이터 입력
            row.createCell(5).setCellValue(data.getChangeClass()); //5열에 데이터 입력
            row.createCell(6).setCellValue(data.getStatus()); //6열에 데이터 입력
            for(int i=0; i<=6; i++){
                row.getCell(i).setCellStyle(cellStyle);
            } //각 본문에 스타일 설정
        }

        response.setContentType("ms-vnd/excel"); //반환 타입
        response.setHeader("Content-Disposition", "attachment;filename=student_class.xls"); //저장 이름 설정

        workbook.write(response.getOutputStream()); //response에 바로 응답. 에러 대응이 어려워서 좋은 코드는 아님
        workbook.close(); //종료
    }
    
}
