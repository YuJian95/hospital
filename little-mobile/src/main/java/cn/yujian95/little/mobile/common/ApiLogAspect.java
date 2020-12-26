package cn.yujian95.little.mobile.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import cn.yujian95.little.mbg.modules.system.entity.SystemApiLog;
import cn.yujian95.little.mobile.modules.system.service.ISystemApiLogService;
import cn.yujian95.little.security.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/10/13
 */
@Aspect
@Component
@Order(1)
public class ApiLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogAspect.class);

    @Value("${spring.application.name}")
    private String application;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private ISystemApiLogService systemApiLogService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("execution(public * cn.yujian95.little.mobile.modules.*.controller.*.*(..))")
    public void apiLog() {
    }

    @Before("apiLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "apiLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("apiLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        // 记录请求信息
        SystemApiLog apiLog = new SystemApiLog();
        apiLog.setType(application);

        Object result = joinPoint.proceed();

        try {

            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;

            Method method = methodSignature.getMethod();

            if (method.isAnnotationPresent(ApiOperation.class)) {
                ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                apiLog.setDescription(apiOperation.value());
            }

            // 获取当前请求对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                String urlStr = request.getRequestURL().toString();

                String authHeader = request.getHeader(this.tokenHeader);

                if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
                    // The part after "Bearer "
                    String authToken = authHeader.substring(this.tokenHead.length());
                    String name = jwtTokenUtil.getUserNameFromToken(authToken);
                    apiLog.setUsername(name);
                }

                apiLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
                apiLog.setIp(request.getRemoteAddr());
                apiLog.setMethod(request.getMethod());

                apiLog.setUri(request.getRequestURI());
                apiLog.setUrl(request.getRequestURL().toString());
            }

            long endTime = System.currentTimeMillis();
            apiLog.setSpendTime((double) (endTime - startTime));
            apiLog.setStartTime(new Date(startTime));

            Object param = getParameter(method, joinPoint.getArgs());

            apiLog.setParameter(JSONUtil.toJsonStr(param));
            apiLog.setResult(JSONUtil.toJsonStr(result));
            Date now = new Date();
            apiLog.setGmtCreate(now);
            apiLog.setGmtModified(now);

            systemApiLogService.save(apiLog);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.info("apiLog: [{}]", JSONUtil.parse(apiLog));

        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     *
     * @param method 方法
     * @param args   传入参数
     * @return 请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {

            // 将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }

            // 将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>(16);
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }

        return getApiParam(argList);
    }

    private Object getApiParam(List<Object> argList) {
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
