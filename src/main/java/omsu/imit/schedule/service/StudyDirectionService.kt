package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.StudyDirection
import omsu.imit.schedule.repository.StudyDirectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudyDirectionService
@Autowired
constructor(private val studyDirectionRepository: StudyDirectionRepository) : BaseService() {

    fun getStudyDirectionById(studyDirectionId: Int): StudyDirection {
        return studyDirectionRepository.findById(studyDirectionId)
                .orElseThrow { NotFoundException(ErrorCode.STUDY_DIRECTION_NOT_EXISXTS, studyDirectionId.toString()) }
    }
}