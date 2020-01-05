package omsu.imit.schedule.configuration

import omsu.imit.schedule.jwt.JwtAuthenticationProvider
import omsu.imit.schedule.jwt.JwtSettings
import omsu.imit.schedule.jwt.JwtTokenService
import omsu.imit.schedule.jwt.filters.CookieJwtAuthFilter
import omsu.imit.schedule.service.JsonWebTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher


@Configuration
@EnableWebSecurity
open class WebSecurityConfig(val jwtTokenService: JwtTokenService) : WebSecurityConfigurerAdapter() {

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun jwtTokenService(settings: JwtSettings): JwtTokenService {
        return JsonWebTokenService(settings)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.formLogin().disable()
        http.logout().disable()
        http.sessionManagement().disable()
        http.requestCache().disable()
        http.anonymous()

        val loginPageMatcher = AntPathRequestMatcher("/signin")
        val notLoginPageMatcher = NegatedRequestMatcher(loginPageMatcher)
        val authFilter = CookieJwtAuthFilter(notLoginPageMatcher)

        http.addFilterBefore(authFilter, FilterSecurityInterceptor::class.java)

        http

                .authorizeRequests().antMatchers(
                        "/signup/**",
                        "/signin",
                        "/signout").permitAll()
                .and()
                .authorizeRequests().antMatchers("/debug/clear").permitAll()
                .and()
                .authorizeRequests().antMatchers(
                        "/v2/api-docs",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().permitAll()

    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(JwtAuthenticationProvider(jwtTokenService))
    }
}
