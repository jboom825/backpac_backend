package com.backpac.repository;

import com.backpac.entity.Orders;
import com.backpac.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UsersRepositoryTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UsersRepository usersRepository;

    @Test
    void save() {
        int userCnt = 100;
        for(int i=0; i< userCnt; i++) {
            String userNum = String.format("%03d", i);
            Users user = new Users("ID" + userNum, "NAME" + userNum, "NM" + userNum,
                    bCryptPasswordEncoder.encode("qqq123"), "010-2226-0" + userNum, "email" + userNum + "@gmail.com", "M");
            usersRepository.save(user);
        }

    }
}