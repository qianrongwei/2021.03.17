package com.qrw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qrw
 * @create 2021-03-18 1:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubUser {

    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
