package com.qrw.dto;

import com.qrw.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qrw
 * @create 2021-03-22 17:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long id;
    private Long notifier;
    private String notifierName;
    private Long  outerId;
    private Integer type;
    private Integer status;
    private Long gmtCreate;
    private String outerTitle;
    private String typeName;

}
