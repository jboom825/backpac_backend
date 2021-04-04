package com.backpac.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * com.backpac.dto
 * ㄴ CommonDto.java
 * </pre>
 * @date : 2021-03-31 오후 2:38
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 공통응답 처리를 위한 DTO (로그인/로그아웃)
 **/
@Getter @Setter
@NoArgsConstructor
public class CommonDto {
    @JsonProperty(defaultValue = "0")
    private String resultCd;

    private String resultMessage;

    public CommonDto(String resultCd, String resultMessage) {
        this.resultCd = resultCd;
        this.resultMessage = resultMessage;
    }
}
