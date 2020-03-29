package omsu.imit.schedule

import omsu.imit.schedule.security.JwtTokenProvider
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ScheduleServer

fun main(args: Array<String>) {
    SpringApplication.run(ScheduleServer::class.java, *args)

}

@Bean
fun jwtTokenProvider(): JwtTokenProvider {
    return JwtTokenProvider()
}