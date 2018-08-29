package forms

import play.api.data.Form
import play.api.data.Forms.{mapping,email,number,text}
case class User(firstname:String,lastname:String,email: EmailGroup,password:String,mobile:String,gender:String)

case class EmailGroup(email:String,confirmemail:String)

class UserForm {

  val userForm=Form(
    mapping(
      "firstname" -> text.verifying("please enter your first name",_.nonEmpty) ,
      "lastname" -> text.verifying("please enter your last name",_.nonEmpty) ,

      "emailGroup" -> mapping(
        "email" -> email ,
        "confirmemail" -> email
      )(EmailGroup.apply)(EmailGroup.unapply).verifying("please check email and confirm email are not same", emailGroup=>emailGroup.confirmemail == emailGroup.email),

      "password" -> text.verifying("",_.nonEmpty) ,
      "mobile" ->  text.verifying("enter mobile number",_.nonEmpty) ,
      "gender" -> text.verifying("please select your gender",_.nonEmpty)
    )(User.apply)(User.unapply)
  )
}
