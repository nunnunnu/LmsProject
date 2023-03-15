package com.project.lms.entity.member.enumfile;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    STUDENT,
    TEACHER,
    MASTER,
    EMPLOYEE;

    @JsonCreator
    public static Role from(String name) { //name과 일치하는 role이 없으면 null이 반환됨
        try {
            return Role.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
