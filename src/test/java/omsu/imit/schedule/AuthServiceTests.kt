package omsu.imit.schedule

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.PersonalData
import omsu.imit.schedule.model.User
import omsu.imit.schedule.model.UserRole
import omsu.imit.schedule.repository.PersonalDataRepository
import omsu.imit.schedule.repository.UserRepository
import omsu.imit.schedule.requests.SignInRequest
import omsu.imit.schedule.requests.SignUpRequest
import omsu.imit.schedule.service.AuthService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.security.crypto.password.PasswordEncoder

@RunWith(MockitoJUnitRunner::class)
class AuthServiceTests {
    @Mock
    lateinit var passwordEncoder: PasswordEncoder
    @Mock
    lateinit var personalDataRepository: PersonalDataRepository
    @Mock
    lateinit var userRepository: UserRepository

    private lateinit var authService: AuthService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.authService = AuthService(
                this.userRepository,
                this.passwordEncoder,
                this.personalDataRepository)
    }

    @Test
    fun testSignUpStudent() {
        val request = SignUpRequest("FirstName", "Patronymic", "LastName", "student@omsu.ru", "password", UserRole.STUDENT)
        val user = User(0, request.email, request.password, UserRole.STUDENT)
        val personalData = PersonalData(0, user, request.firstName, request.patronymic, request.lastName)
        val response = User(user.id, user.email, user.password, user.userRole)

        `when`(passwordEncoder.encode(request.password)).thenReturn(request.password)
        `when`(userRepository.save(user)).thenReturn(user)
        `when`(personalDataRepository.save(personalData)).thenReturn(personalData)

        assertEquals(response, authService.signUp(request))

        verify(passwordEncoder).encode(request.password)
        verify(userRepository).save(user)
        verify(personalDataRepository).save(personalData)
    }

    @Test
    fun testSignUpAlreadyExistingStudent() {
        val request = SignUpRequest("FirstName", "Patronymic", "LastName", "student@omsu.ru", "password", UserRole.STUDENT)
        val user = User(0, request.email, request.password, UserRole.STUDENT)

        `when`(userRepository.findByEmail(request.email)).thenReturn(user)

        try {
            authService.signUp(request)
        } catch (ex: ScheduleGeneratorException) {
            assertEquals(ErrorCode.USER_ALREADY_EXISTS, ex.errorCode);
        }
        verify(userRepository).findByEmail(request.email)
    }

    @Test
    fun testSignIn() {
        val request = SignInRequest("student@omsu.ru", "password")
        val user = User(0, request.email, request.password, UserRole.STUDENT)
        val response = User(user.id, user.email, user.password, user.userRole)

        `when`(userRepository.findByEmail(request.email)).thenReturn(user)
        `when`(passwordEncoder.matches(request.password, user.password)).thenReturn(true)

        assertEquals(response, authService.signIn(request))

        verify(userRepository).findByEmail(request.email)
        verify(passwordEncoder).matches(request.password, user.password)
    }

    @Test
    fun testSignInByNonExistingUser() {
        val request = SignInRequest("student@omsu.ru", "password")

        `when`(userRepository.findByEmail(request.email)).thenReturn(null)
        try {
            authService.signIn(request)
        } catch (ex: ScheduleGeneratorException) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.errorCode);
        }
        verify(userRepository).findByEmail(request.email)
    }

}
