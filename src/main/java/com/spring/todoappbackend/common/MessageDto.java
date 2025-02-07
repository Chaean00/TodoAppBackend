package com.spring.todoappbackend.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MessageDto {
    private boolean isSuccess;
    private String message;
}
