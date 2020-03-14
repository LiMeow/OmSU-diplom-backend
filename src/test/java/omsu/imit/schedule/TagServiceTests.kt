package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateTagRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Tag
import omsu.imit.schedule.repository.TagRepository
import omsu.imit.schedule.service.TagService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class TagServiceTests : BaseTests() {
    @MockK
    lateinit var tagRepository: TagRepository

    private lateinit var tagService: TagService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.tagService = TagService(this.tagRepository)
    }

    @Test
    fun testCreateTag() {
        val tag = getTag()
        val request = CreateTagRequest(tag.tag)

        every { tagRepository.save(tag) } returns (tag)

        assertEquals(tag, tagService.createTag(request))
        verify { tagRepository.save(tag) }
    }

    @Test
    fun testCreateAlreadyExistingTag() {
        val tag = getTag()
        val request = CreateTagRequest(tag.tag)

        every { tagRepository.save(tag) } throws DataIntegrityViolationException("")

        assertThrows(CommonValidationException::class.java) { tagService.createTag(request) }
        verify { tagRepository.save(tag) }
    }

    @Test
    fun testGetTagById() {
        val tag = getTag()

        every { tagRepository.findById(tag.id) } returns Optional.of(tag)

        assertEquals(tag, tagService.getTagById(tag.id))
        verify { tagRepository.findById(tag.id) }
    }

    @Test
    fun testGetNonExistingTagById() {
        val id = 1;

        every { tagRepository.findById(id) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { tagService.getTagById(id) }
        verify { tagRepository.findById(id) }
    }

    @Test
    fun testGetAllTags() {
        val tag1 = Tag("tag1")
        val tag2 = Tag("tag2")
        val tags = listOf(tag1, tag2)

        every { tagRepository.findAll() } returns tags

        assertEquals(tags, tagService.getAllTags())
        verify { tagRepository.findAll() }
    }

    @Test
    fun testGetAllTagsByIds() {
        val tag1 = Tag(1, "tag1")
        val tag2 = Tag(2, "tag2")
        val tags = listOf(tag1, tag2)
        val tagsIds = listOf(tag1.id, tag2.id)

        every { tagRepository.findAllById(tagsIds) } returns tags

        assertEquals(tags, tagService.getAllTagsByIds(tagsIds))
        verify { tagRepository.findAllById(tagsIds) }
    }

    @Test
    fun testDeleteTagById() {
        val id = 1;

        every { tagRepository.existsById(id) } returns true
        every { tagRepository.deleteById(id) } returns mockk()

        tagService.deleteTagById(id)

        verify { tagRepository.existsById(id) }
        verify { tagRepository.deleteById(id) }
    }

    @Test
    fun testDeleteNonExistingTag() {
        val id = 1;
        every { tagRepository.existsById(id) } returns false

        assertThrows(NotFoundException::class.java) { tagService.deleteTagById(id) }
        verify { tagRepository.existsById(id) }
    }
}