package omsu.imit.schedule

import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.PersonalData
import omsu.imit.schedule.model.UserRole
import omsu.imit.schedule.repository.PersonalDataRepository
import omsu.imit.schedule.requests.SignInRequest
import omsu.imit.schedule.requests.SignUpRequest
import omsu.imit.schedule.service.AuthService
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
    lateinit var passwordEncoder: PasswordEncoder
    @Mock
    lateinit var personalDataRepository: PersonalDataRepository

    private lateinit var authService: AuthService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.authService = AuthService(
                this.passwordEncoder,
                this.personalDataRepository)
    }

    @Test
    fun testSignUpStudent() {
        val request = SignUpRequest("FirstName", "Patronymic", "LastName", "student@omsu.ru", "password", UserRole.STUDENT)
        val personalData = PersonalData(0, request.firstName, request.patronymic, request.lastName, request.email, request.password, UserRole.STUDENT)
        val response = PersonalData(personalData.id, request.firstName, request.patronymic, request.lastName, personalData.email, personalData.password, personalData.userRole)

        `when`(passwordEncoder.encode(request.password)).thenReturn(request.password)
        `when`(personalDataRepository.save(personalData)).thenReturn(personalData)
        `when`(personalDataRepository.save(personalData)).thenReturn(personalData)

        assertEquals(response, authService.signUp(request))

        verify(passwordEncoder).encode(request.password)
        verify(personalDataRepository).save(personalData)
        verify(personalDataRepository).save(personalData)
    }

    @Test
    fun testSignUpAlreadyExistingStudent() {
        val request = SignUpRequest("FirstName", "Patronymic", "LastName", "student@omsu.ru", "password", UserRole.STUDENT)
        val user = PersonalData(0, request.firstName, request.patronymic, request.lastName, request.email, request.password, UserRole.STUDENT)

        `when`(personalDataRepository.findByEmail(request.email)).thenReturn(Optional.of(user))


        assertThrows(CommonValidationException::class.java) { authService.signUp(request) }
        verify(personalDataRepository).findByEmail(request.email)
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

        `when`(personalDataRepository.findByEmail(request.email)).thenReturn(Optional.empty())

        assertThrows(NotFoundException::class.java) { authService.signIn(request) }
        verify(personalDataRepository).findByEmail(request.email)
    }

}
