package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import omsu.imit.schedule.repository.LecturerRepository
import omsu.imit.schedule.repository.UserRepository
import omsu.imit.schedule.service.ChairService
import omsu.imit.schedule.service.LecturerService
import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class LecturerServiceTests {
    @MockK
    lateinit var chairService: ChairService

    @MockK
    lateinit var lecturerRepository: LecturerRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var lecturerService: LecturerService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.lecturerService = LecturerService(
                this.chairService,
                this.lecturerRepository,
                this.userRepository)
    }
}