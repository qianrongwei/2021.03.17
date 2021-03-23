package com.qrw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qrw
 * @create 2021-03-22 10:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
