package omsu.imit.schedule.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.repository.StudyDirectionRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class StudyDirectionServiceTests : BaseTests() {

    @MockK
    lateinit var studyDirectionRepository: StudyDirectionRepository

    private lateinit var studyDirectionService: StudyDirectionService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.studyDirectionService = StudyDirectionService(this.studyDirectionRepository)
    }

    @Test
    fun testGetStudyDirectionById() {
        val studyDirection = getStudyDirection()

        every { studyDirectionRepository.findById(studyDirection.id) } returns Optional.of(studyDirection)

        assertEquals(studyDirection, studyDirectionService.getStudyDirectionById(studyDirection.id))
        verify { studyDirectionRepository.findById(studyDirection.id) }
    }

    @Test
    fun testGetStudyDirectionByNonExistingId() {
        val id = 1;

        every { studyDirectionRepository.findById(id) } returns Optional.empty()

        Assertions.assertThrows(NotFoundException::class.java) { studyDirectionService.getStudyDirectionById(id) }
        verify { studyDirectionRepository.findById(id) }
    }
}