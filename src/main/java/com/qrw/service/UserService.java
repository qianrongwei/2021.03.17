package com.qrw.service;

import com.qrw.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author qrw
 * @create 2021-03-20 11:20
 */
public interface UserService {

    void createOrUpdate(User user);
}
