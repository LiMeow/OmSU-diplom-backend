package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "lecturers")
class Lecturer(@Id
               @GeneratedValue(strategy = GenerationType.IDENTITY)
               var id: Int,

               @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
               @JoinColumn(name = "chair_id")
               var chair: Chair,

               @OneToOne(cascade = [CascadeType.ALL])
               @JoinColumn(name = "personal_data_id", referencedColumnName = "id")
               var personalData: PersonalData,

               @Column
               var enabled: Boolean,

               @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecturer")
               var preferences: List<LecturerPreferences>?) {

    constructor(id: Int,
                chair: Chair,
                personalData: PersonalData,
                enabled: Boolean) :
            this(id, chair, personalData, enabled, null)

    constructor(chair: Chair,
                personalData: PersonalData,
                enabled: Boolean) :
            this(0, chair, personalData, enabled)


    fun getFullName(): String {
        return personalData.firstName[0] + "." +
                personalData.patronymic!![0] + "." +
                personalData.lastName;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Lecturer) return false

        if (id != other.id) return false
        if (chair != other.chair) return false
        if (personalData != other.personalData) return false
        if (enabled != other.enabled) return false
        if (preferences != other.preferences) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + chair.hashCode()
        result = 31 * result + personalData.hashCode()
        result = 31 * result + enabled.hashCode()
        result = 31 * result + (preferences?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Lecturer(" +
                "id=$id, " +
                "chair=$chair, " +
                "personalData=$personalData, " +
                "enabled=$enabled, " +
                "preferences=$preferences)"
    }


}
