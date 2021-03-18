package com.qrw.provider;

import com.alibaba.fastjson.JSON;
import com.qrw.dto.AccessTokenDTO;
import com.qrw.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @author qrw
 * @create 2021-03-18 0:01
 */
@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        final MediaType mediaType = MediaType.get("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {

            String string = response.body().string();//access_token=f94fbf814fb5ceed2e8035cdfefbef6b310929de&scope=user&token_type=bearer
            String[] split = string.split("&");
            String tokenstr = split[0];
            String token = tokenstr.split("=")[1];
            return token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
