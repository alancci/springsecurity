package com.project.springsecurity.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @desc
 * springboot中集成springsecurity
 * 1.导入spring-security依赖
 *          <dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-starter-security</artifactId>
 *         </dependency>
 *         版本问题：指定   <spring-security.version>5.1.11.BUILD-SNAPSHOT</spring-security.version>
 *         2.编写配置文件
 *         SecurityConfig：继承WebSecurityConfiguerAdapter 重写configure方法
 *              实现HttpSecurity的 定义请求授权规则 定义角色
 *              实现AuthenticationManagerBuilder的 定义认证规则 给用户分配角色 注意要对前端传过来的密码进行加密
 *              BCrypt官方推荐加密方式
 * @author  alancci
 * @date   2020/7/31 9:24
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //定义认证为规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("vip2","vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("admin")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("admin")).roles("vip1","vip2");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定制请求的授权规则
        // 首页所有人可以访问
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/toLogin")
                .loginProcessingUrl("/login");
        http.logout().logoutSuccessUrl("/");
        http.rememberMe().rememberMeParameter("remember");
    }
}
