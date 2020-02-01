package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.ChangeUserTypeRequest
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.User
import omsu.imit.schedule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService
@Autowired
constructor(private val userRepository: UserRepository) {

    fun whoIAm(): User {
        val email = SecurityContextHolder.getContext().authentication.name
        return getUserByEmail(email)
    }

    fun changeUserType(userId: Int, request: ChangeUserTypeRequest): User {
        val user = getUserById(userId)

        user.userRole = request.userRole
        userRepository.save(user)

        return user
    }

    fun getUserById(id: Int): User {
        return userRepository.findById(id).orElseThrow {
            NotFoundException(ErrorCode.USER_NOT_EXISTS_BY_ID, id.toString())
        }
    }

    fun getUserByEmail(email: String): User {
        return userRepository
                .findByEmail(email)
                .orElseThrow { NotFoundException(ErrorCode.USER_NOT_EXISTS, email) }
    }
}
