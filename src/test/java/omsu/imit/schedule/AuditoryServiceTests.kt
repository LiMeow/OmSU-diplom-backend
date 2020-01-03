package omsu.imit.schedule

import omsu.imit.schedule.repository.AuditoryOccupationRepository
import omsu.imit.schedule.repository.AuditoryRepository
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.repository.TagRepository
import omsu.imit.schedule.service.AuditoryService
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuditoryServiceTests {
    @Mock
    lateinit var auditoryRepository: AuditoryRepository
    @Mock
    lateinit var auditoryOccupationRepository: AuditoryOccupationRepository
    @Mock
    lateinit var buildingRepository: BuildingRepository
    @Mock
    lateinit var tagRepository: TagRepository

    private lateinit var auditoryService: AuditoryService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.auditoryService = AuditoryService(
                this.auditoryRepository,
                this.auditoryOccupationRepository,
                this.buildingRepository,
                this.tagRepository)
    }

//    @Test
//    fun testAddAuditory() {
//        val request = CreateAuditoryRequest(1, "214")
//        val building = Building(1, 1, "пр. Мира, 55-а")
//        val auditory = Auditory(0, building, request.number)
//        val response = AuditoryInfo(auditory.id, building.id, auditory.number)
//
//        `when`(buildingRepository.findById(1)).thenReturn(Optional.of(building))
//        `when`(auditoryRepository.findByBuildingAndNumber(request.buildingId, request.number)).thenReturn(null)
//        `when`(auditoryRepository.save(auditory)).thenReturn(auditory)
//
//        assertEquals(response, auditoryService.addAuditory(request))
//
//        verify(buildingRepository).findById(1)
//        verify(auditoryRepository).findByBuildingAndNumber(request.buildingId, request.number)
//        verify(auditoryRepository).save(auditory)
//    }
//
//    @Test
//    fun testAddAuditoryToNonExistingBuilding() {
//        val request = CreateAuditoryRequest(1, "214")
//        `when`(buildingRepository.findById(1)).thenReturn(Optional.empty())
//
//        try {
//            auditoryService.addAuditory(request)
//        } catch (ex: ScheduleGeneratorException) {
//            assertEquals(ErrorCode.BUILDING_NOT_EXISTS, ex.errorCode);
//        }
//
//        verify(buildingRepository).findById(1)
//    }
//
//    @Test
//    fun testAddAlreadyExistingAuditory() {
//        val request = CreateAuditoryRequest(1, "214")
//        val building = Building(1, 1, "пр. Мира, 55-а")
//        val auditory = Auditory(0, building, request.number)
//        val response = AuditoryInfo(auditory.id, building.id, auditory.number)
//
//        `when`(buildingRepository.findById(1)).thenReturn(Optional.of(building))
//        `when`(auditoryRepository.findByBuildingAndNumber(request.buildingId, request.number)).thenReturn(auditory)
//
//        try {
//            auditoryService.addAuditory(request)
//        } catch (ex: ScheduleGeneratorException) {
//            assertEquals(ErrorCode.AUDITORY_ALREADY_EXISTS, ex.errorCode);
//        }
//
//        verify(buildingRepository).findById(1)
//        verify(auditoryRepository).findByBuildingAndNumber(request.buildingId, request.number)
//    }
//
//    @Test
//    fun testGetAuditoryById() {
//        val building = Building(1, 1, "пр. Мира, 55-а")
//        val auditory = Auditory(1, building, "214")
//        val response = AuditoryInfo(auditory.id, building.id, auditory.number)
//
//        `when`(auditoryRepository.findById(1)).thenReturn(Optional.of(auditory))
//        assertEquals(response, auditoryService.getAuditoryById(1))
//        verify(auditoryRepository).findById(1)
//    }
//
//    @Test
//    fun testNonExistingGetAuditoryById() {
//        `when`(auditoryRepository.findById(1)).thenReturn(Optional.empty())
//        try {
//            auditoryService.getAuditoryById(1)
//        } catch (ex: ScheduleGeneratorException) {
//            assertEquals(ErrorCode.AUDITORY_NOT_EXISTS, ex.errorCode);
//        }
//        verify(auditoryRepository).findById(1)
//    }
//
//    @Test
//    fun testGetAllAuditoriesByDate() {
//        val date = "01.09.2019"
//        val page = 0
//        val size = 3
//        val pageable: Pageable = PageRequest.of(page, size, Sort.by("number"))
//        val building = Building(1, 1, "пр. Мира, 55-а")
//
//        val auditory1 = Auditory(1, building, "214")
//        val auditory2 = Auditory(2, building, "215")
//        val auditories = listOf(auditory1, auditory2)
//
//        val auditoryInfo1 = AuditoryInfo(auditory1.id, building.id, auditory1.number)
//        val auditoryInfo2 = AuditoryInfo(auditory2.id, building.id, auditory2.number)
//        val auditoryInfoList = listOf(auditoryInfo1, auditoryInfo2)
//        val metaInfo = MetaInfo(0, page, size,
//                null,
//                null,
//                "/auditories/building/1?page=0&size=3",
//                "/auditories/building/1?page=0&size=3")
//
//        val response = AuditoryFullInfo(metaInfo, auditoryInfoList)
//
//        `when`(buildingRepository.existsById(building.id)).thenReturn(true)
//        `when`(auditoryRepository.findAllByBuilding(building.id, pageable)).thenReturn(auditories)
//        `when`(auditoryOccupationRepository.findByAuditoryAndDate(auditory1.id, date)).thenReturn(null)
//        `when`(auditoryOccupationRepository.findByAuditoryAndDate(auditory2.id, date)).thenReturn(null)
//
//        assertEquals(response, auditoryService.getAllAuditoriesByDate(building.id, date, page, size))
//
//        verify(buildingRepository).existsById(building.id)
//        verify(auditoryRepository).findAllByBuilding(building.id, pageable)
//        verify(auditoryOccupationRepository).findByAuditoryAndDate(auditory1.id, date)
//        verify(auditoryOccupationRepository).findByAuditoryAndDate(auditory2.id, date)
//    }

}