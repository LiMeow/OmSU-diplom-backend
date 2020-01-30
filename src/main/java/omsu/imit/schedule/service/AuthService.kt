package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.SignInRequest
import omsu.imit.schedule.dto.request.SignUpRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.PersonalData
import omsu.imit.schedule.repository.PersonalDataRepository
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
        if (personalDataRepository.findByEmail(request.email).isPresent)
            throw CommonValidationException(ErrorCode.USER_ALREADY_EXISTS, request.email)

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
                .orElseThrow { NotFoundException(ErrorCode.USER_NOT_EXISTS, request.email) }

        if (!passwordEncoder.matches(request.password, user.password))
            throw CommonValidationException(ErrorCode.WRONG_PASSWORD)

        return user
    }
}
