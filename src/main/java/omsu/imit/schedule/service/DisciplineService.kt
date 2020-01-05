package omsu.imit.schedule.service

import omsu.imit.schedule.model.Discipline
import omsu.imit.schedule.repository.DisciplineRepository
import omsu.imit.schedule.requests.DisciplineRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class DisciplineService
@Autowired
constructor(private val disciplineRepository: DisciplineRepository) {

    fun createDiscipline(request: DisciplineRequest): Discipline {
        val discipline = Discipline(request.name)
        disciplineRepository.save(discipline)
        return discipline

    }

    fun getDiscipline(disciplineId: Int): Discipline {
        return disciplineRepository.findById(disciplineId)
                .orElseThrow { EntityNotFoundException(String.format("Discipline with id=%d not found", disciplineId)) }
    }

    fun getAllDisciplines(): MutableList<Discipline> {
        return disciplineRepository.findAll()
    }

    fun editDiscipline(disciplineId: Int, request: DisciplineRequest): Discipline {
        val discipline = getDiscipline(disciplineId)

        discipline.name = request.name
        disciplineRepository.save(discipline)
        return discipline
    }

    fun deleteDiscipline(disciplineId: Int) {
        disciplineRepository.deleteById(disciplineId)
    }

}