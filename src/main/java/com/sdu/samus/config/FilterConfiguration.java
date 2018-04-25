package com.sdu.samus.config;

import com.sdu.samus.filter.AppContextFilter;
import com.sdu.samus.filter.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
	@Bean
	public FilterRegistrationBean sessionFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		//注入过滤器
		registration.setFilter(new SessionFilter());
		//拦截规则
		registration.addUrlPatterns("/*");
		//过滤器名称
		registration.setName("SessionFilter");
		//是否自动注册 false 取消Filter的自动注册
		registration.setEnabled(true);
		//过滤器顺序
		registration.setOrder(2);
		return registration;
	}

	@Bean
	public FilterRegistrationBean appContextFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		//注入过滤器
		registration.setFilter(new AppContextFilter());
		//拦截规则
		registration.addUrlPatterns("/*");
		//过滤器名称
		registration.setName("AppContextFilter");
		//是否自动注册 false 取消Filter的自动注册
		registration.setEnabled(true);
		//过滤器顺序
		registration.setOrder(1);
		return registration;
	}

}