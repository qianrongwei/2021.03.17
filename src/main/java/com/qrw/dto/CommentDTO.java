package com.qrw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qrw
 * @create 2021-03-20 18:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long parentId;
    private String comment;
    private Integer type;
}
