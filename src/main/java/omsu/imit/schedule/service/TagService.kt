package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateTagRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Tag
import omsu.imit.schedule.repository.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class TagService
@Autowired
constructor(private val tagRepository: TagRepository) {

    fun addTag(request: CreateTagRequest): Tag {
        if (tagRepository.findByTag(request.tag) != null)
            throw CommonValidationException(ErrorCode.TAG_ALREADY_EXISTS, request.tag)

        val tag = Tag(request.tag)
        tagRepository.save(tag)

        return tag
    }

    fun getTagById(tagId: Int): Tag {
        val tag: Tag? = tagRepository.findById(tagId)
                .orElseThrow { NotFoundException(ErrorCode.TAG_NOT_EXISTS, tagId.toString()) }
        return tag!!
    }

    fun getAllTags(): MutableList<Tag> {
        return tagRepository.findAll()
    }

    fun deleteTagById(tagId: Int) {
        if (!tagRepository.existsById(tagId))
            throw NotFoundException(ErrorCode.TAG_NOT_EXISTS, tagId.toString())

        tagRepository.deleteById(tagId)
    }
}
