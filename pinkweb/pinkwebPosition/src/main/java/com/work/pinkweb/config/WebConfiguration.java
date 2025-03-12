package com.work.pinkweb.config;

import com.work.pinkweb.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Web 配置类，用于配置跨域访问、异步支持、拦截器和静态资源处理
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private TokenInterceptor tokenInterceptor;

    // 构造方法，注入 TokenInterceptor
    public WebConfiguration(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }


    /**
     * 添加跨域映射配置
     * 允许所有子域名的跨域访问，支持凭证、所有头信息和方法
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    /**
     * 配置异步请求的支持
     * 设置线程池大小为 3，异步请求默认超时时间为 30000 毫秒
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(
                3)));
        configurer.setDefaultTimeout(30000);
    }

    /**
     * 添加拦截器配置
     * 对所有请求进行拦截，但排除登录、注册和静态资源请求
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        // 排除拦截，除了注册登录(此时还没token)，其他都拦截
        excludePath.add("/login/admin_login");  // 登录
        excludePath.add("/login/company_login");  // 登录
        excludePath.add("/premise/apply_login");  // 登录
        excludePath.add("/premise/apply_register/signup");  // 登录
        excludePath.add("/premise/apply_register/base");  // 登录
        excludePath.add("/premise/apply_register/getUser/**");  // 登录
        excludePath.add("/**");  // 登录
        excludePath.add("/static/**");  // 静态资源
        excludePath.add("/assets/**");  // 静态资源

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    /**
     * 配置静态资源处理
     * 将 classpath 下的 /static/ 目录映射到 URL 路径 /static/**
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
