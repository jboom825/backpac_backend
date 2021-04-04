package com.backpac.entity;

import com.backpac.dto.UserDto;
import com.backpac.util.ModelMapperUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * <pre>
 * com.backpac.entity
 * ㄴ Users.java
 * </pre>
 * @date : 2021-03-31 오전 11:19
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 회원정보 Entity
 **/
@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name="USERS")
public class Users {

    @Builder
    public Users(String id, String name, String nickName, @Length(min = 10, max = 100) String pwd, @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$") String phoneNumber, @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$") String email, @Pattern(regexp = "^[MF]") String sex) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.pwd = pwd;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.sex = sex;
    }

    @Id
    @Column(name="ID")
    private String id;

    @Column(name="NAME")
    private String name;

    @Column(name="NICK_NAME")
    private String nickName;

    @Length(min = 10, max = 100)
    @Column(name="PASSWORD")
    private String pwd;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    @Column(name="EMAIL")
    private String email;

    @Pattern(regexp = "^[MF]")
    @Column(name="SEX")
    private String sex;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Orders> orders;

    public static Users of(UserDto userDto) {
        return ModelMapperUtil.getMapper().map(userDto, Users.class);
    }
}
