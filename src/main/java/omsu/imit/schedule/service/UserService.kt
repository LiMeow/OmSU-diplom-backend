package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.PersonalData
import omsu.imit.schedule.repository.PersonalDataRepository
import omsu.imit.schedule.requests.ChangeUserTypeRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService
@Autowired
constructor(private val personalDataRepository: PersonalDataRepository) {

    fun whoIAm(): PersonalData {
        val email = SecurityContextHolder.getContext().authentication.name
        return personalDataRepository.findByEmail(email) ?: throw ScheduleGeneratorException(ErrorCode.USER_NOT_EXISTS)
    }

    fun changeUserType(userId: Int, request: ChangeUserTypeRequest): PersonalData {
        val user = personalDataRepository.findById(userId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.USER_NOT_EXISTS)

        user.userRole = request.userRole
        personalDataRepository.save(user)

        return user
    }
}
