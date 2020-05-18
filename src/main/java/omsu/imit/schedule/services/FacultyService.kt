package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CreateFacultyRequest
import omsu.imit.schedule.dto.response.FacultyInfo
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Faculty
import omsu.imit.schedule.repository.FacultyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FacultyService
@Autowired constructor(private val buildingService: BuildingService,
                       private val facultyRepository: FacultyRepository) : BaseService() {

    fun createFaculty(request: CreateFacultyRequest): FacultyInfo {
        val building: Building = buildingService.getBuildingById(request.buildingId)
        val faculty = Faculty(building, request.name)

        facultyRepository.save(faculty)

        return toFacultyInfo(faculty)
    }

    fun getFacultyById(facultyId: Int): Faculty {
        return facultyRepository.findById(facultyId)
                .orElseThrow { NotFoundException(ErrorCode.FACULTY_NOT_EXISTS, facultyId.toString()) }
    }

    fun getFacultyInfo(facultyId: Int): FacultyInfo {
        return toFacultyInfo(getFacultyById(facultyId))
    }

    fun getAllFaculties(): List<FacultyInfo> {
        return facultyRepository.findAll().asSequence().map { toFacultyInfo(it) }.toList()
    }

    fun deleteFaculty(facultyId: Int) {
        if (!facultyRepository.existsById(facultyId))
            throw NotFoundException(ErrorCode.FACULTY_NOT_EXISTS, facultyId.toString())
        facultyRepository.deleteById(facultyId)
    }

}
