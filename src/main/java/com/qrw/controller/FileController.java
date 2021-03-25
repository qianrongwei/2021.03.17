package com.qrw.controller;

import com.qrw.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @author qrw
 * @create 2021-03-23 18:25
 */
@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        String fileName = UUID.randomUUID().toString();
        fileDTO.setUrl("/img/"+fileName+".jpg");
        //fileDTO.setUrl("/images/profile.jpg");
        return fileDTO;
    }
}
