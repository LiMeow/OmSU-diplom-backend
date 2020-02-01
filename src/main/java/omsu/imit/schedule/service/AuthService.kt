package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.SignInRequest
import omsu.imit.schedule.dto.request.SignUpRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.ConfirmationToken
import omsu.imit.schedule.model.User
import omsu.imit.schedule.repository.ConfirmationTokenRepository
import omsu.imit.schedule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*


@Service
class AuthService
@Autowired
constructor(
        private val confirmationTokenRepository: ConfirmationTokenRepository,
        private val emailSenderService: EmailSenderService,
        private val passwordEncoder: PasswordEncoder,
        private val userRepository: UserRepository,
        private val userService: UserService) {
    private val EXPIRATION_TIME_IN_MINUTES = 60 * 24

    fun signUp(request: SignUpRequest): User {
        val user = User(
                request.firstName,
                request.patronymic, request.lastName,
                request.email,
                passwordEncoder.encode(request.password),
                request.userRole)
        try {
            userRepository.save(user)
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.USER_ALREADY_EXISTS, request.email)
        }
        return user
    }

    fun signIn(request: SignInRequest): User {
        val user = userService.getUserByEmail(request.email)

        if (!passwordEncoder.matches(request.password, user.password))
            throw CommonValidationException(ErrorCode.WRONG_PASSWORD)

        if (!user.enabled)
            throw CommonValidationException(ErrorCode.ACCOUNT_NOT_ACTIVATED, user.email)

        return user
    }

    fun sendVerificationToken(user: User) {
        val confirmationToken = ConfirmationToken(user, calculateTokenExpirationDate())
        confirmationTokenRepository.save(confirmationToken)

        val mailMessage = SimpleMailMessage();
        mailMessage.setTo(user.email)
        mailMessage.subject = "Complete Registration!"
        mailMessage.text = "To confirm your account, please click here : " +
                "http://localhost:8080/api/confirm-account?token=" + confirmationToken.token

        emailSenderService.sendEmail(mailMessage)
    }

    fun confirmAccount(token: String) {
        val confirmationToken = confirmationTokenRepository.findByToken(token)

        if (!confirmationToken.isPresent || checkTokenExpiration(confirmationToken.get()))
            throw  NotFoundException(ErrorCode.TOKEN_NOT_FOUND)

        val user = userService.getUserById(confirmationToken.get().user.id)

        user.enabled = true;
        userRepository.save(user)
    }

    private fun checkTokenExpiration(token: ConfirmationToken): Boolean {
        val cal = Calendar.getInstance()
        return token.expiredDate.time - cal.time.time <= 0
    }

    private fun calculateTokenExpirationDate(): Date {
        val cal = Calendar.getInstance()
        cal.time = Timestamp(cal.time.time)
        cal.add(Calendar.MINUTE, EXPIRATION_TIME_IN_MINUTES);
        return Date(cal.time.time);
    }
}
