package omsu.imit.schedule.service

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import omsu.imit.schedule.repository.ConfirmationTokenRepository
import omsu.imit.schedule.repository.UserRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder

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

//    @Before
//    fun setUp() {
//        MockKAnnotations.init(this)
//        this.authService = AuthService(
//                this.confirmationTokenRepository,
//                this.emailSenderService,
//                this.passwordEncoder,
//                this.userRepository,
//                this.userService)
//    }
//
//    @Test
//    fun testSignUp() {
//        val user = getUser()
//        val request = SignUpRequest(
//                user.firstName,
//                user.patronymic,
//                user.lastName,
//                user.email,
//                user.password!!,
//                user.role)
//
//        every { passwordEncoder.encode(user.password) } returns user.password
//        every { userRepository.save(user) } returns user
//
//        assertEquals(user, authService.signUp(request))
//
//        verify { userRepository.save(user) }
//        verify { passwordEncoder.encode(user.password) }
//    }
//
//    @Test
//    fun testSignIn() {
//        val user = getUser()
//        val request = SignInRequest(user.email, user.password!!)
//
//        every { userService.getUserByEmail(user.email) } returns user
//        every { passwordEncoder.matches(request.password, user.password) } returns true
//
//        assertEquals(user, authService.signIn(request))
//
//        verify { userService.getUserByEmail(user.email) }
//        verify { passwordEncoder.matches(request.password, user.password) }
//    }

}