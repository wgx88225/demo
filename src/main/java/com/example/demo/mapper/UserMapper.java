package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author vince
 * @email wengaoxue@163.com
 * @date 2020-09-26 18:03:49
 */
//@Mapper
public interface UserMapper extends BaseMapper<User> {
	
}
