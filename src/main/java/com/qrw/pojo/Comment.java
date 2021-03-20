package com.qrw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qrw
 * @create 2021-03-20 18:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private String comment;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;

}
