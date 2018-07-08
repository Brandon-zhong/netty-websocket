package com.netty4.server.scanner;

import com.netty4.common.SocketCommand;
import com.netty4.common.SocketModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 执行器扫描类
 *
 * @author brandon
 * Created by brandon on 2018/7/8.
 */
@Component
public class InvokerScanner implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clazz = bean.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();

        if (null != interfaces && interfaces.length > 0) {
            //扫描所有的接口类
            for (Class<?> interFace : interfaces) {
                SocketModule annotation = interFace.getAnnotation(SocketModule.class);
                //判断是否为handler接口
                if (null == annotation) {
                    continue;
                }
                //获取所有方法
                Method[] methods = interFace.getMethods();
                if (null != methods && methods.length > 0) {
                    for (Method method : methods) {
                        SocketCommand command = method.getAnnotation(SocketCommand.class);
                        //判断是否为命令方法
                        if (null == command) {
                            continue;
                        }
                        //获取模块号和命令号
                        short module = annotation.module();
                        short cmd = command.cmd();

                        Invoker invoker = InvokerManager.getInvoker(module, cmd);
                        if (null != null) {
                            System.out.println("重复命令:" + "module:" + module + " " + "cmd：" + cmd);
                            continue;
                        }
                        //封装invoker对象
                        InvokerManager.addInvoker(module, cmd, Invoker.valueOf(method, bean));
                    }
                }
            }
        }

        return bean;
    }
}
