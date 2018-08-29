package forms

import play.api.data.Form
import play.api.data.Forms.{email, text}
import play.api.data.Forms.mapping


case class UserSignin(email:String,password:String)

class Signinform {

  val signinForm=Form(
    mapping(
      "email" -> email ,
      "password" -> text.verifying("please enter your password",_.nonEmpty)
    ) (UserSignin.apply)(UserSignin.unapply)
  )

}
