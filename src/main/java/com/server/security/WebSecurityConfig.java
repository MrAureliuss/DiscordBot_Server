package com.server.security;

import com.server.security.JWT.JWTUserGetter;
import com.server.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс для конфигурирования безопасности проекта.
 *
 * @see WebSecurityConfig#configure(HttpSecurity)
 * @see LoginPageFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
 *
 * @author Aurelius
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserServiceImpl userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    class LoginPageFilter extends GenericFilterBean {
        /**
         * Метод для фильтрации. Запрещает аутентифицированным пользователям обращаться по URL /authentication
         *
         *
         * @param request  Входящий запрос
         * @param response Серверный ответ
         * @param chain Предоставляет доступ к следующему фильтру в цепочке
         *
         * @throws IOException
         * @throws ServletException
         */
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            if (SecurityContextHolder.getContext().getAuthentication() != null
                    && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                    && ((HttpServletRequest)request).getRequestURI().equals("/authentication")) {
                ((HttpServletResponse)response).sendRedirect("/");
            }
            chain.doFilter(request, response);
        }

    }

    /**
     * Метод для составлени условий доступа пользователей к различным частям веб-ресурса.
     *
     * @param httpSecurity the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(
                new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);

        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .mvcMatchers("/authentication").anonymous()
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin/**").hasRole("ADMIN")
                //Доступ разрешен всем пользователей
                .antMatchers("/sign_in", "/sign_up", "/authentication", "/submit_channel" ,"/resources/**", "/css/**", "/libs/**", "/js/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/authentication")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/authentication");
    }

}
