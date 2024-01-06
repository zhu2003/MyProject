package com.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.Vo.ArticleVo;
import com.lx.Vo.HeadlineVo;
import com.lx.Vo.PageInfoVo;
import com.lx.dto.HeadlineDto;
import com.lx.entity.Headline;
import com.lx.entity.PageInfo;
import com.lx.entity.Type;
import com.lx.entity.User;
import com.lx.mapper.HeadlineMapper;
import com.lx.mapper.TypeMapper;
import com.lx.mapper.UserMapper;
import com.lx.service.HeadlineService;
import com.lx.utils.BeanCopyUtils;
import com.lx.utils.Result;
import com.lx.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline> implements HeadlineService {
    @Autowired
    private HeadlineMapper headlineMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private UserMapper userMapper;
    /*
     * 分页查询首页头条信息
     * */
    @Override
    public Result findNewsPage(PageInfo pageInfo) {
        //封装page信息
        Page<Headline> page = new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize());
        LambdaQueryWrapper<Headline> lqw = new LambdaQueryWrapper<>();
        lqw.like(pageInfo.getKeyWords()!="",Headline::getTitle,pageInfo.getKeyWords())
            .eq(pageInfo.getType() > 0,Headline::getType,pageInfo.getType())
            .orderByDesc(Headline::getUpdateTime).orderByAsc(Headline::getPageViews);
        //查询
        headlineMapper.selectPage(page,lqw);
        //封装数据
        PageInfoVo pageInfoVo = new PageInfoVo();
        pageInfoVo.setPageNum(page.getCurrent());
        pageInfoVo.setPageSize(page.getSize());
        pageInfoVo.setTotalPage(page.getPages());
        pageInfoVo.setTotalSize(page.getTotal());
        List<Headline> records = page.getRecords();
        Date date2 = new Date();
        Long num = 86400000L;
        List<HeadlineVo> list =records.stream().map(item->{
            HeadlineVo headlineVo = BeanCopyUtils.copyBean(item, HeadlineVo.class);
            Date date1 = item.getCreateTime();
            Long time = (date2.getTime()-date1.getTime())/num;
            headlineVo.setPastHours(time.intValue());
            return headlineVo;
        }).collect(Collectors.toList());
        pageInfoVo.setPageData(list);
        Map map = new HashMap<>();
        map.put("pageInfo",pageInfoVo);
        return Result.ok(map);
    }
    /*
     * 查询头条详细内容
     * */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        if(hid==null) return Result.build(null, ResultCodeEnum.ID_IS_NULL);
        //根据id查询文章详细
        Headline headline = headlineMapper.selectById(hid);
        //查询类别名称
        LambdaQueryWrapper<Type> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Type::getTid,headline.getType());
        String tname = typeMapper.selectOne(lqw).getTname();
        //查询新闻作者
        LambdaQueryWrapper<User> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(User::getUid,headline.getPublisher());
        String nickName = userMapper.selectOne(lqw2).getNickName();
        //浏览量加一
        headline.setVersion(headline.getVersion());
        headline.setPageViews(headline.getPageViews()+1);
        headlineMapper.updateById(headline);
        //封装数据
        Date date2 = new Date();
        Long num = 86400000L;
        Long time = (date2.getTime() - headline.getCreateTime().getTime())/num;
        HeadlineDto headlineDto = BeanCopyUtils.copyBean(headline, HeadlineDto.class);
        headlineDto.setTypeName(tname);
        headlineDto.setAuthor(nickName);
        headlineDto.setPastHours(time.intValue());
        Map map = new HashMap();
        map.put("headline",headlineDto);
        return Result.ok(map);
    }
    /*
     * 头条发布实现
     * */
    @Override
    public Result publish(Headline headline) {
        headline.setPageViews(0);
//        headline.setCreateTime(new Date());
//        headline.setUpdateTime(new Date());
        int count = headlineMapper.insert(headline);
        if(count==0) return Result.build(null,ResultCodeEnum.PUBLISH_ERROR);
        return Result.ok(null);
    }
    /*
     * 根据新闻id查询新闻
     * */
    @Override
    public Result findHeadlineByHid(Integer hid) {
        //根据id查询文章
        Headline headline = headlineMapper.selectById(hid);
        ArticleVo articleVo = BeanCopyUtils.copyBean(headline, ArticleVo.class);
        //封装数据
        Map map = new HashMap();
        map.put("headline",articleVo);
        return Result.ok(map);
    }
    /*
     * 头条修改
     * */
    @Override
    public Result updateHeadline(Headline headline) {
        //读取版本
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);
        headline.setUpdateTime(new Date());
        int i = headlineMapper.updateById(headline);
        if(i==0) return Result.build(null,508,"更新失败");
        return Result.ok(null);
    }
    /*
     * 根据id删除文章
     * */
    @Override
    public Result removeByHid(Integer hid) {
        int i = headlineMapper.deleteById(hid);
        return Result.ok(null);
    }
}
