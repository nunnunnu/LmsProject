package com.project.lms.entity.member.enumfile;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Position {
    사원,주임,실장,대리,팀장,과장,부장,원장;

    @JsonCreator
    public static Position from(String name) { //name과 일치하는 role이 없으면 null이 반환됨
        try {
            return Position.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
