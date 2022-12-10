package cn.xdevops.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 基础的类
 * @author: Lidong
 * @time: 2020/7/29 21:14
 **/
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

    /**
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Author LiDong
     * @Description //TODO 处理HttpServletRequest拿到参数的Map
     * @Date 20:17 2020/11/30
     * @Param [request]
     **/
    public Map<String, String> getParamMap(HttpServletRequest request) {
        try {
            if (Objects.isNull(this.request)) {
                this.request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            }
            Map<String, String[]> parameterMap = this.request.getParameterMap();
            Map<String, String> map = new HashMap<>(16);
            // 以form的方式传递参数
            if (parameterMap != null && parameterMap.size() > 0) {
                for (String key : parameterMap.keySet()) {
                    String[] strings = parameterMap.get(key);
                    String value = Arrays.asList(strings).get(0);
                    map.put(key, String.valueOf(value));
                }
                return map;
            }else{
                // 从流中读取参数
                ServletInputStream inputStream = request.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                int data;
                while ((data=inputStream.read())!=-1){
                    stringBuffer.append((char)data);
                }
                if(Objects.nonNull(stringBuffer)){
                    String res= String.valueOf(stringBuffer);
                    try {
                        Map<String, String> mp = JSONObject.parseObject(res, Map.class);
                        if(Objects.nonNull(mp)){
                            return mp;
                        }
                    }catch (Exception e){
                        logger.info("=====> JSON解析参数出错"+e.getMessage());
                    }
                }
                return new HashMap<>(1);
            }
        } catch (Exception e) {
            logger.error("=====>getParamMap()出错", e.getMessage(), e);
            return new HashMap<>(1);
        }
    }

}
