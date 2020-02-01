package omsu.imit.schedule

import omsu.imit.schedule.dto.request.SignInRequest
import omsu.imit.schedule.dto.request.SignUpRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.User
import omsu.imit.schedule.model.UserRole
import omsu.imit.schedule.repository.ConfirmationTokenRepository
import omsu.imit.schedule.repository.UserRepository
import omsu.imit.schedule.service.AuthService
import omsu.imit.schedule.service.EmailSenderService
import omsu.imit.schedule.service.UserService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class AuthServiceTests {
    @Mock
    lateinit var confirmationTokenRepository: ConfirmationTokenRepository

    @Mock
    lateinit var emailSenderService: EmailSenderService

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var userService: UserService

    private lateinit var authService: AuthService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.authService = AuthService(
                this.confirmationTokenRepository,
                this.emailSenderService,
                this.passwordEncoder,
                this.userRepository,
                this.userService)
    }

    @Test
    fun testSignUpStudent() {
        val request = SignUpRequest("FirstName", "Patronymic", "LastName", "student@omsu.ru", "password", UserRole.STUDENT)
        val personalData = User(0, request.firstName, request.patronymic, request.lastName, request.email, request.password, UserRole.STUDENT, false)
        val response = User(personalData.id, request.firstName, request.patronymic, request.lastName, personalData.email, personalData.password, personalData.userRole, false)

        `when`(passwordEncoder.encode(request.password)).thenReturn(request.password)
        `when`(userRepository.save(personalData)).thenReturn(personalData)
        `when`(userRepository.save(personalData)).thenReturn(personalData)

        assertEquals(response, authService.signUp(request))

        verify(passwordEncoder).encode(request.password)
        verify(userRepository).save(personalData)
        verify(userRepository).save(personalData)
    }

    @Test
    fun testSignUpAlreadyExistingStudent() {
        val request = SignUpRequest("FirstName", "Patronymic", "LastName", "student@omsu.ru", "password", UserRole.STUDENT)
        val user = User(0, request.firstName, request.patronymic, request.lastName, request.email, request.password, UserRole.STUDENT, false)

        `when`(userRepository.findByEmail(request.email)).thenReturn(Optional.of(user))


        assertThrows(CommonValidationException::class.java) { authService.signUp(request) }
        verify(userRepository).findByEmail(request.email)
    }

//    @Test
//    fun testSignIn() {
//        val request = SignInRequest("student@omsu.ru", "password")
//        val user = PersonalData(0, request.email, request.password, UserRole.STUDENT)
//        val response = PersonalData(user.id, user.email, user.password, user.userRole)
//
//        `when`(userRepository.findByEmail(request.email)).thenReturn(user)
//        `when`(passwordEncoder.matches(request.password, user.password)).thenReturn(true)
//
//        assertEquals(response, authService.signIn(request))
//
//        verify(userRepository).findByEmail(request.email)
//        verify(passwordEncoder).matches(request.password, user.password)
//    }

    @Test
    fun testSignInByNonExistingUser() {
        val request = SignInRequest("student@omsu.ru", "password")

        `when`(userRepository.findByEmail(request.email)).thenReturn(Optional.empty())

        assertThrows(NotFoundException::class.java) { authService.signIn(request) }
        verify(userRepository).findByEmail(request.email)
    }

}
