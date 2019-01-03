package cc.mrbird.core.interceptor;

import cc.mrbird.core.log.ManagerInvokeLog;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author Leo on 07/12/2016.
 */
@Component
//@Aspect
//@Lazy(false)
public class ManagerInterceptor {

    @Around("execution( public * com.leo.workbench.core.*.manager.*.impl.*.*(..))")
    public Object aroundInvoke(final ProceedingJoinPoint pjp) throws Throwable {
        Class<?> targetClass = pjp.getTarget().getClass();
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        String methodName = targetClass.getSimpleName() + "." + signature.getMethod().getName();
        String params = JSON.toJSONString(pjp.getArgs());
        long startTime = System.currentTimeMillis();
        Throwable throwable = null;
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable t) {
            throwable = t;
        }
        long endTime = System.currentTimeMillis();
        long timeCost = endTime - startTime;
        if (throwable != null) {
            ManagerInvokeLog.error(throwable, "invoke [%s] failed, params:[%s], msg:[%s], timeCost:[%s]", methodName, params, throwable.getMessage(), timeCost);
            throw throwable;
        } else {
            ManagerInvokeLog.info("invoke [%s] success, params:[%s], result:[%s], timeCost:[%s]", methodName, params, JSON.toJSONString(result), timeCost);
        }
        return result;
    }
}
