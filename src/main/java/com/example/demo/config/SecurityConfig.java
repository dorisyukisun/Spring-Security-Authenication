package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.ui.LoginPageGeneratingWebFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.handle.MyAccessDeniedHandler;

//若要自訂登入邏輯則要繼承 WebSecurityConfigurerAdapter

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;
	
	@Override
	protected void configure (HttpSecurity http) throws Exception{
		//表單提交
		  http.formLogin()
		     // loginpage.html 表單 action 內容
		  .loginProcessingUrl("/login");
		    // 自定義登入頁面
		  .loginPage("/loginpage");
		   //登入成功之後要造訪的頁面 
		  .successForwardUrl("/"); //welcome 頁面
		  //登入失敗後要造訪的頁面
		  .failureForwardUrl("/fail");
		  
		  //授權認證
		  http.authorizeHttpRequests();
		  			// 不需要認證的頁面:/loginpage
		  			.antMatchers("/loginpage").permitAll()
		  			//權限判斷
		  			//必須要有admin權限才可以訪問
		  			.antMatchers("/mangerpage").hasRole("manager")
		  			//其他指定任意角色都可以訪問
		  			.antMatchers("/employeepage").hasAnyRole("manager","employee")
		  			//其他的都要被認證
		  			.anyRequest().authenicated();
		  			
		  			//http.csrf().disable(); //關閉 csrf 防護
		  			
		  			//登出
		  			http.logout()
		  				.deleteCookies("JSESSIONID")
		  				.logoutSuccessUrl("loginpage")
		  				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		  			     //可使用任何的http方法登出
		  			
		  			//異常處理
		  			http.exceptionHandling()
		  				//.accessDeniedPage("/異常處理頁面); //請自行撰寫
		  			.accessDeniedHandler(myAccessDeniedHandler);
		  			
		  			//勿忘我 remember me
		  			http.rememberMe()
		  				.userDetailsService(userDetailsService)
		  				.tokenValiditySeconds(60)// 通常都會大於 session time out 的時間
	}
		//注意 ！規定 ！要建立密碼演算的實力
		 @Bean
		 public PasswordEncoder getPassordEncoder() {
			 return new BCryptPasswordEncoder();
		 }
	 
 

}
