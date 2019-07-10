package com.yunze.vehiclemanagement.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @Value("${spring.datasource.StatViewServlet.loginUsername}")
    private String loginUsername;

    @Value("${spring.datasource.StatViewServlet.loginPassword}")
    private String loginPassword;
    /**
     * 数据源
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * druid监控Filter
     */
    @Bean
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean();
        bean.setServlet(new StatViewServlet());
        bean.addUrlMappings("/druid/*");
        //设置控制台管理用户
        bean.addInitParameter("loginUsername",loginUsername);
        bean.addInitParameter("loginPassword",loginPassword);
        // 禁用HTML页面上的“Reset All”功能
        bean.addInitParameter("resetEnable","false");
        //reg.addInitParameter("allow", "127.0.0.1"); //白名单
        return bean;
    }

    @Bean
    public FilterRegistrationBean webStatFilter(){
        //创建注册器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //设置过滤器
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<String, String>();
        //忽略过滤的形式
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        //设置过滤器过滤路径
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
