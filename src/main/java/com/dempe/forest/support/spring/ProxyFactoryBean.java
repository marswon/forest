package com.dempe.forest.support.spring;

import com.dempe.forest.client.proxy.ForestDynamicProxy;
import com.dempe.forest.config.MethodConfig;
import com.dempe.forest.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2016/12/5
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
public class ProxyFactoryBean implements FactoryBean<Object>, InitializingBean, DisposableBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProxyFactoryBean.class);

    private Class<?> serviceInterface;

    private Map<String, MethodConfig> methodConfigMap;

    private Object proxyBean;

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public Object getObject() throws Exception {
        return proxyBean;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    public Map<String, MethodConfig> getMethodConfigMap() {
        return methodConfigMap;
    }

    public void setMethodConfigMap(Map<String, MethodConfig> methodConfigMap) {
        this.methodConfigMap = methodConfigMap;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceConfig config = ServiceConfig.Builder.newBuilder().build();
        for (Map.Entry<String, MethodConfig> methodConfigEntry : methodConfigMap.entrySet()) {
            config.registerMethodConfig(methodConfigEntry.getKey(), methodConfigEntry.getValue());
        }
        proxyBean = ForestDynamicProxy.newInstance(serviceInterface, config);

    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
