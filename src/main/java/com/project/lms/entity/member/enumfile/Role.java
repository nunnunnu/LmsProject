package com.project.lms.entity.member.enumfile;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    학생,선생,관리,직원;

    @JsonCreator
    public static Role from(String name) {
        try {
            return Role.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
