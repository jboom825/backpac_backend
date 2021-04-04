package com.backpac.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * com.backpac.dto
 * ㄴ ErrorResponseDto.java
 * </pre>
 * @date : 2021-03-31 오후 1:44
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 에러처리응답을 위한 DTO
 **/
@Setter @Getter
@NoArgsConstructor
public class ErrorResponseDto extends CommonDto{
    private String errorCode;
    private String errorMessage;

    public ErrorResponseDto(String errorCode, String errorMessage) {
        super(errorCode, "");
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static ErrorResponseDto of(String errorCode, String errorMessage) {
        return new ErrorResponseDto(errorCode, errorMessage);
    }
}
