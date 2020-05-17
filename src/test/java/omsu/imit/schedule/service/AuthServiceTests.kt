package omsu.imit.schedule.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.SignInRequest
import omsu.imit.schedule.dto.request.SignUpRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.ConfirmationToken
import omsu.imit.schedule.repository.ConfirmationTokenRepository
import omsu.imit.schedule.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class AuthServiceTests : BaseTests() {

    @MockK
    lateinit var confirmationTokenRepository: ConfirmationTokenRepository

    @MockK
    lateinit var emailSenderService: EmailSenderService

    @MockK
    lateinit var passwordEncoder: PasswordEncoder

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var userService: UserService

    private lateinit var authService: AuthService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.authService = AuthService(
                this.confirmationTokenRepository,
                this.emailSenderService,
                this.passwordEncoder,
                this.userRepository,
                this.userService)
    }

    @Test
    fun testSignUpNewUser() {
        val user = getUser()
        val request = SignUpRequest(
                user.firstName,
                user.patronymic,
                user.lastName,
                user.email,
                user.password!!,
                user.role)

        every { passwordEncoder.encode(user.password) } returns user.password
        every { userRepository.findByEmail(request.email) } returns Optional.of(user)
        every { userRepository.save(user) } returns user

        assertEquals(user, authService.signUp(request))

        verify { userRepository.findByEmail(request.email) }
        verify { userRepository.save(user) }
        verify { passwordEncoder.encode(user.password) }
    }

    @Test
    fun testSignUpExistingUser() {
        val user = getUser()
        val request = SignUpRequest(
                user.firstName,
                user.patronymic,
                user.lastName,
                user.email,
                user.password!!,
                user.role)

        every { passwordEncoder.encode(user.password) } returns user.password
        every { userRepository.findByEmail(request.email) } returns Optional.empty()
        every { userRepository.save(user) } returns user

        assertEquals(user, authService.signUp(request))

        verify { userRepository.findByEmail(request.email) }
        verify { userRepository.save(user) }
        verify { passwordEncoder.encode(user.password) }
    }

    @Test
    fun testSignIn() {
        val user = getUser()
        val request = SignInRequest(user.email, user.password!!)

        every { userService.getUserByEmail(user.email) } returns user
        every { passwordEncoder.matches(request.password, user.password) } returns true

        assertEquals(user, authService.signIn(request))

        verify { userService.getUserByEmail(user.email) }
        verify { passwordEncoder.matches(request.password, user.password) }
    }

    @Test
    fun testSignInToNotConfirmedAccount() {
        val user = getUser(false)
        val request = SignInRequest(user.email, user.password!!)

        every { userService.getUserByEmail(user.email) } returns user
        assertThrows(CommonValidationException::class.java) { authService.signIn(request) }
        verify { userService.getUserByEmail(user.email) }
    }

    @Test
    fun testSignInWithIncorrectPassword() {
        val user = getUser()
        val request = SignInRequest(user.email, user.password!!)

        every { userService.getUserByEmail(user.email) } returns user
        every { passwordEncoder.matches(request.password, user.password) } returns false

        assertThrows(CommonValidationException::class.java) { authService.signIn(request) }

        verify { userService.getUserByEmail(user.email) }
        verify { passwordEncoder.matches(request.password, user.password) }
    }

    @Test
    fun testSendVerificationToken() {
        val user = getUser()
        val confirmationToken = ConfirmationToken(user)
        val subject = "Complete Registration!"

        every { confirmationTokenRepository.save(any<ConfirmationToken>()) } returns confirmationToken
        every { emailSenderService.sendEmail(user, subject, any<String>()) } returns mockk()

        authService.sendVerificationToken(user)

        verify { confirmationTokenRepository.save(any<ConfirmationToken>()) }
        verify { emailSenderService.sendEmail(user, subject, any<String>()) }
    }

    @Test
    fun testConfirmAccount() {
        val user = getUser(false)
        val confirmedUser = getUser(true)
        val token = UUID.randomUUID().toString()
        val confirmationToken = ConfirmationToken(0, token, user)

        every { confirmationTokenRepository.findByToken(token) } returns Optional.of(confirmationToken)
        every { userService.getUserById(user.id) } returns user
        every { userRepository.save(confirmedUser) } returns confirmedUser
        every { confirmationTokenRepository.deleteById(confirmationToken.id) } returns mockk()

        authService.confirmAccount(token)

        verify { confirmationTokenRepository.findByToken(token) }
        verify { userService.getUserById(user.id) }
        verify { userRepository.save(confirmedUser) }
        verify { confirmationTokenRepository.deleteById(confirmationToken.id) }
    }

    @Test
    fun testConfirmAccountWithNotExistedToken() {
        val token = UUID.randomUUID().toString()

        every { confirmationTokenRepository.findByToken(token) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { authService.confirmAccount(token) }
        verify { confirmationTokenRepository.findByToken(token) }
    }

}