package omsu.imit.schedule.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class Token @JsonCreator
constructor(@param:JsonProperty("token") val token: String)
