package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.Lecturer
import omsu.imit.schedule.model.PersonalData
import omsu.imit.schedule.model.UserRole
import omsu.imit.schedule.repository.ChairRepository
import omsu.imit.schedule.repository.FacultyRepository
import omsu.imit.schedule.repository.LecturerRepository
import omsu.imit.schedule.repository.PersonalDataRepository
import omsu.imit.schedule.requests.CreateLecturerRequest
import omsu.imit.schedule.response.LecturerInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class LecturerService
@Autowired
constructor(
        private val chairRepository: ChairRepository,
        private val facultyRepository: FacultyRepository,
        private val lecturerRepository: LecturerRepository,
        private val personalDataRepository: PersonalDataRepository) {

    fun createLecturer(request: CreateLecturerRequest): LecturerInfo {
        val chair = chairRepository.findById(request.charId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.CHAIR_NOT_EXISTS, request.charId.toString())

        val personalData = PersonalData(request.firstName, request.patronymic, request.lastName, request.email, UserRole.LECTURER)
        personalDataRepository.save(personalData)

        val lecturer = Lecturer(chair, personalData, false)
        lecturerRepository.save(lecturer)

        return createLecturerInfo(lecturer)
    }

    fun getLecturer(lectureId: Int): LecturerInfo {
        val lecturer = lecturerRepository.findById(lectureId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.LECTURER_NOT_EXISTS, lectureId.toString())
        return createLecturerInfo(lecturer)
    }

    fun getAllLecturers(): List<LecturerInfo> {
        val lecturers: MutableList<Lecturer> = lecturerRepository.findAll()
        val lecturersInfo = ArrayList<LecturerInfo>()

        for (lecturer in lecturers) {
            lecturersInfo.add(createLecturerInfo(lecturer))
        }
        return lecturersInfo
    }

    /*fun editLecturer(lectureId: Int, request: EditLecturerRequest): LecturerInfo {
        val lecturer = userRepository.findById(lectureId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.USER_NOT_EXISTS, lectureId.toString())

        if (!request.firstName.isNullOrEmpty()) lecturer.firstName = request.firstName!!
        if (!request.patronymic.isNullOrEmpty()) lecturer.patronymic = request.patronymic!!
        if (!request.lastName.isNullOrEmpty()) lecturer.lastName = request.lastName!!
        else throw ScheduleGeneratorException(ErrorCode.EMPTY_REQUEST_BODY)

        userRepository.save(lecturer)
        return createLecturerInfo(lecturer)
    }*/

    fun deleteLecturer(lectureId: Int) {
        TODO("Why lecturer deleted from personal data and not from lecturer repository??")
        if (!personalDataRepository.existsById(lectureId))
            throw ScheduleGeneratorException(ErrorCode.USER_NOT_EXISTS, lectureId.toString())

        personalDataRepository.deleteById(lectureId)
    }

    private fun createLecturerInfo(lecturer: Lecturer): LecturerInfo {
        return LecturerInfo(
                lecturer.id,
                lecturer.getFullName(),
                lecturer.chair.name,
                lecturer.enabled)
    }
}