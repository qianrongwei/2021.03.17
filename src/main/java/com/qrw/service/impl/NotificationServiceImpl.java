package com.qrw.service.impl;

import com.qrw.dto.NotificationDTO;
import com.qrw.dto.PaginationDTO;
import com.qrw.dto.QuestionDTO;
import com.qrw.enums.NotificationStatusEnum;
import com.qrw.enums.NotificationTypeEnum;
import com.qrw.exception.CustomizeErrorCode;
import com.qrw.exception.CustomizeException;
import com.qrw.mapper.NotificationMapper;
import com.qrw.pojo.Notification;
import com.qrw.pojo.Question;
import com.qrw.pojo.User;
import com.qrw.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author qrw
 * @create 2021-03-22 17:18
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = notificationMapper.getCountByUserId(userId);
        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if(page < 1 ){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.listByUserId(userId,offset,size);

        if(notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    @Override
    public Long unreadCount(Long userId) {
        return notificationMapper.unreadCount(userId,NotificationStatusEnum.UNREAD.getStatus());
    }

    @Override
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;

    }


}
