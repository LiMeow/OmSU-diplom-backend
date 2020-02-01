package omsu.imit.schedule.model

import javax.persistence.*


@Entity
@Table(name = "student")
class Student(@Id
              @GeneratedValue(strategy = GenerationType.IDENTITY)
              var id: Int,

              @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
              @JoinColumn(name = "group_id")
              var group: Group?,

              @OneToOne(cascade = [CascadeType.ALL])
              @JoinColumn(name = "user_id", referencedColumnName = "id")
              var user: User) {
}