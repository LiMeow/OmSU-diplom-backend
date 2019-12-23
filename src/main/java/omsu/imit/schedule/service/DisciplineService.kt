package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.Discipline
import omsu.imit.schedule.repository.DisciplineRepository
import omsu.imit.schedule.requests.DisciplineRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DisciplineService
@Autowired
constructor(private val disciplineRepository: DisciplineRepository) {

    fun createDiscipline(request: DisciplineRequest): Discipline {
        val discipline = Discipline(request.name)

        if (disciplineRepository.findByDisciplineName(discipline.name) != null)
            throw ScheduleGeneratorException(ErrorCode.DISCIPLINE_ALREADY_EXISTS, request.name)

        disciplineRepository.save(discipline)
        return discipline

    }

    fun getDiscipline(disciplineId: Int): Discipline? {
        return disciplineRepository.findById(disciplineId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.DISCIPLINE_NOT_EXISTS, disciplineId.toString())
    }

    fun getAllDisciplines(): MutableList<Discipline> {
        return disciplineRepository.findAll()
    }

    fun editDiscipline(disciplineId: Int, request: DisciplineRequest): Discipline {
        val discipline = disciplineRepository.findById(disciplineId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.DISCIPLINE_NOT_EXISTS, disciplineId.toString())

        discipline.name = request.name
        disciplineRepository.save(discipline)
        return discipline
    }

    fun deleteDiscipline(disciplineId: Int) {
        if (!disciplineRepository.existsById(disciplineId))
            throw ScheduleGeneratorException(ErrorCode.DISCIPLINE_NOT_EXISTS, disciplineId.toString())

        disciplineRepository.deleteById(disciplineId)
    }

}