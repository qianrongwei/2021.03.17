package com.qrw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qrw
 * @create 2021-03-18 16:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String accountId;
    private String name;
    private String bio;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}
