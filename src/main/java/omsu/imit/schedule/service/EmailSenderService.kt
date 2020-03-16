package omsu.imit.schedule.service

import omsu.imit.schedule.dto.MailContentDto
import omsu.imit.schedule.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
open class EmailSenderService
@Autowired
constructor(private val javaMailSender: JavaMailSender,
            private val mailContentBuilder: MailContentBuilder) {

    @Async
    open fun sendEmail(user: User, subject: String, verifyToken: String) {
        val mimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, false, "UTF-8")
        val mailContent = mailContentBuilder.build(MailContentDto(user, verifyToken))

        helper.setTo(user.email)
        helper.setSubject(subject)
        helper.setText(mailContent, true)

        javaMailSender.send(mimeMessage)
    }
}