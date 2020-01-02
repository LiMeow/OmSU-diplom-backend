package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Faculty
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.repository.FacultyRepository
import omsu.imit.schedule.requests.CreateFacultyRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FacultyService
@Autowired constructor(private val buildingRepository: BuildingRepository,
                       private val facultyRepository: FacultyRepository) {
    fun createFaculty(request: CreateFacultyRequest): Any {
        val building: Building = buildingRepository.findById(request.buildingId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.BUILDING_NOT_EXISTS, request.buildingId.toString())

        if (facultyRepository.findByNameAndAddress(request.name, request.buildingId) != null)
            throw ScheduleGeneratorException(ErrorCode.FACULTY_ALREADY_EXISTS, request.name, building.address)

        val faculty = Faculty(building, request.name)
        facultyRepository.save(faculty)

        return faculty
    }

    fun getFaculty(facultyId: Int): Any {
        return facultyRepository.findById(facultyId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.FACULTY_NOT_EXISTS, facultyId.toString())
    }

    fun getAllFaculties(): Any {
        return facultyRepository.findAll()
    }

    fun deleteFaculty(facultyId: Int) {
        if (!facultyRepository.existsById(facultyId))
            throw ScheduleGeneratorException(ErrorCode.GROUP_NOT_EXISTS, facultyId.toString())

        facultyRepository.deleteById(facultyId)
    }
}
