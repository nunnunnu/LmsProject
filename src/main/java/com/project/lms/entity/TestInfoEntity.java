package com.project.lms.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "test_info")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class TestInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_seq") private Long testSeq;
    @Column(name = "test_name") private String testName;
    @Column(name = "test_start_time") private LocalTime testStartTime;
    @Column(name = "test_end_time") private LocalTime testEndTime;
}
