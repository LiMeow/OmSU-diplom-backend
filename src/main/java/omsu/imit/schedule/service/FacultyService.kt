package omsu.imit.schedule.service

import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Faculty
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.repository.FacultyRepository
import omsu.imit.schedule.requests.CreateFacultyRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class FacultyService
@Autowired constructor(private val buildingRepository: BuildingRepository,
                       private val facultyRepository: FacultyRepository) {
    fun createFaculty(request: CreateFacultyRequest): Faculty {
        val building: Building = buildingRepository
                .findById(request.buildingId)
                .orElseThrow { EntityNotFoundException(String.format("Building with id=%d not found", request.buildingId)) }

        val faculty = Faculty(building, request.name)
        facultyRepository.save(faculty)

        return faculty
    }

    fun getFaculty(facultyId: Int): Faculty {
        return facultyRepository.findById(facultyId)
                .orElseThrow { EntityNotFoundException(String.format("Faculty with id=%d not found", facultyId)) }
    }

    fun getAllFaculties(): List<Faculty> {
        return facultyRepository.findAll()
    }

    fun deleteFaculty(facultyId: Int) {
        facultyRepository.deleteById(facultyId)
    }
}
