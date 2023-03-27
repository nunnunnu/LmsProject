package com.project.lms.repository.custom;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.GradeInfoEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GradeJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<GradeInfoEntity> grades) {
        jdbcTemplate.batchUpdate("INSERT INTO LMS_project.grade_info"
        +"(gi_sub_seq, gi_mi_seq1, gi_mi_seq2, gi_grade, gi_test_seq)"
        +"VALUES(?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, grades.get(i).getSubject().getSubSeq());
                        ps.setLong(2, grades.get(i).getStudent().getMiSeq());
                        ps.setLong(3, grades.get(i).getTeacher().getMiSeq());
                        ps.setInt(4, grades.get(i).getGrade());
                        ps.setLong(5, grades.get(i).getTest().getTestSeq());
                    }

                    @Override
                    public int getBatchSize() {
                        return grades.size();
                    }
                });
    }
}
