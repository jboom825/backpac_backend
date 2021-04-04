package com.backpac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * com.backpac.dto
 * ㄴ LoginUserDto.java
 * </pre>
 * @date : 2021-03-31 오후 3:19
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 회원 로그인을 위한 DTO
 **/
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    private String id;
    private String password;
}
