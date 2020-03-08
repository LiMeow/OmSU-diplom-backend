package omsu.imit.schedule.model

import javax.persistence.*


@Entity
@Table(name = "course")
class Course(@Id
             @GeneratedValue(strategy = GenerationType.IDENTITY)
             var id: Int,

             @ManyToOne(fetch = FetchType.EAGER)
             @JoinColumn(name = "faculty_id")
             var faculty: Faculty,

             @Column(name = "start_year")
             var startYear: String,

             @Column(name = "finish_year")
             var finishYear: String,

             @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
             var groups: List<Group>? = ArrayList()) {
    constructor(faculty: Faculty, startYear: String, finishYear: String) :
            this(0, faculty, startYear, finishYear)
}
