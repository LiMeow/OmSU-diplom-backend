package omsu.imit.schedule.model

import javax.persistence.*


@Entity
@Table(name = "students")
class Student(@Id
              @GeneratedValue(strategy = GenerationType.IDENTITY)
              var id: Int,

              @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
              @JoinColumn(name = "group_id")
              var group: Group?,

              @OneToOne(cascade = [CascadeType.ALL])
              @JoinColumn(name = "personal_data_id", referencedColumnName = "id")
              var personalData: PersonalData) {
}