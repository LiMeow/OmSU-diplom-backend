package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateDisciplineRequest
import omsu.imit.schedule.dto.request.EditDisciplineRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Discipline
import omsu.imit.schedule.repository.DisciplineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class DisciplineService
@Autowired
constructor(private val disciplineRepository: DisciplineRepository,
            private val tagService: TagService) {

    fun createDiscipline(request: CreateDisciplineRequest): Discipline {
        val discipline = Discipline(request.name)

        if (!request.requirements.isNullOrEmpty())
            discipline.requirements = tagService.getAllTagsByIds(request.requirements!!)

        try {
            disciplineRepository.save(discipline)
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.DISCIPLINE_ALREADY_EXISTS, request.name)
        }
        return discipline

    }

    fun getDisciplineById(id: Int): Discipline {
        return disciplineRepository.findById(id)
                .orElseThrow { NotFoundException(ErrorCode.DISCIPLINE_NOT_EXISTS, id.toString()) }
    }

    fun getAllDisciplines(): MutableList<Discipline> {
        return disciplineRepository.findAll()
    }

    fun editDiscipline(id: Int, request: EditDisciplineRequest): Discipline {
        val discipline = getDisciplineById(id)

        if (!request.name.isNullOrEmpty())
            discipline.name = request.name!!

        if (!request.requirements.isNullOrEmpty())
            discipline.requirements = tagService.getAllTagsByIds(request.requirements!!)

        disciplineRepository.save(discipline)
        return discipline
    }

    fun deleteDiscipline(id: Int) {
        if (!disciplineRepository.existsById(id))
            throw NotFoundException(ErrorCode.DISCIPLINE_NOT_EXISTS, id.toString());
        disciplineRepository.deleteById(id)
    }

}