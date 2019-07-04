package com.six.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web
	.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

import com.six.dao.AdminDao;
import com.six.dao.StudentDao;
import com.six.dao.TeacherDao;
import com.six.service.LoginService;
import com.six.service.impl.LoginServiceImpl;

/**
 * @author gede
 * @version date：2019年6月15日 下午4:44:56
 * @description ：
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AdminDao adminDao;
	@Autowired
	TeacherDao teacherDao;
	@Autowired
	StudentDao studentDao;
	
	@Autowired
	DataSource datasource;
	  
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(new LoginServiceImpl(adminDao, teacherDao, studentDao));
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
			// TODO Auto-generated method stub
			http.authorizeRequests() 
			.antMatchers("/h-ui/**").permitAll()
			  .antMatchers("/esayui/**").permitAll()
			  .antMatchers("/verify**").permitAll()
			  .antMatchers("/select").permitAll()
			  .antMatchers("/login.jsp").permitAll()
			  .antMatchers("/").hasAnyRole("TEACHER","ADMIN","STUDENT")
			  .anyRequest().authenticated() .and().logout().logoutSuccessUrl("/")
			  .and().formLogin().loginPage("/login.jsp") .successHandler(new SimpleUrlAuthenticationSuccessHandler("/")) 
			  .and().csrf().disable();
			http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN));
		}
		@Override
		public void configure(WebSecurity websec) throws Exception {
			// TODO Auto-generated method stub
			
		}
	 
}
