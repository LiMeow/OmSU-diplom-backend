package omsu.imit.schedule.repository

import omsu.imit.schedule.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {

    @Query("SELECT u FROM User u WHERE u.email = :email ")
    fun findByEmail(@Param("email") email: String): User?


}