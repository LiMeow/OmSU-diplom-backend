package omsu.imit.schedule.services

import omsu.imit.schedule.dto.MailContentDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context


@Service
class MailContentBuilder
@Autowired
constructor(private val templateEngine: TemplateEngine) {

    fun build(contentDto: MailContentDto): String {
        LOGGER.info("Build mail message {}", contentDto)
        val context = Context()
        context.setVariable("mailContent", contentDto)
        return templateEngine.process("email", context)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MailContentBuilder::class.java)
    }
}