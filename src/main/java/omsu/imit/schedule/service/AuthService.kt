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
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService
@Autowired
constructor(
        private val confirmationTokenRepository: ConfirmationTokenRepository,
        private val emailSenderService: EmailSenderService,
        private val passwordEncoder: PasswordEncoder,
        private val userRepository: UserRepository,
        private val userService: UserService) {

    fun signUp(request: SignUpRequest): User {
        val existingUser = userRepository.findByEmail(request.email)
        var user = User()

        when {
            existingUser.isPresent -> user = existingUser.get()
            existingUser.isEmpty -> user = User(
                    request.firstName,
                    request.patronymic, request.lastName,
                    request.email,
                    request.role)
        }

        user.password = passwordEncoder.encode(request.password)
        userRepository.save(user)

        return user
    }

    fun signIn(request: SignInRequest): User {
        val user = userService.getUserByEmail(request.email)

        if (!user.enabled)
            throw CommonValidationException(ErrorCode.ACCOUNT_NOT_ACTIVATED, user.email)

        if (!passwordEncoder.matches(request.password, user.password))
            throw CommonValidationException(ErrorCode.WRONG_PASSWORD)

        return user
    }

    fun sendVerificationToken(user: User) {
        val confirmationToken = ConfirmationToken(user)
        confirmationTokenRepository.save(confirmationToken)
        val subject = "Complete Registration!"
        val verificationToken = "http://localhost:8080/api/confirm-account?token=" + confirmationToken.token

        emailSenderService.sendEmail(user, subject, verificationToken)
    }

    fun confirmAccount(token: String) {
        val confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow { NotFoundException(ErrorCode.TOKEN_NOT_FOUND) }

        val user = userService.getUserById(confirmationToken.user.id)
        user.enabled = true;

        userRepository.save(user)
        confirmationTokenRepository.deleteById(confirmationToken.id)
    }
}
