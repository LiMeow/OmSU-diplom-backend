package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import omsu.imit.schedule.repository.GroupRepository
import omsu.imit.schedule.service.CourseService
import omsu.imit.schedule.service.GroupService
import omsu.imit.schedule.service.StudyDirectionService
import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GroupServiceTests {

    @MockK
    lateinit var courseService: CourseService

    @MockK
    lateinit var groupRepository: GroupRepository

    @MockK
    lateinit var studyDirectionService: StudyDirectionService

    private lateinit var groupService: GroupService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.groupService = GroupService(
                this.courseService,
                this.groupRepository,
                this.studyDirectionService)
    }
}