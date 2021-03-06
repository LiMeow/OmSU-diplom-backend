package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CreateTimeBlockRequest
import omsu.imit.schedule.dto.request.EditTimeBlockRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.TimeBlock
import omsu.imit.schedule.repository.TimeBlockRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class TimeBlockService
@Autowired
constructor(private val timeBlockRepository: TimeBlockRepository) {

    fun createTimeBlock(request: CreateTimeBlockRequest): TimeBlock {

        val timeBlock = TimeBlock(request.timeFrom, request.timeTo)
        try {
            timeBlockRepository.save(timeBlock)
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.TIMEBLOCK_ALREADY_EXISTS, request.timeFrom, request.timeTo)
        }
        return timeBlock
    }

    fun getTimeBlockById(timeBlockId: Int): TimeBlock {
        return timeBlockRepository
                .findById(timeBlockId)
                .orElseThrow { NotFoundException(ErrorCode.TIMEBLOCK_NOT_EXISTS, timeBlockId.toString()) }
    }

    fun getAllTimeBlocks(): MutableList<TimeBlock> {
        return timeBlockRepository.findAll()
    }

    fun editTimeBlock(timeBlockId: Int, request: EditTimeBlockRequest): TimeBlock {
        val timeBlock = getTimeBlockById(timeBlockId)

        if (request.timeFrom != null) timeBlock.timeFrom = request.timeFrom!!
        if (request.timeTo != null) timeBlock.timeTo = request.timeTo!!

        timeBlockRepository.save(timeBlock)
        return timeBlock
    }

    fun deleteTimeBlock(timeBlockId: Int) {
        if (!timeBlockRepository.existsById(timeBlockId))
            throw NotFoundException(ErrorCode.TIMEBLOCK_NOT_EXISTS, timeBlockId.toString())
        timeBlockRepository.deleteById(timeBlockId)
    }

    fun deleteAllTimeBlocks() {
        timeBlockRepository.deleteAll()
    }
}
