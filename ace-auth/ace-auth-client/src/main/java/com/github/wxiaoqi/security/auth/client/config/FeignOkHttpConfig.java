package com.github.wxiaoqi.security.auth.client.config;

import com.github.wxiaoqi.security.auth.client.interceptor.OkHttpTokenInterceptor;
import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@AutoConfigureBefore(FeignAutoConfiguration.class)
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignOkHttpConfig {

	@Autowired
	OkHttpTokenInterceptor okHttpLoggingInterceptor;

	@Bean
	public OkHttpClient okHttpClient() {
		int feignOkHttpReadTimeout = 60;
		int feignConnectTimeout = 60;
		int feignWriteTimeout = 120;
		return new OkHttpClient.Builder().readTimeout(feignOkHttpReadTimeout, TimeUnit.SECONDS).connectTimeout(feignConnectTimeout, TimeUnit.SECONDS)
				.writeTimeout(feignWriteTimeout, TimeUnit.SECONDS).connectionPool(new ConnectionPool())
				.addInterceptor(okHttpLoggingInterceptor)
				.build();
	}
}
