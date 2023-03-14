package com.project.lms.entity.member.enumfile;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    STUDENT,
    TEACHER,
    MASTER,
    EMPLOYEE;

    @JsonCreator
    public static Role from(String name) {
        try {
            return Role.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
