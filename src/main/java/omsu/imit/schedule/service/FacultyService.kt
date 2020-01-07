package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateFacultyRequest
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Faculty
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.repository.FacultyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FacultyService
@Autowired constructor(private val buildingRepository: BuildingRepository,
                       private val facultyRepository: FacultyRepository) {
    fun createFaculty(request: CreateFacultyRequest): Faculty {
        val building: Building = buildingRepository
                .findById(request.buildingId)
                .orElseThrow { NotFoundException(ErrorCode.BUILDING_NOT_EXISTS, request.buildingId.toString()) }

        val faculty = Faculty(building, request.name)
        facultyRepository.save(faculty)

        return faculty
    }

    fun getFaculty(facultyId: Int): Faculty {
        return facultyRepository.findById(facultyId)
                .orElseThrow { NotFoundException(ErrorCode.FACULTY_NOT_EXISTS, facultyId.toString()) }
    }

    fun getAllFaculties(): List<Faculty> {
        return facultyRepository.findAll()
    }

    fun deleteFaculty(facultyId: Int) {
        if (!facultyRepository.existsById(facultyId))
            throw NotFoundException(ErrorCode.FACULTY_NOT_EXISTS, facultyId.toString())
        facultyRepository.deleteById(facultyId)
    }
}
