package omsu.imit.schedule.controller

import com.fasterxml.jackson.databind.ObjectMapper
import omsu.imit.schedule.dto.request.CreateClassroomRequest
import omsu.imit.schedule.dto.request.EditClassroomRequest
import omsu.imit.schedule.repository.UserRepository
import omsu.imit.schedule.service.BaseTests
import omsu.imit.schedule.service.ClassroomService
import org.junit.Before
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.*
import javax.servlet.http.Cookie


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ClassroomControllerTests : BaseTests() {
    private val URL = "/classrooms"
    private val email = "example@gmail.com"
    private lateinit var accessToken: Cookie

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var classroomService: ClassroomService

    @MockBean
    private lateinit var userRepository: UserRepository


    @Before
    fun setUp() {
        this.accessToken = obtainAccessToken(email)
    }

    @Test
    fun testCreateClassroom() {
        val classroom = getClassroom()
        val response = getClassroomShortInfo(classroom)
        val request = CreateClassroomRequest(1, "214")

        `when`(classroomService.createClassroom(request)).thenReturn(response)

        mockMvc.perform(post(URL)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .cookie(accessToken))
                .andExpect(status().isOk)
                .andReturn();

        verify(classroomService, times(1)).createClassroom(request)
    }

    @Test
    fun testGetClassroomById() {
        val classroom = getClassroom()
        val response = getClassroomInfo(classroom)

        `when`(classroomService.getClassroomInfo(classroom.id)).thenReturn(response)

        mockMvc.perform(
                        get("${URL}/${classroom.id}")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .cookie(accessToken))
                .andExpect(status().isOk)
                .andReturn();

        verify(classroomService, times(1)).getClassroomInfo(classroom.id)
    }


    @Test
    fun testGetClassroomsByTags() {
        val classroom = getClassroom()
        val tags = listOf("1", "2")
        val response = listOf(getClassroomShortInfo(classroom))
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("tags", "1, 2")

        `when`(classroomService.getClassroomsByTags(tags)).thenReturn(response)

        mockMvc.perform(get(URL)
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .cookie(accessToken))
                .andExpect(status().isOk)
                .andReturn();

        verify(classroomService, times(1)).getClassroomsByTags(tags)
    }

    @Test
    fun testEditClassroom() {
        val classroom = getClassroom()
        val request = EditClassroomRequest("215")
        val response = getClassroomShortInfo(classroom)

        `when`(classroomService.editClassroom(classroom.id, request)).thenReturn(response)

        mockMvc.perform(put("${URL}/${classroom.id}")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .cookie(accessToken))
                .andExpect(status().isOk)
                .andReturn();

        verify(classroomService, times(1)).editClassroom(classroom.id, request)
    }

    @Test
    fun testDeleteClassroom() {
        val classroom = getClassroom()

        doNothing().`when`(classroomService).deleteClassroom(classroom.id)

        mockMvc.perform(delete("${URL}/${classroom.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .cookie(accessToken))
                .andExpect(status().isOk)
                .andReturn();

        verify(classroomService, times(1)).deleteClassroom(classroom.id)
    }

    @Throws(Exception::class)
    fun obtainAccessToken(email: String): Cookie {
        val user = getUser()
        `when`(userRepository.findByEmail(user.email)).thenReturn(Optional.of(user))

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("email", email)

        val result = mockMvc.perform(get("/token")
                        .params(params)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json;charset=UTF-8"))

        return result.andReturn().response.getCookie("accessToken")
    }
}