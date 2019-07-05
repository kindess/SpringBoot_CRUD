package com.yunze.vehiclemanagement.aspect;
import com.yunze.vehiclemanagement.annotation.CacheRemove;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 功能描述:清除缓存切面类
 *
 */
@Component
@Aspect
public class CacheRemoveAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "redisTemplate")
    RedisTemplate<String, String> redis;

    //截获标有@CacheRemove的方法
    @Pointcut(value = "(execution(* *.*(..)) && @annotation(com.yunze.vehiclemanagement.annotation.CacheRemove))")
    private void pointcut() {
    }

    /**
     * 功能描述: 切面在截获方法返回值之后
     *
     * @return void
     * @throws
     * @author fuyuchao
     * @date 2018/9/14 16:55
     * @params [joinPoint]
     */
    @AfterReturning(value = "pointcut()")
    private void process(JoinPoint joinPoint) {
        //获取被代理的类
        Object target = joinPoint.getTarget();
        //获取切入方法的数据
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入方法
        Method method = signature.getMethod();
        //获得注解
        CacheRemove cacheRemove = method.getAnnotation(CacheRemove.class);

        //方法上是否存在此注解
        if (cacheRemove != null) {
            //清除当前类的缓存
//            cleanRedisCache("*" + target.getClass().toString() + "*");

            //清除当前类的缓存（清除指定缓存名的下的所有缓存）
           /* String value = cacheRemove.value();
            if (!value.equals("")) {
                //缓存的项目所有redis业务部缓存
                cleanRedisCache("*" + value + "*");
            }*/
            //需要移除的正则key
            String[] keys = cacheRemove.key();
            for (String key : keys) {
                //指定清除的key的缓存
                cleanRedisCache("*" + key + "*");
            }
        }
    }

    private void cleanRedisCache(String key) {
        if (key != null) {
            Set<String> stringSet = redis.keys(key);
            redis.delete(stringSet);//删除缓存
            logger.info("清除 " + key + " 缓存");
        }
    }
}