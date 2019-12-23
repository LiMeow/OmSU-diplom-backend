package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.PersonalData
import omsu.imit.schedule.model.User
import omsu.imit.schedule.model.UserRole
import omsu.imit.schedule.repository.PersonalDataRepository
import omsu.imit.schedule.repository.UserRepository
import omsu.imit.schedule.requests.SignInRequest
import omsu.imit.schedule.requests.SignUpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService
@Autowired
constructor(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val personalDataRepository: PersonalDataRepository) {

    fun signUp(request: SignUpRequest): User {
        val user: User;
        if (userRepository.findByEmail(request.email) != null)
            throw ScheduleGeneratorException(ErrorCode.USER_ALREADY_EXISTS, request.email)

        if (!isAdminOrDispatcher(request.userRole) && validateName(request)) {
            user = User(request.email, passwordEncoder.encode(request.password), request.userRole)
            userRepository.save(user)

            val personalData = PersonalData(user, request.firstName, request.patronymic, request.lastName)
            personalDataRepository.save(personalData)
        } else {
            user = User(request.email, passwordEncoder.encode(request.password), request.userRole)
            userRepository.save(user)
        }
        return user
    }


    fun signIn(request: SignInRequest): User {
        val user = userRepository.findByEmail(request.email)
                ?: throw ScheduleGeneratorException(ErrorCode.USER_NOT_EXISTS, request.email)

        if (!passwordEncoder.matches(request.password, user.password))
            throw ScheduleGeneratorException(ErrorCode.WRONG_PASSWORD)

        return user
    }

    private fun isAdminOrDispatcher(userRole: UserRole): Boolean {
        return userRole == UserRole.DISPATCHER || userRole == UserRole.ADMIN
    }

    private fun validateName(request: SignUpRequest): Boolean {
        return request.firstName.isNotEmpty() && request.lastName.isNotEmpty()
    }
}
