package omsu.imit.schedule

import omsu.imit.schedule.repository.*
import omsu.imit.schedule.service.AuditoryOccupationService
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuditoryOccupationServiceTests {
    @Mock
    lateinit var auditoryRepository: AuditoryRepository
    @Mock
    lateinit var auditoryOccupationRepository: AuditoryOccupationRepository
    @Mock
    lateinit var groupRepository: GroupRepository
    @Mock
    lateinit var lecturerRepository: LecturerRepository
    @Mock
    lateinit var timeBlockRepository: TimeBlockRepository

    private lateinit var auditoryOccupationService: AuditoryOccupationService

//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        this.auditoryOccupationService = AuditoryOccupationService(
//                this.auditoryRepository,
//                this.auditoryOccupationRepository,
//                this.groupRepository,
//                this.lecturerRepository,
//                this.timeBlockRepository)
//    }

//    @Test
//    fun testOccupyAuditory() {
//        val auditory = getAuditory()
//        val lecturer = getLecturer()
//        val group = getGroup()
//        val timeBlock = getTimeBlock()
//        val auditoryOccupation = getAuditoryOccupation()
//
//        val request = OccupyAuditoryRequest(timeBlock.timeFrom, timeBlock.timeTo, "19.05.2019", lecturer.id, group.id, "comment")
//        val response = OccupationInfo(
//                auditoryOccupation.id,
//                request.timeFrom,
//                request.timeTo,
//                request.date,
//                auditoryOccupation.lecturer!!.getFullName(),
//                group,
//                request.comment)
//
//        `when`(groupRepository.findById(group.id)).thenReturn(Optional.of(group))
//        `when`(lecturerRepository.findById(lecturer.id)).thenReturn(Optional.of(lecturer))
//        `when`(timeBlockRepository.findByTime(request.timeFrom, request.timeTo)).thenReturn(timeBlock)
//        `when`(auditoryRepository.findById(auditory.id)).thenReturn(Optional.of(auditory))
//        `when`(auditoryOccupationRepository.save(auditoryOccupation)).thenReturn(auditoryOccupation)
//
//        assertEquals(response, auditoryOccupationService.occupyAuditory(auditory.id, request))
//
//        verify(groupRepository).findById(group.id)
//        verify(lecturerRepository).findById(lecturer.id)
//        verify(timeBlockRepository).findByTime(request.timeFrom, request.timeTo)
//        verify(auditoryRepository).findById(auditory.id)
//        verify(auditoryOccupationRepository).save(auditoryOccupation)
//    }
//
//    @Test
//    fun testOccupyAuditoryToNonExistingGroup() {
//        val auditory = getAuditory()
//        val lecturer = getLecturer()
//        val timeBlock = getTimeBlock()
//        val request = OccupyAuditoryRequest(timeBlock.timeFrom, timeBlock.timeTo, "19.05.2019", lecturer.id, 2, "comment")
//
//        `when`(groupRepository.findById(request.groupId!!)).thenReturn(Optional.empty())
//
//        try {
//            auditoryOccupationService.occupyAuditory(auditory.id, request)
//        } catch (ex: ScheduleGeneratorException) {
//            Assert.assertEquals(ErrorCode.GROUP_NOT_EXISTS, ex.errorCode);
//        }
//
//        verify(groupRepository).findById(request.groupId!!)
//    }
//
//    @Test
//    fun testOccupyAuditoryByNonExistingLecturer() {
//        val auditory = getAuditory()
//        val group = getGroup()
//        val timeBlock = getTimeBlock()
//
//        val request = OccupyAuditoryRequest(timeBlock.timeFrom, timeBlock.timeTo, "19.05.2019", 2, group.id, "comment")
//
//        `when`(groupRepository.findById(group.id)).thenReturn(Optional.of(group))
//        `when`(lecturerRepository.findById(request.lecturerId!!)).thenReturn(Optional.empty())
//
//        try {
//            auditoryOccupationService.occupyAuditory(auditory.id, request)
//        } catch (ex: ScheduleGeneratorException) {
//            Assert.assertEquals(ErrorCode.LECTURER_NOT_EXISTS, ex.errorCode);
//        }
//
//        verify(groupRepository).findById(group.id)
//        verify(lecturerRepository).findById(request.lecturerId!!)
//    }
//
//    @Test
//    fun testOccupyAuditoryToNonExistingTimeBlock() {
//        val auditory = getAuditory()
//        val lecturer = getLecturer()
//        val group = getGroup()
//
//        val request = OccupyAuditoryRequest("8:00", "9:00", "19.05.2019", lecturer.id, group.id, "comment")
//
//        `when`(groupRepository.findById(group.id)).thenReturn(Optional.of(group))
//        `when`(lecturerRepository.findById(lecturer.id)).thenReturn(Optional.of(lecturer))
//        `when`(timeBlockRepository.findByTime(request.timeFrom, request.timeTo)).thenReturn(null)
//
//        try {
//            auditoryOccupationService.occupyAuditory(auditory.id, request)
//        } catch (ex: ScheduleGeneratorException) {
//            Assert.assertEquals(ErrorCode.TIMEBLOCK_NOT_EXISTS, ex.errorCode);
//        }
//
//        verify(groupRepository).findById(group.id)
//        verify(lecturerRepository).findById(lecturer.id)
//        verify(timeBlockRepository).findByTime(request.timeFrom, request.timeTo)
//    }
//
//    @Test
//    fun testOccupyNonExistingAuditory() {
//        val lecturer = getLecturer()
//        val group = getGroup()
//        val timeBlock = getTimeBlock()
//
//        val request = OccupyAuditoryRequest(timeBlock.timeFrom, timeBlock.timeTo, "19.05.2019", lecturer.id, group.id, "comment")
//
//        `when`(groupRepository.findById(group.id)).thenReturn(Optional.of(group))
//        `when`(lecturerRepository.findById(lecturer.id)).thenReturn(Optional.of(lecturer))
//        `when`(timeBlockRepository.findByTime(request.timeFrom, request.timeTo)).thenReturn(timeBlock)
//        `when`(auditoryRepository.findById(2)).thenReturn(Optional.empty())
//
//        try {
//            auditoryOccupationService.occupyAuditory(2, request)
//        } catch (ex: ScheduleGeneratorException) {
//            Assert.assertEquals(ErrorCode.AUDITORY_NOT_EXISTS, ex.errorCode);
//        }
//
//        verify(groupRepository).findById(group.id)
//        verify(lecturerRepository).findById(lecturer.id)
//        verify(timeBlockRepository).findByTime(request.timeFrom, request.timeTo)
//        verify(auditoryRepository).findById(2)
//    }
//
//    @Test
//    fun testOccupyAuditoryNotForGroup() {
//        val auditory = getAuditory()
//        val lecturer = getLecturer()
//        val timeBlock = getTimeBlock()
//        val auditoryOccupation = getAuditoryOccupation()
//        auditoryOccupation.group = null
//
//        val request = OccupyAuditoryRequest(timeBlock.timeFrom, timeBlock.timeTo, "19.05.2019", lecturer.id, 0, "comment")
//        val response = OccupationInfo(
//                auditoryOccupation.id,
//                request.timeFrom,
//                request.timeTo,
//                request.date,
//                auditoryOccupation.lecturer!!.getFullName(),
//                request.comment)
//
//        `when`(lecturerRepository.findById(lecturer.id)).thenReturn(Optional.of(lecturer))
//        `when`(timeBlockRepository.findByTime(request.timeFrom, request.timeTo)).thenReturn(timeBlock)
//        `when`(auditoryRepository.findById(auditory.id)).thenReturn(Optional.of(auditory))
//        `when`(auditoryOccupationRepository.save(auditoryOccupation)).thenReturn(auditoryOccupation)
//
//        assertEquals(response, auditoryOccupationService.occupyAuditory(auditory.id, request))
//
//        verify(lecturerRepository).findById(lecturer.id)
//        verify(timeBlockRepository).findByTime(request.timeFrom, request.timeTo)
//        verify(auditoryRepository).findById(auditory.id)
//        verify(auditoryOccupationRepository).save(auditoryOccupation)
//    }
//
//    @Test
//    fun testOccupyAuditoryByNotLecturer() {
//        val auditory = getAuditory()
//        val lecturer = getLecturer()
//        val group = getGroup()
//        val timeBlock = getTimeBlock()
//        val auditoryOccupation = getAuditoryOccupation()
//        auditoryOccupation.lecturer = null
//
//        val request = OccupyAuditoryRequest(timeBlock.timeFrom, timeBlock.timeTo, "19.05.2019", 0, group.id, "comment")
//        val response = OccupationInfo(
//                auditoryOccupation.id,
//                request.timeFrom,
//                request.timeTo,
//                request.date,
//                group,
//                request.comment)
//
//        `when`(groupRepository.findById(group.id)).thenReturn(Optional.of(group))
//        `when`(timeBlockRepository.findByTime(request.timeFrom, request.timeTo)).thenReturn(timeBlock)
//        `when`(auditoryRepository.findById(auditory.id)).thenReturn(Optional.of(auditory))
//        `when`(auditoryOccupationRepository.save(auditoryOccupation)).thenReturn(auditoryOccupation)
//
//        assertEquals(response, auditoryOccupationService.occupyAuditory(auditory.id, request))
//
//        verify(groupRepository).findById(group.id)
//        verify(timeBlockRepository).findByTime(request.timeFrom, request.timeTo)
//        verify(auditoryRepository).findById(auditory.id)
//        verify(auditoryOccupationRepository).save(auditoryOccupation)
//    }
//
//    @Test
//    fun testDeleteAuditoryOccupation() {
//
//        val auditoryOccupation = getAuditoryOccupation()
//        `when`(auditoryOccupationRepository.existsById(auditoryOccupation.id)).thenReturn(true)
//
//        auditoryOccupationService.deleteAuditoryOccupation(auditoryOccupation.id)
//
//        verify(auditoryOccupationRepository).existsById(auditoryOccupation.id)
//        verify(auditoryOccupationRepository).deleteById(auditoryOccupation.id)
//
//    }
//
//    @Test
//    fun testDeleteAllAuditoryOccupations() {
//
//        auditoryOccupationService.deleteAllAuditoryOccupations(1)
//        verify(auditoryOccupationRepository).deleteAllByAuditory(1)
//    }

//
//    private fun getAuditory(): Auditory {
//        return Auditory(1, getBuilding(), "214")
//    }
//
//    private fun getAuditoryOccupation(): AuditoryOccupation {
//        return AuditoryOccupation(getAuditory(), getTimeBlock(), "19.05.2019", getLecturer(), getGroup(), "comment")
//    }
//
//    private fun getBuilding(): Building {
//        return Building(1, 1, "пр. Мира, 55-а")
//    }
//
//    private fun getFaculty(): Faculty {
//        return Faculty(1, getBuilding(), "ИМИТ");
//    }
//
//    private fun getChair(): Chair {
//        return Chair(1, getFaculty(), "КАФЕДРА ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ И ЗАЩИТЫ ИНФОРМАЦИИ");
//    }
//
//    private fun getGroup(): List<Group> {
//        val studyDirection = StudyDirection(1, getFaculty(), Qualification.BACCALAUREATE, "name")
//        return listOf(Group(1, studyDirection, "МПБ-604"))
//    }
//
//    private fun getLecturer(): Lecturer {
//        return Lecturer(1, getChair(), getPersonalData(), false)
//    }
//
//    private fun getPersonalData(): PersonalData {
//        return PersonalData(1, "Firstname", "Patronymic", "Lastname")
//    }
//
//    private fun getTimeBlock(): TimeBlock {
//        return TimeBlock("8:00", "9:35")
//    }

}