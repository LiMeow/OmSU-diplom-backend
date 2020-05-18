package omsu.imit.schedule.security

import omsu.imit.schedule.model.User
import omsu.imit.schedule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetails
@Autowired
constructor(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email)
                .orElseThrow { UsernameNotFoundException("User with email '$email' not found") }

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.password)
                .authorities(user.role)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build()
    }
}