package com.wml.mapper;




import com.wml.entity.TUser;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lfy
 * @Description
 * @create 2023-04-20 16:59
 */
@Mapper
public interface UserMapper {

    /**
     * 1、每个方法都在Mapper文件中有一个sql标签对应。
     * 2、所有参数都应该用@Param进行签名，以后使用指定的名字在SQL中取值
     * @param id
     * @return
     */
    TUser getUserById(@Param("id") Long id);
}
