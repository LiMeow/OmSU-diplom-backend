package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "tags")
class Tag(@Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          var id: Int,
          var tag: String) {

    constructor(tag: String) : this(0, tag)

}
