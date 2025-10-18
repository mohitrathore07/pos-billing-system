package com.pos.system.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pos.system.payload.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String jwt;
    private String message;
    private String title;
    private UserDto user;
}
