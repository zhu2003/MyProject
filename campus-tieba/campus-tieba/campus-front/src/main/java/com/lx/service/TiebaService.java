package com.lx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.common.Result;
import com.lx.entity.Search;
import com.lx.entity.Tieba;
import org.springframework.transaction.annotation.Transactional;

/**
 * (Tieba)表服务接口
 *
 * @author makejava
 * @since 2023-10-18 11:13:36
 */
public interface TiebaService extends IService<Tieba> {
    /*
     * 发布或更新贴吧
     * */

    Result publish(Tieba tieba,String token);
    /*
    * 获取文章列表
    * */
    Result getTiebas();
    /*
     * 获取热门贴吧
     * */
    Result getHotTieba();
    /*
     * 获取贴吧详情
     * */
    Result getDetail(Long tid);
    /*
     * 点赞接口
     * */
    Result addLike(Long tid);
    /*
     *取消点赞
     * */
    Result cancelLike(Long tid);
    /*
     * 增加浏览量
     * */
    Result addView(Long tid);
    /*
     * 根据关键词搜索贴吧
     * */
    Result search(String keyWord);
    /*
     * 获取个人空间的贴吧列表
     * */
    Result spaceTiebaList();
    /*
     * 删除贴吧
     * */
    Result deleteTieba(Long tid);
    /*
     * 个人空间贴吧搜索
     * */
    Result spaceTiebaSearch(Search search);
}

