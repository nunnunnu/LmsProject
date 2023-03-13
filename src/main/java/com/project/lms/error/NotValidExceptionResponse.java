package com.project.lms.error;

import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class NotValidExceptionResponse extends ErrorResponse{
    private final List<String> err;
}