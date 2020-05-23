package omsu.imit.schedule

import com.fasterxml.jackson.databind.ObjectMapper
import omsu.imit.schedule.dto.request.SignInRequest
import omsu.imit.schedule.dto.request.SignUpRequest
import omsu.imit.schedule.model.Role
import omsu.imit.schedule.model.User
import omsu.imit.schedule.security.JwtTokenProvider
import omsu.imit.schedule.services.AuthService
import omsu.imit.schedule.services.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests : BaseTests() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var authService: AuthService

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var jwtTokenProvider: JwtTokenProvider


    @Test
    fun testSignUp() {
        val URL = "/signup"
        val user = getUser(enabled = false)

        val response = getUser(enabled = false, role = Role.ROLE_USER, password = "")
        val request = SignUpRequest(
                "FirstName",
                "Patronymic",
                "LastName",
                "example@gmail.com",
                "password",
                Role.ROLE_USER)

        `when`(authService.signUp(request)).thenReturn(user)
        doNothing().`when`(authService).sendVerificationToken(user)

        val result: MvcResult = mockMvc.perform(post(URL)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andReturn()


        assertEquals(response, mapper.readValue(result.response.contentAsString, User::class.java))
        verify(authService, times(1)).signUp(request)
        verify(authService, times(1)).sendVerificationToken(user)
    }

    @Test
    fun testSignIn() {
        val URL = "/signin"
        val user = getUser(enabled = false)

        val response = getUser(enabled = false, role = Role.ROLE_USER, password = "")
        val request = SignInRequest(
                "example@gmail.com",
                "password")

        `when`(authService.signIn(request)).thenReturn(user)
        `when`(jwtTokenProvider.createToken(user.email, listOf(user.role))).thenReturn("token")

        val result: MvcResult = mockMvc.perform(post(URL)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andReturn()


        assertEquals(response, mapper.readValue(result.response.contentAsString, User::class.java))
        assertEquals("token", result.response.cookies[0].value)

        verify(authService, times(1)).signIn(request)
        verify(jwtTokenProvider, times(1)).createToken(user.email, listOf(user.role))
    }

    @Test
    fun testConfirmAccount() {
        val URL = "/confirm-account"
        val confirmationToken = "token"

        doNothing().`when`(authService).confirmAccount(confirmationToken)

        mockMvc.perform(get(URL)
                .param("token", confirmationToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andReturn()

        verify(authService, times(1)).confirmAccount(confirmationToken)
    }
}