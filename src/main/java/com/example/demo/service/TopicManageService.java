package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.TopicManage;
import com.example.demo.vo.TopicManageVO;

/**
 * <p>
 * 题目管理 服务类
 * </p>
 *
 * @author vince
 * @since 2021-09-27
 */
public interface TopicManageService extends IService<TopicManage> {

    boolean add(TopicManageVO topicManageVO);
}
