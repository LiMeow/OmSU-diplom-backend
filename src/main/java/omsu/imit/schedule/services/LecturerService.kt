package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CreateLecturerRequest
import omsu.imit.schedule.dto.response.LecturerInfo
import omsu.imit.schedule.dto.response.LecturerShortInfo
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Lecturer
import omsu.imit.schedule.model.Role
import omsu.imit.schedule.model.User
import omsu.imit.schedule.repository.LecturerRepository
import omsu.imit.schedule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LecturerService
@Autowired
constructor(
        private val chairService: ChairService,
        private val lecturerRepository: LecturerRepository,
        private val userRepository: UserRepository) : BaseService() {

    fun createLecturer(request: CreateLecturerRequest): LecturerInfo {
        val chair = chairService.getChairById(request.charId)

        val personalData = User(
                request.firstName,
                request.patronymic,
                request.lastName,
                request.email,
                Role.ROLE_LECTURER)
        userRepository.save(personalData)

        val lecturer = Lecturer(chair, personalData)
        lecturerRepository.save(lecturer)

        return toLecturerInfo(lecturer)
    }

    fun getLecturer(lectureId: Int): Lecturer {
        return lecturerRepository.findById(lectureId)
                .orElseThrow { NotFoundException(ErrorCode.LECTURER_NOT_EXISTS, lectureId.toString()) }
    }

    fun getAllLecturers(): List<LecturerInfo> {
        return lecturerRepository
                .findAll()
                .asSequence()
                .map { toLecturerInfo(it) }
                .toList();
    }

    fun getLecturersByChair(chairId: Int): List<LecturerShortInfo> {
        return lecturerRepository
                .findAllByChairId(chairId)
                .asSequence()
                .map { toLecturerShortInfo(it) }
                .toList();
    }

    fun deleteLecturer(lectureId: Int) {
        if (!lecturerRepository.existsById(lectureId))
            throw NotFoundException(ErrorCode.LECTURER_NOT_EXISTS, lectureId.toString())

        lecturerRepository.deleteById(lectureId)
    }

    fun getLecturerInfo(lectureId: Int): LecturerInfo {
        return toLecturerInfo(getLecturer(lectureId))
    }
}