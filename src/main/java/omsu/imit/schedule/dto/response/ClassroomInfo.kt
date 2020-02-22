package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Tag

class ClassroomInfo(var id: Int,
                    var number: String,
                    var tags: List<Tag>,
                    var events: List<EventInfo>?) {

    constructor(id: Int, number: String) : this(id, number, mutableListOf(), mutableListOf())

    override fun toString(): String {
        return "ClassroomInfo(" +
                "id=$id, " +
                "number='$number', " +
                "tags=$tags, " +
                "events=$events)"
    }


}
