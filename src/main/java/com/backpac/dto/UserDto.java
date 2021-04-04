package com.backpac.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * com.backpac.dto
 * ㄴ UserDto.java
 * </pre>
 * @date : 2021-03-31 오후 2:50
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 회원정보 DTO
 **/
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String pwd;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String email;
    private String sex;
}
