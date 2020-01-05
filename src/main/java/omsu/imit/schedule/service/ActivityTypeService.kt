package omsu.imit.schedule.service

import omsu.imit.schedule.model.ActivityType
import omsu.imit.schedule.repository.ActivityTypeRepository
import omsu.imit.schedule.requests.CreateActivityTypeRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

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
                .orElseThrow { EntityNotFoundException(String.format("Activity type  with id=%d not found", activityTypeId)) };
    }

    fun getAllActivityTypes(): Any {
        return activityTypeRepository.findAll()
    }

    fun deleteActivityType(activityTypeId: Int) {
        activityTypeRepository.deleteById(activityTypeId)
    }

}