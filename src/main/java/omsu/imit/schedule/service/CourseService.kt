package omsu.imit.schedule.service

import omsu.imit.schedule.dto.response.CourseInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Course
import omsu.imit.schedule.repository.CourseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class CourseService
@Autowired
constructor(private val courseRepository: CourseRepository,
            private val facultyService: FacultyService) : BaseService() {

    fun createCourse(facultyId: Int, startYear: String, finishYear: String): CourseInfo {
        val faculty = facultyService.getFacultyById(facultyId)
        val course = Course(faculty, startYear, finishYear)

        try {
            courseRepository.save(course)
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.COURSE_ALREADY_EXISTS, startYear, facultyId.toString())
        }
        return toCourseInfo(course)
    }

    fun getCourseById(courseId: Int): Course {
        return courseRepository.findById(courseId)
                .orElseThrow { NotFoundException(ErrorCode.COURSE_NOT_EXISTS, courseId.toString()) }
    }

    fun getCourseInfo(courseId: Int): CourseInfo {
        return toCourseInfo(getCourseById(courseId))
    }

    fun toCourseInfo(course: Course): CourseInfo {
        return CourseInfo(course.id,
                course.faculty.name,
                course.startYear,
                course.finishYear,
                course.groups?.asSequence()?.map { toGroupInfo(it) }?.toList())
    }
}