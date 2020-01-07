package omsu.imit.schedule.dto.response

class MetaInfo(var total: Int,
               var page: Int,
               var size: Int,
               var next: String?,
               var prev: String?,
               var first: String?,
               var last: String?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MetaInfo) return false

        if (total != other.total) return false
        if (page != other.page) return false
        if (size != other.size) return false
        if (next != other.next) return false
        if (prev != other.prev) return false
        if (first != other.first) return false
        if (last != other.last) return false

        return true
    }

    override fun hashCode(): Int {
        var result = total
        result = 31 * result + page
        result = 31 * result + size
        result = 31 * result + next.hashCode()
        result = 31 * result + prev.hashCode()
        result = 31 * result + first.hashCode()
        result = 31 * result + last.hashCode()
        return result
    }

    override fun toString(): String {
        return "MetaInfo(" +
                "total=$total, " +
                "page=$page, " +
                "size=$size, " +
                "next='$next', " +
                "prev='$prev', " +
                "first='$first', " +
                "last='$last')"
    }
}
