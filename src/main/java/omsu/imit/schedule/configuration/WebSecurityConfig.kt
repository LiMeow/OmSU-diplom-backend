package omsu.imit.schedule.configuration

import omsu.imit.schedule.security.JwtTokenFilterConfigurer
import omsu.imit.schedule.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
class WebSecurityConfig(@Autowired private val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.cors()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(
                        "/signup/**",
                        "/signin",
                        "/signout",
                        "/token",
                        "/debug/clear",
                        "/confirm-account").permitAll()
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers(
                        "/buildings/**",
                        "/classrooms/**",
                        "/events/**",
                        "/timeblocks/**").hasAnyRole("ADMIN", "DISPATCHER")
                .antMatchers(
                        "/chairs/**",
                        "/disciplines/**",
                        "/faculties/**",
                        "/groups/**",
                        "/lecturers/**",
                        "/schedules/**").hasAnyRole("ADMIN", "DISPATCHER", "LECTURER")
                .anyRequest().authenticated();

        http.apply(JwtTokenFilterConfigurer(jwtTokenProvider));
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/v2/api-docs")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/configuration/**")
                .antMatchers("/webjars/**")
                .antMatchers("/public")

    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = Arrays.asList("http://localhost:3000")
        configuration.allowedMethods = Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH")
        configuration.allowedHeaders = listOf(
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Headers",
                "Origin", "Accept",
                "X-Requested-With",
                "Content-Type",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
