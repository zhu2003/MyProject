package com.lx.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.Vo.TiebaListVo;
import com.lx.Vo.TiebaVo;
import com.lx.common.Result;
import com.lx.common.StatusCode;
import com.lx.entity.Search;
import com.lx.entity.User;
import com.lx.exception.CustomExcetption;
import com.lx.mapper.TiebaMapper;
import com.lx.entity.Tieba;
import com.lx.service.TiebaService;
import com.lx.service.UserService;
import com.lx.utils.RedisCache;
import com.lx.utils.ServletContext;
import com.lx.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * (Tieba)表服务实现类
 *
 * @author makejava
 * @since 2023-10-18 11:13:36
 */
@Service("tiebaService")
@Slf4j
@Transactional //开启事务管理
public class TiebaServiceImpl extends ServiceImpl<TiebaMapper, Tieba> implements TiebaService {
    @Autowired
    private TiebaMapper tiebaMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtil tokenUtil;
    /*
     * 发布或更新贴吧
     * */
    @Override

    public Result publish(Tieba tieba,String token) {
        //解析token
        User user = tokenUtil.getLoginUser(token);
        //获取创建人id
        String uid = user.getId();
        //设置信息
        tieba.setCreateBy(uid);
        tieba.setPublishTime(LocalDateTime.now());
        //有id就更新，没有就是插入
        boolean flag = saveOrUpdate(tieba);
        if (flag){
            return Result.ok(StatusCode.PUBLISH_SUCCESS);
        }
        return Result.error(StatusCode.PUBLISH_ERROR);
    }
    /*
     * 获取文章列表
     * */
    @Override
    public Result getTiebas() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        //获取所有贴吧
        List<Tieba> tiebas = tiebaMapper.selectList(null);
        AtomicInteger count = new AtomicInteger();
        //封装贴吧数据
        List<TiebaVo> tiebaVos = tiebas.stream().map(tieba -> {
            count.getAndIncrement();
            String createbyId = tieba.getCreateBy();
            //根据创建人id查询user表
            User user = userService.getById(createbyId);
            TiebaVo tiebaVo = new TiebaVo();
            BeanUtils.copyProperties(tieba,tiebaVo);
            tiebaVo.setHeadImageUrl(user.getHeadImageUrl());
            tiebaVo.setNickName(user.getNickname());
            //判断是否登录
            if (StringUtils.hasText(token)){
                //获取当前登录用户的id
                User loginUser = tokenUtil.getLoginUser(token);
                //遍历like_users，判断是否点了赞
                String likeUsers = tieba.getLikeUsers();
                if (likeUsers!=null){
                    boolean b = likeUsers.contains(loginUser.getId());
                    tiebaVo.setIsLike(b);
                }else{
                    tiebaVo.setIsLike(false);
                }
            }else{
                tiebaVo.setIsLike(false);
            }
            return tiebaVo;
        }).collect(Collectors.toList());
        TiebaListVo tiebaListVo = new TiebaListVo();
        tiebaListVo.setRows(tiebaVos);
        tiebaListVo.setTotal(count.get());
        return Result.ok(tiebaListVo);
    }
    /*
     * 获取热门贴吧
     * */
    @Override
    public Result getHotTieba() {
        LambdaQueryWrapper<Tieba> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tieba::getTid,Tieba::getTitle);
        wrapper.orderByDesc(Tieba::getLikes);
        IPage<Tieba> page = new Page<>(0,5);
        tiebaMapper.selectPage(page,wrapper);
        List<Tieba> tiebas = page.getRecords();
        return Result.ok(tiebas);
    }
    /*
     * 获取贴吧详情
     * */
    @Override
    public Result getDetail(Long tid) {
        String token = ServletContext.getRequest().getHeader("token");
        if (!StringUtils.hasText(token)){
            throw new CustomExcetption(StatusCode.UNLOGIN);
        }
        User user = tokenUtil.getLoginUser(token);
        Tieba tieba = tiebaMapper.selectById(tid);
        TiebaVo tiebaVo = new TiebaVo();
        BeanUtils.copyProperties(tieba,tiebaVo);
        String ids = tieba.getLikeUsers();
        if (ids!=null){
            boolean b = ids.contains(user.getId());
            tiebaVo.setIsLike(b);
        }else{
            tiebaVo.setIsLike(false);
        }
        return Result.ok(tiebaVo);
    }

    /*
    * 点赞接口
    * */
    @Override
    public Result addLike(Long tid) {
        String token = ServletContext.getRequest().getHeader("token");
        User user = tokenUtil.getLoginUser(token);
        String uid = user.getId();
        Tieba tieba = tiebaMapper.selectById(tid);
        //获取点赞列表
        String likeUsers = tieba.getLikeUsers();
        if (likeUsers!=null && likeUsers.contains(uid)){
            return Result.error(StatusCode.LIKE_REPEAT);
        }
        ArrayList<String> list = null;
        if (Objects.equals(likeUsers, "")
                || Objects.equals(likeUsers,null)
                || Objects.equals(likeUsers, "[]")
                || Objects.equals(likeUsers, "null")){
            list = new ArrayList<>();
        }else{
            list = JSON.parseObject(likeUsers, ArrayList.class);
        }
        //添加到点赞列表
        list.add(uid);
        //更新点赞列
        LambdaUpdateWrapper<Tieba> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Tieba::getTid,tid)
                .set(Tieba::getLikes,tieba.getLikes()+1)
                .set(Tieba::getLikeUsers,JSON.toJSONString(list));

        boolean b = update(null,wrapper);
        return b?Result.ok():Result.error();
    }
    /*
     *取消点赞
     * */
    @Override
    public Result cancelLike(Long tid) {
        //获取当前登录用户id
        String token = ServletContext.getRequest().getHeader("token");
        //没有登录抛出异常
        User user = tokenUtil.getLoginUser(token);
        //根据id查询文章
        Tieba tieba = tiebaMapper.selectById(tid);
        //获取点赞列表
        String likeUsers = tieba.getLikeUsers();
        if (!likeUsers.contains(user.getId())){
            return Result.error();
        }
        ArrayList<String> list = null;
        //转成list对象
        list = JSON.parseObject(likeUsers, ArrayList.class);
        //取消点赞
        list.remove(user.getId());
        //更新文章数据
        LambdaUpdateWrapper<Tieba> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Tieba::getTid,tid)
                .set(Tieba::getLikes,tieba.getLikes()-1)
                .set(Tieba::getLikeUsers,JSON.toJSONString(list));
        boolean b = update(null,wrapper);
        return b?Result.ok():Result.error();
    }
    /*
     * 增加浏览量
     * */
    @Override
    public Result addView(Long tid) {
        //判断是否登录
        String token = ServletContext.getRequest().getHeader("token");
        User user = tokenUtil.getLoginUser(token);
        //查询贴吧
        Tieba tieba = tiebaMapper.selectById(tid);
        if (user!=null){
            LambdaUpdateWrapper<Tieba> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Tieba::getTid,tieba.getTid())
                    .set(Tieba::getViews,tieba.getViews()+1);
            tiebaMapper.update(null,wrapper);
        }
        return Result.ok();
    }
    /*
     * 根据关键词搜索贴吧
     * */
    @Override
    public Result search(String keyWord) {
        //判断是否登录
        String token = ServletContext.getRequest().getHeader("token");
        User user = tokenUtil.getLoginUser(token);
        //构造条件
        LambdaQueryWrapper<Tieba> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(keyWord!="",Tieba::getTitle,keyWord);
        //模糊查询
        Page<Tieba> page = new Page<>();
        tiebaMapper.selectPage(page,wrapper);
        //处理数据
        List<Tieba> tiebas = page.getRecords();
        List<TiebaVo> list = tiebas.stream().map(item->{
            TiebaVo tiebaVo = new TiebaVo();
            BeanUtils.copyProperties(item,tiebaVo);
            return tiebaVo;
        }).collect(Collectors.toList());
        //封装数据
        TiebaListVo tiebaListVo = new TiebaListVo();
        tiebaListVo.setTotal((int) page.getTotal());
        tiebaListVo.setRows(list);
        //返回
        return Result.ok(tiebaListVo);
    }
    /*
     * 获取个人空间的贴吧列表
     * */
    @Override
    public Result spaceTiebaList() {
        //获取当前登录用户
        String token = ServletContext.getRequest().getHeader("token");
        User user = tokenUtil.getLoginUser(token);
        //根据用户id查询贴吧数据
        QueryWrapper<Tieba> wrapper = new QueryWrapper<>();
        wrapper.eq("create_by",user.getId());
        List<Tieba> tiebas = tiebaMapper.selectList(wrapper);
        //封装数据
        List<TiebaVo> list = tiebas.stream().map(item->{
            TiebaVo tiebaVo = new TiebaVo();
            BeanUtils.copyProperties(item,tiebaVo);
            //查询是否点赞了
            if (item.getLikeUsers() != null){
                tiebaVo.setIsLike(item.getLikeUsers().contains(user.getId()));
            }else{
                tiebaVo.setIsLike(false);
            }
            return tiebaVo;
        }).collect(Collectors.toList());
        //封装数据
        TiebaListVo tiebaListVo = new TiebaListVo();
        tiebaListVo.setTotal(list.size());
        tiebaListVo.setRows(list);
        //返回
        return Result.ok(tiebaListVo);
    }
    /*
     * 删除贴吧
     * */
    @Override
    public Result deleteTieba(Long tid) {
        //根据id删除贴吧
        int i = tiebaMapper.deleteById(tid);
        if (i>0){
            return Result.ok(StatusCode.TIEBA_DELETE);
        }
        return Result.error();
    }
    /*
     * 个人空间贴吧搜索
     * */
    @Override
    public Result spaceTiebaSearch(Search search) {
        //判断是否登录
        String token = ServletContext.getRequest().getHeader("token");
        User user = tokenUtil.getLoginUser(token);
        //构造条件
        LambdaQueryWrapper<Tieba> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tieba::getCreateBy, user.getId())
                .like(search.getKeyWord() != "", Tieba::getTitle, search.getKeyWord())
                .between(search.getDate().size() != 0, Tieba::getCreateTime, search.getDate().get(0), search.getDate().get(1));
        List<Tieba> tiebas = tiebaMapper.selectList(wrapper);
        //封装数据
        List<TiebaVo> list = tiebas.stream().map(item->{
            TiebaVo tiebaVo = new TiebaVo();
            BeanUtils.copyProperties(item,tiebaVo);
            //查询是否点赞了
            if (item.getLikeUsers() != null){
                tiebaVo.setIsLike(item.getLikeUsers().contains(user.getId()));
            }else{
                tiebaVo.setIsLike(false);
            }
            return tiebaVo;
        }).collect(Collectors.toList());
        //封装数据
        TiebaListVo tiebaListVo = new TiebaListVo();
        tiebaListVo.setTotal(list.size());
        tiebaListVo.setRows(list);
        //返回
        return Result.ok(tiebaListVo);
    }


}

