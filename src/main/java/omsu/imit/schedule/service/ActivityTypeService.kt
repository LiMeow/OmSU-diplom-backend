package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateActivityTypeRequest
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.ActivityType
import omsu.imit.schedule.repository.ActivityTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivityTypeService
@Autowired
constructor(private val activityTypeRepository: ActivityTypeRepository) {

    fun createActivityType(request: CreateActivityTypeRequest): ActivityType {
        val activityType = ActivityType(request.type)
        activityTypeRepository.save(activityType)

        return activityType
    }

    fun getActivityTypeById(activityTypeId: Int): ActivityType? {
        return activityTypeRepository
                .findById(activityTypeId)
                .orElseThrow {
                    NotFoundException(ErrorCode.ACTIVITY_TYPE_NOT_EXISTS, activityTypeId.toString())
                }
    }

    fun getAllActivityTypes(): Any {
        return activityTypeRepository.findAll()
    }

    fun deleteActivityType(activityTypeId: Int) {
        if (!activityTypeRepository.existsById(activityTypeId))
            throw NotFoundException(ErrorCode.ACTIVITY_TYPE_NOT_EXISTS, activityTypeId.toString())
        activityTypeRepository.deleteById(activityTypeId)
    }

}