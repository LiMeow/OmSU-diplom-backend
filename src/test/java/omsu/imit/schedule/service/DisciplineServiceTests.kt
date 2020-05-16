package omsu.imit.schedule.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateDisciplineRequest
import omsu.imit.schedule.dto.request.EditDisciplineRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Discipline
import omsu.imit.schedule.repository.DisciplineRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class DisciplineServiceTests {

    @MockK
    lateinit var disciplineRepository: DisciplineRepository

    @MockK
    lateinit var tagService: TagService

    private lateinit var disciplineService: DisciplineService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.disciplineService = DisciplineService(this.disciplineRepository, this.tagService)
    }

    @Test
    fun testCreateDiscipline() {
        val request = CreateDisciplineRequest("discipline")
        val discipline = Discipline(0, request.name)

        every { disciplineRepository.findByDisciplineName(discipline.name) } returns (null)
        every { disciplineRepository.save(discipline) } returns discipline

        assertEquals(discipline, disciplineService.createDiscipline(request))

        verify { disciplineRepository.findByDisciplineName(discipline.name) }
        verify { disciplineRepository.save(discipline) }
    }

    @Test
    fun testCreateAlreadyExistingDiscipline() {
        val request = CreateDisciplineRequest("discipline")
        val discipline = Discipline(1, request.name)

        every { disciplineRepository.findByDisciplineName(discipline.name) } returns discipline

        assertThrows(CommonValidationException::class.java) { disciplineService.createDiscipline(request) }
        verify { disciplineRepository.findByDisciplineName(discipline.name) }
    }

    @Test
    fun testGetDisciplineById() {
        val discipline = Discipline(1, "discipline")

        every { disciplineRepository.findById(discipline.id) } returns Optional.of(discipline)

        assertEquals(discipline, disciplineService.getDisciplineById(discipline.id))
        verify { disciplineRepository.findById(discipline.id) }
    }

    @Test
    fun testGetNonExistingDisciplineById() {

        every { disciplineRepository.findById(1) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { disciplineService.getDisciplineById(1) }
        verify { disciplineRepository.findById(1) }
    }

    @Test
    fun testGetAllDisciplines() {
        val discipline1 = Discipline(1, "discipline1")
        val discipline2 = Discipline(2, "discipline2")
        val discipline3 = Discipline(3, "discipline3")
        val response = listOf(discipline1, discipline2, discipline3)

        every { disciplineRepository.findAll() } returns response

        assertEquals(response, disciplineService.getAllDisciplines())
        verify { disciplineRepository.findAll() }
    }

    @Test
    fun testEditDiscipline() {
        val request = EditDisciplineRequest("updatedDiscipline")
        val discipline = Discipline(1, "discipline")
        val updatedDiscipline = Discipline(1, "updatedDiscipline")

        every { disciplineRepository.findById(discipline.id) } returns Optional.of(discipline)
        every { disciplineRepository.save(updatedDiscipline) } returns updatedDiscipline

        assertEquals(discipline, disciplineService.editDiscipline(discipline.id, request))

        verify { disciplineRepository.findById(discipline.id) }
        verify { disciplineRepository.save(updatedDiscipline) }
    }

    @Test
    fun testEditNonExistingDiscipline() {
        val request = EditDisciplineRequest("updatedDiscipline")

        every { disciplineRepository.findById(1) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { disciplineService.editDiscipline(1, request) }
        verify { disciplineRepository.findById(1) }
    }

    @Test
    fun testDeleteDiscipline() {
        val discipline = Discipline(1, "discipline")

        every { disciplineRepository.existsById(discipline.id) } returns true
        every { disciplineRepository.deleteById(discipline.id) } returns mockk()

        disciplineService.deleteDiscipline(discipline.id)

        verify { disciplineRepository.existsById(discipline.id) }
        verify { disciplineRepository.deleteById(discipline.id) }
    }

    @Test
    fun testDeleteNonExistingDiscipline() {

        every { disciplineRepository.existsById(1) } returns false

        assertThrows(NotFoundException::class.java) { disciplineService.deleteDiscipline(1) }
        verify { disciplineRepository.existsById(1) }
    }

}