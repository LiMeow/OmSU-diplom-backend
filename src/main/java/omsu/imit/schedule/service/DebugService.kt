package omsu.imit.schedule.service

import omsu.imit.schedule.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DebugService
@Autowired
constructor(private val buildingRepository: BuildingRepository,
            private val disciplineRepository: DisciplineRepository,
            private val groupRepository: GroupRepository,
            private val tagRepository: TagRepository,
            private val timeBlockRepository: TimeBlockRepository,
            private val activityTypeRepository: ActivityTypeRepository) {

    fun clear() {
        timeBlockRepository.deleteAll()
        groupRepository.deleteAll()
        buildingRepository.deleteAll()
        tagRepository.deleteAll()
        disciplineRepository.deleteAll()
        activityTypeRepository.deleteAll()
    }
}
