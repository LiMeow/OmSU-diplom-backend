package omsu.imit.schedule.dto.response

class ScheduleItemInfo(var id: Int,
                       var auditorOccupation: OccupationInfo,
                       var discipline: String,
                       var activity: String) {
}