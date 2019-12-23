package omsu.imit.schedule.servicetests

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.Discipline
import omsu.imit.schedule.repository.DisciplineRepository
import omsu.imit.schedule.requests.DisciplineRequest
import omsu.imit.schedule.service.DisciplineService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DisciplineServiceTests {

    @Mock
    lateinit var disciplineRepository: DisciplineRepository

    private lateinit var disciplineService: DisciplineService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.disciplineService = DisciplineService(
                this.disciplineRepository)
    }

    @Test
    fun testCreateDiscipline() {
        val request = DisciplineRequest("discipline")
        val discipline = Discipline(0, request.name)

        `when`(disciplineRepository.findByDisciplineName(discipline.name)).thenReturn(null)
        `when`(disciplineRepository.save(discipline)).thenReturn(discipline)

        assertEquals(discipline, disciplineService.createDiscipline(request))

        verify(disciplineRepository).findByDisciplineName(discipline.name)
        verify(disciplineRepository).save(discipline)
    }

    @Test
    fun testCreateAlreadyExistingDiscipline() {
        val request = DisciplineRequest("discipline")
        val discipline = Discipline(1, request.name)

        `when`(disciplineRepository.findByDisciplineName(discipline.name)).thenReturn(discipline)
        try {
            disciplineService.createDiscipline(request)
        } catch (ex: ScheduleGeneratorException) {
            assertEquals(ErrorCode.DISCIPLINE_ALREADY_EXISTS, ex.errorCode);
        }
        verify(disciplineRepository).findByDisciplineName(discipline.name)
    }

    @Test
    fun testGetDisciplineById() {
        val discipline = Discipline(1, "discipline")

        `when`(disciplineRepository.findById(discipline.id)).thenReturn(Optional.of(discipline))
        assertEquals(discipline, disciplineService.getDiscipline(discipline.id))
        verify(disciplineRepository).findById(discipline.id)
    }

    @Test
    fun testGetNonExistingDisciplineById() {

        `when`(disciplineRepository.findById(1)).thenReturn(Optional.empty())
        try {
            disciplineService.getDiscipline(1)
        } catch (ex: ScheduleGeneratorException) {
            assertEquals(ErrorCode.DISCIPLINE_NOT_EXISTS, ex.errorCode);
        }
        verify(disciplineRepository).findById(1)
    }

    @Test
    fun testGetAllDisciplines() {
        val discipline1 = Discipline(1, "discipline1")
        val discipline2 = Discipline(2, "discipline2")
        val discipline3 = Discipline(3, "discipline3")
        val response = listOf(discipline1, discipline2, discipline3)

        `when`(disciplineRepository.findAll()).thenReturn(response)
        assertEquals(response, disciplineService.getAllDisciplines())
        verify(disciplineRepository).findAll()
    }

    @Test
    fun testEditDiscipline() {
        val request = DisciplineRequest("updatedDiscipline")
        val discipline = Discipline(1, "discipline")
        val updatedDiscipline = Discipline(1, "updatedDiscipline")

        `when`(disciplineRepository.findById(discipline.id)).thenReturn(Optional.of(discipline))
        `when`(disciplineRepository.save(updatedDiscipline)).thenReturn(updatedDiscipline)

        assertEquals(discipline, disciplineService.editDiscipline(discipline.id, request))

        verify(disciplineRepository).findById(discipline.id)
        verify(disciplineRepository).save(updatedDiscipline)
    }

    @Test
    fun testEditNonExistingDiscipline() {
        val request = DisciplineRequest("updatedDiscipline")

        `when`(disciplineRepository.findById(1)).thenReturn(Optional.empty())

        try {
            disciplineService.editDiscipline(1, request)
        } catch (ex: ScheduleGeneratorException) {
            assertEquals(ErrorCode.DISCIPLINE_NOT_EXISTS, ex.errorCode);
        }

        verify(disciplineRepository).findById(1)
    }

    @Test
    fun testDeleteDiscipline() {
        val discipline = Discipline(1, "discipline")

        `when`(disciplineRepository.existsById(discipline.id)).thenReturn(true)
        disciplineService.deleteDiscipline(discipline.id)

        verify(disciplineRepository).existsById(discipline.id)
        verify(disciplineRepository).deleteById(discipline.id)
    }

    @Test
    fun testDeleteNonExistingDiscipline() {

        `when`(disciplineRepository.existsById(1)).thenReturn(false)
        try {
            disciplineService.deleteDiscipline(1)
        } catch (ex: ScheduleGeneratorException) {
            assertEquals(ErrorCode.DISCIPLINE_NOT_EXISTS, ex.errorCode);
        }
        verify(disciplineRepository).existsById(1)
    }

}