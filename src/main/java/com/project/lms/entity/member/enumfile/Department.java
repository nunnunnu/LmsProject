package com.project.lms.entity.member.enumfile;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Department {
    교무행정,능력개발,취업지원,상담;

    @JsonCreator
    public static Department from(String name) { //name과 일치하는 role이 없으면 null이 반환됨
        try {
            return Department.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
