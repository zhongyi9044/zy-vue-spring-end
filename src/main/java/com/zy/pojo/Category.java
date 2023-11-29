package com.zy.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @NotNull(groups=Add.class)//进行分组校验
    private Integer id;//主键ID
    @NotEmpty(groups = {Add.class,Update.class})//进行分组校验
    private String categoryName;//分类名称
    @NotEmpty(groups = {Add.class,Update.class})//进行分组校验
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    //因为ADD和Update都需要在NotEmpty组，可以让Add，Update继承Default，这样可以不在NotEmpty增加@groups

    public interface Add{

    }

    public interface Update{

    }
}
