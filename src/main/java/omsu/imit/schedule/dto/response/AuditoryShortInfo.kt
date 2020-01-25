package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Tag

class AuditoryShortInfo(var id: Int,
                        var number: String,
                        var tags: List<Tag>) {
}