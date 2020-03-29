package omsu.imit.schedule.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Role
import omsu.imit.schedule.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class UserServiceTests : BaseTests() {

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var userService: UserService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.userService = UserService(this.userRepository)
    }

    @Test
    fun testGetUserByEmail() {
        val user = getUser()

        every { userRepository.findByEmail(user.email) } returns Optional.of(user)

        assertEquals(user, userService.getUserByEmail(user.email))
        verify { userRepository.findByEmail(user.email) }
    }

    @Test
    fun testGetUserByNonExistingEmail() {
        val email = "wrongEmail@gmail.com"

        every { userRepository.findByEmail(email) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { userService.getUserByEmail(email) }
        verify { userRepository.findByEmail(email) }
    }

    @Test
    fun testGetUserById() {
        val user = getUser()

        every { userRepository.findById(user.id) } returns Optional.of(user)

        assertEquals(user, userService.getUserById(user.id))
        verify { userRepository.findById(user.id) }
    }

    @Test
    fun testGetUserByNonExistingId() {
        val id = 1

        every { userRepository.findById(id) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { userService.getUserById(id) }
        verify { userRepository.findById(id) }
    }

    @Test
    fun testDeleteAccount() {
        val id = 1;

        every { userRepository.existsById(id) } returns true
        every { userRepository.deleteById(id) } returns mockk()

        userService.deleteAccount(id)

        verify { userRepository.existsById(id) }
        verify { userRepository.deleteById(id) }
    }

    @Test
    fun testDeleteNonExistingAccount() {
        val id = 1;

        every { userRepository.existsById(id) } returns false
        assertThrows(NotFoundException::class.java) { userService.deleteAccount(id) }

        verify { userRepository.existsById(id) }
    }

    @Test
    fun testDisableAccount() {
        val user = getUser()
        val disabledUser = getUser(false)

        every { userRepository.findById(user.id) } returns Optional.of(user)
        every { userRepository.save(disabledUser) } returns disabledUser

        assertTrue { user.enabled }
        assertEquals(disabledUser, userService.disableAccount(user.id))

        verify { userRepository.findById(user.id) }
        verify { userRepository.save(disabledUser) }
    }

    @Test
    fun testDisableNonExistingAccount() {
        val id = 1

        every { userRepository.findById(id) } returns Optional.empty()
        assertThrows(NotFoundException::class.java) { userService.disableAccount(id) }

        verify { userRepository.findById(id) }
    }

    @Test
    fun testChangeUserRole() {
        val user = getUser()
        val updatedUser = getUser(true, Role.ROLE_ADMIN)

        every { userRepository.findById(user.id) } returns Optional.of(user)
        every { userRepository.save(updatedUser) } returns updatedUser

        assertEquals(updatedUser, userService.changeUserRole(user.id, Role.ROLE_ADMIN))

        verify { userRepository.findById(user.id) }
        verify { userRepository.save(updatedUser) }
    }

    @Test
    fun testChangeRoleToNonExistingUser() {
        val id = 1

        every { userRepository.findById(id) } returns Optional.empty()
        assertThrows(NotFoundException::class.java) { userService.changeUserRole(id, Role.ROLE_ADMIN) }

        verify { userRepository.findById(id) }
    }
}