package omsu.imit.schedule.response

class AuditoryFullInfo(var metaInfo: MetaInfo,
                       var auditoryInfoList: List<AuditoryInfo>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuditoryFullInfo) return false

        if (metaInfo != other.metaInfo) return false
        if (auditoryInfoList != other.auditoryInfoList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = metaInfo.hashCode() ?: 0
        result = 31 * result + (auditoryInfoList.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AuditoryFullInfo(" +
                "metaInfo=$metaInfo," +
                " auditoryInfoList=$auditoryInfoList)"
    }

}
