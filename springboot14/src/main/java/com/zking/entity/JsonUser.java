package com.zking.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonUser {

    @JsonProperty("username")
    private String name;
    @JsonIgnore
    private String pass;
    @JsonInclude(JsonInclude.Include.NON_NULL)//非空才转为字符串
    private Date birth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss",timezone = "GMT+8")
    private Date birth1;
    private String img_url;
//    @JsonFilter("")
    private Integer age;
    private String[] courses;

    @JsonUnwrapped
    private Friend friend;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Friend{
        private Integer fage;
        private String fname;
    }
}
