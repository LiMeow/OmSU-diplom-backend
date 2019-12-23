package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.ActivityType
import omsu.imit.schedule.repository.ActivityTypeRepository
import omsu.imit.schedule.requests.CreateActivityTypeRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivityTypeService
@Autowired
constructor(private val activityTypeRepository: ActivityTypeRepository) {

    fun createActivityType(request: CreateActivityTypeRequest): ActivityType {
        if (activityTypeRepository.findByActivityType(request.type) != null)
            throw ScheduleGeneratorException(ErrorCode.ACTIVITY_TYPE_ALREADY_EXISTS, request.type)

        val activityType = ActivityType(request.type)
        activityTypeRepository.save(activityType)

        return activityType
    }

    fun getActivityTypeById(activityTypeId: Int): ActivityType? {
        return activityTypeRepository.findById(activityTypeId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.ACTIVITY_TYPE_NOT_EXISTS, activityTypeId.toString())
    }

    fun getAllActivityTypes(): Any {
        return activityTypeRepository.findAll()
    }

    fun deleteActivityType(activityTypeId: Int) {
        if (!activityTypeRepository.existsById(activityTypeId))
            throw ScheduleGeneratorException(ErrorCode.ACTIVITY_TYPE_NOT_EXISTS, activityTypeId.toString())

        activityTypeRepository.deleteById(activityTypeId)
    }

}