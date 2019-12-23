package omsu.imit.schedule

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class ScheduleServer

fun main(args: Array<String>) {
    SpringApplication.run(ScheduleServer::class.java, *args)

}