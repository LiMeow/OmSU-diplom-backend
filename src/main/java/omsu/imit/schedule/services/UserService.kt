package omsu.imit.schedule.services

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Role
import omsu.imit.schedule.model.User
import omsu.imit.schedule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService
@Autowired
constructor(private val userRepository: UserRepository) {

    fun changeUserRole(userId: Int, role: Role): User {
        val user = getUserById(userId)

        user.role = role
        userRepository.save(user)

        return user
    }

    fun disableAccount(userId: Int): User {
        val user = getUserById(userId)

        user.enabled = false
        userRepository.save(user)

        return user
    }

    fun deleteAccount(userId: Int) {
        if (!userRepository.existsById(userId))
            throw NotFoundException(ErrorCode.USER_WITH_ID_NOT_EXISTS, userId.toString())

        userRepository.deleteById(userId)
    }

    fun getUserById(id: Int): User {
        return userRepository.findById(id).orElseThrow {
            NotFoundException(ErrorCode.USER_WITH_ID_NOT_EXISTS, id.toString())
        }
    }

    fun getUserByEmail(email: String): User {
        return userRepository
                .findByEmail(email)
                .orElseThrow { NotFoundException(ErrorCode.USER_NOT_EXISTS, email) }
    }
}
