package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CreateTagRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Tag
import omsu.imit.schedule.repository.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
open class TagService
@Autowired
constructor(private val tagRepository: TagRepository) {

    fun createTag(request: CreateTagRequest): Tag {
        val tag = Tag(request.tag.toUpperCase())

        try {
            tagRepository.save(tag)
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.TAG_ALREADY_EXISTS, request.tag)
        }
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

    fun getAllTagsByIds(tagsIds: List<Int>): MutableList<Tag> {
        return tagRepository.findAllById(tagsIds)
    }

    fun deleteTagById(tagId: Int) {
        if (!tagRepository.existsById(tagId))
            throw NotFoundException(ErrorCode.TAG_NOT_EXISTS, tagId.toString())

        tagRepository.deleteById(tagId)
    }
}
