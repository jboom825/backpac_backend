package com.backpac.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * <pre>
 * com.backpac.util
 * ㄴ ModelMapperUtil.java
 * </pre>
 * @date : 2021-04-03 오전 10:02
 * @author : 김재범
 * @version : 1.0.0
 * @desc : Entity 객체와 DTO 객체간의 매핑을 위한 공통 유틸
 **/
public class ModelMapperUtil {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    public static ModelMapper getMapper() {
        return modelMapper;
    }
}
