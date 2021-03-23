package com.qrw.dto;

import com.qrw.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qrw
 * @create 2021-03-19 10:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private User user;
}
