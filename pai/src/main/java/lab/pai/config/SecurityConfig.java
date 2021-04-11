package lab.pai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    public UserDetailsService userDetailsService;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
    	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
        .antMatchers("/delegation/adduserdelegtion").hasAnyRole("ROLE_USER")
        .antMatchers("/delegation/delete").hasAnyRole("ROLE_USER")
        .antMatchers("/delegation/changedelegation").hasAnyRole("ROLE_USER")
        .antMatchers("/delegation/getall").hasAnyRole("ROLE_USER")
        .antMatchers("/delegation/getdate").hasAnyRole("ROLE_USER")
        .antMatchers("/user/registeruser").hasAnyRole("ROLE_USER")
        .antMatchers("/user/allusers").hasAnyRole("ROLE_USER")
        .antMatchers("/user/changepassword").hasAnyRole("ROLE_USER")
        .antMatchers("/user/deleteuser").hasAnyRole("ROLE_USER")
        .antMatchers("/user/getdelegation").hasAnyRole("ROLE_USER")
        .antMatchers("/user/getallbyrolename").hasAnyRole("ROLE_USER")
        .antMatchers("/swagger-ui.html/**").hasAnyRole("ROLE_USER")
        .and().formLogin().loginPage("/login").loginProcessingUrl("/login")
        //.and().oauth2Login().loginPage("/login")
        .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login");
	}
	
	@Bean
    public  BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
