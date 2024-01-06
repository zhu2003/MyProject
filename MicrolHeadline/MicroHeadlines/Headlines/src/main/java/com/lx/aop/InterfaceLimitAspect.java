package com.lx.aop;

import com.lx.annotation.AccessLimit;
import com.lx.exception.AccessLimitException;
import com.lx.utils.Result;
import com.lx.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;


@Aspect
@Component
@Slf4j
public class InterfaceLimitAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    @Pointcut("@annotation(accessLimit)")
    public void pt(AccessLimit accessLimit){}

    @Around("pt(accessLimit)")
    public Object Around(ProceedingJoinPoint joinPoint,AccessLimit accessLimit) throws Throwable {
        // 获得request对象
        ServletRequestAttributes sra =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        log.info(request.getRequestURI());
        //redis这里推荐使用hash类型，url为外层key,ip作为内层key,访问次数作为value
        BoundHashOperations hashOps = redisTemplate.boundHashOps("interfaceLimit:"+request.getRequestURI());
        //获取ip获取接口访问次数
        Integer ipCount = (Integer)hashOps.get(request.getRemoteAddr());
        Integer count = ipCount==null?0:ipCount;
        //判断访问次数是否大于限制的次数
        if(count>=accessLimit.value()){//超过次数，不执行目标方法
            log.error("接口拦截：{} 请求超过限制频率【{}次/{}ms】,IP为{}",
                    request.getRequestURI(),
                    accessLimit.value(),
                    accessLimit.time(),
                    request.getRemoteAddr());
            throw new AccessLimitException(ResultCodeEnum.ACCESS_LIMIT);
        }else{
            //请求时，设置有效时间, 记录加一
            hashOps.increment(request.getRemoteAddr(),1);
            hashOps.expire(accessLimit.time(), TimeUnit.MILLISECONDS);
        }
        Object result = joinPoint.proceed();
        return result;
    }
}
