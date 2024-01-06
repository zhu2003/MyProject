package com.lx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.entity.Headline;
import com.lx.entity.PageInfo;
import com.lx.utils.Result;

public interface HeadlineService extends IService<Headline> {
    /*
     * 分页查询首页头条信息
     * */
    Result findNewsPage(PageInfo pageInfo);
    /*
     * 查询头条详细内容
     * */
    Result showHeadlineDetail(Integer hid);
    /*
     * 头条发布实现
     * */
    Result publish(Headline headline);
    /*
     * 根据新闻id查询新闻
     * */
    Result findHeadlineByHid(Integer hid);
    /*
     * 头条修改
     * */
    Result updateHeadline(Headline headline);
    /*
     * 根据id删除文章
     * */
    Result removeByHid(Integer hid);
}
