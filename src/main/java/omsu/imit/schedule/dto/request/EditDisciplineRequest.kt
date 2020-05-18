package omsu.imit.schedule.dto.request

data class EditDisciplineRequest(var name: String? = null,
                                 var requirements: List<Int>? = null)