package com.qrw.service;

import com.qrw.dto.NotificationDTO;
import com.qrw.dto.PaginationDTO;
import com.qrw.pojo.User;

/**
 * @author qrw
 * @create 2021-03-22 17:16
 */
public interface NotificationService {

    PaginationDTO list(Long userId,Integer page,Integer size);

    Long unreadCount(Long userId);

    NotificationDTO read(Long id, User user);
}
