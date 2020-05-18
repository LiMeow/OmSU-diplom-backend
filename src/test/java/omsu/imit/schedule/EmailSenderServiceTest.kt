package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.MailContentDto
import omsu.imit.schedule.model.User
import omsu.imit.schedule.services.EmailSenderService
import omsu.imit.schedule.services.MailContentBuilder
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import java.util.*
import javax.mail.Session
import javax.mail.internet.MimeMessage

@ExtendWith(MockKExtension::class)
class EmailSenderServiceTest : BaseTests() {

    @MockK
    lateinit var javaMailSender: JavaMailSender

    @MockK
    lateinit var mailContentBuilder: MailContentBuilder

    @Mock
    lateinit var mimeMessage: MimeMessage

    @Mock
    lateinit var helper: MimeMessageHelper

    private lateinit var emailSenderService: EmailSenderService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.mimeMessage = MimeMessage(Session.getDefaultInstance(Properties()))
        this.emailSenderService = EmailSenderService(this.javaMailSender, this.mailContentBuilder)
    }

    @Test
    fun testSendEmail() {
        val user = getUser()
        val subject = "Complete Registration!"
        val verificationToken = "token"
        val mailContentDto = getMailContentDto(user, verificationToken)

        every { javaMailSender.createMimeMessage() } returns (mimeMessage)
        every { mailContentBuilder.build(mailContentDto) } returns "<html></html>"
        every { javaMailSender.send(mimeMessage) } returns mockk()

        emailSenderService.sendEmail(user, subject, verificationToken)

        verify { javaMailSender.createMimeMessage() }
        verify { mailContentBuilder.build(mailContentDto) }
        verify { javaMailSender.send(mimeMessage) }
    }

    private fun getMailContentDto(user: User, verificationToken: String): MailContentDto {
        return MailContentDto(user, verificationToken)
    }
}