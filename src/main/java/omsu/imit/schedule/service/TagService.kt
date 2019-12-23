package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.Tag
import omsu.imit.schedule.repository.TagRepository
import omsu.imit.schedule.requests.CreateTagRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class TagService
@Autowired
constructor(private val tagRepository: TagRepository) {

    fun addTag(request: CreateTagRequest): Tag {
        if (tagRepository.findByTag(request.tag) != null)
            throw ScheduleGeneratorException(ErrorCode.TAG_ALREADY_EXISTS, request.tag)

        val tag = Tag(request.tag)
        tagRepository.save(tag)

        return tag
    }

    fun getTagById(tagId: Int): Tag {
        val tag: Tag? = tagRepository.findById(tagId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.TAG_NOT_EXISTS, tagId.toString())
        return tag!!
    }

    fun getAllTags(): MutableList<Tag> {
        return tagRepository.findAll()
    }

    fun deleteTagById(tagId: Int) {
        if (!tagRepository.existsById(tagId))
            throw ScheduleGeneratorException(ErrorCode.TAG_NOT_EXISTS, tagId.toString())

        tagRepository.deleteById(tagId)
    }
}
