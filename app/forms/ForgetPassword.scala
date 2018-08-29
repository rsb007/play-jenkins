package forms

import play.api.data.Form
import play.api.data.Forms.{mapping, text,email}

case class Password(password:String,confirmpassword:String)

case class UserForget(email:String,password:Password)

class ForgetPassword {

  val changepassword=Form (
    mapping(
      "email" -> email ,
      "passwordGroup" -> mapping(
        "confirmpassword" -> text.verifying("please enter your  passowrd",_.nonEmpty) ,
        "password" -> text.verifying("please enter your passowrd",_.nonEmpty)
      )(Password.apply)(Password.unapply)
        .verifying("Password and confirm password does't match",
          passwordGroup=>passwordGroup.password == passwordGroup.confirmpassword)

    ) (UserForget.apply)(UserForget.unapply)
  )
}
