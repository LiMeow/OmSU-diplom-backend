package omsu.imit.schedule.dto.request

class EditClassroomRequest(var number: String? = null,
                           var tags: List<Int>? = null) {
    override fun toString(): String {
        return "EditClassroomRequest(" +
                "number=$number, " +
                "tags=$tags)"
    }
}
