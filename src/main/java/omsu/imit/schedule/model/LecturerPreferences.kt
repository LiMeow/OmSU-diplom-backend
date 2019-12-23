package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "lecturer_preferences")
class LecturerPreferences(@Id
                          @GeneratedValue(strategy = GenerationType.IDENTITY)
                          var id: Int,

                          @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                          @JoinColumn(name = "lecturer_id")
                          var lecturer: Lecturer,

                          @Column
                          var preference: String) {

}
