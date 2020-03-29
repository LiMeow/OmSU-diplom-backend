package omsu.imit.schedule.model

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    ROLE_ADMIN,
    ROLE_DISPATCHER,
    ROLE_LECTURER,
    ROLE_USER;

    override fun getAuthority(): String? {
        return name
    }
}
