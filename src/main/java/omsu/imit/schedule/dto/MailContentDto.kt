package omsu.imit.schedule.dto

import omsu.imit.schedule.model.User

data class MailContentDto(var name: String,
                          var verificationToken: String) {

    constructor(user: User, verificationToken: String)
            : this(user.firstName + " " + user.patronymic, verificationToken)

}