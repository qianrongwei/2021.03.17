package com.qrw.cache;

import com.qrw.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qrw
 * @create 2021-03-22 10:49
 */
public class TagCache {

    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<TagDTO>();

        TagDTO program = new TagDTO();
        program.setCategoryName("编程语言");
        program.setTags(Arrays.asList("C","C++","Java","PHP","CSS","js","Python","Ruby","golang","html"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","mybatis","struts2","django","yii","koa"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux","Nginx","docker","apache","unix","windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","oracle","sqlserver","redis","mongodb","sqlite"));
        tagDTOS.add(db);

        return tagDTOS;
    }

    public static String filterInvalid (String tags) {
        String[] split = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}










