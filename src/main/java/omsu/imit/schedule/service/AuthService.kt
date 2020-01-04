package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.PersonalData
import omsu.imit.schedule.model.UserRole
import omsu.imit.schedule.repository.PersonalDataRepository
import omsu.imit.schedule.requests.SignInRequest
import omsu.imit.schedule.requests.SignUpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService
@Autowired
constructor(
        private val passwordEncoder: PasswordEncoder,
        private val personalDataRepository: PersonalDataRepository) {

    fun signUp(request: SignUpRequest): PersonalData {
        if (personalDataRepository.findByEmail(request.email) != null)
            throw ScheduleGeneratorException(ErrorCode.USER_ALREADY_EXISTS, request.email)

        val personalData = PersonalData(
                request.firstName,
                request.patronymic, request.lastName,
                request.email,
                passwordEncoder.encode(request.password),
                request.userRole)
        personalDataRepository.save(personalData)
        return personalData
    }


    fun signIn(request: SignInRequest): PersonalData {
        val user = personalDataRepository.findByEmail(request.email)
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
