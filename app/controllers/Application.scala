package controllers

import forms.{ForgetPassword, Signinform, UserForm}
import javax.inject.Inject
import models.{CacheRepo, UserInfo}
import play.api.mvc._
import views.html._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Application @Inject()(controllerComponent: ControllerComponents, user: UserForm, cache: CacheRepo,
                            signin : Signinform ,forget : ForgetPassword) extends AbstractController(controllerComponent) {

  def index: Action[AnyContent] = Action { implicit request =>
    Ok(registration())
  }


  def signinForm: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.signin())
  }

  def getUser: Action[AnyContent] = Action.async { implicit request =>
    signin.signinForm.bindFromRequest().fold(
      formWithError => {
        Future.successful(BadRequest(s"${formWithError.errors}"))
      },
      data => {
        cache.get(data.email).flatMap {
          OptionUser => OptionUser.fold {
            Future.successful(NotFound("User doesn't exist!"))
          }{
               userInfo => if(userInfo.password == data.password) Future.successful(Ok(views.html.home()))
            else
                Future.successful(BadRequest(s"Username or Password is incorrect!"))
              }
          }
        }
       )
      }

  def register: Action[AnyContent] = Action.async { implicit request =>
    user.userForm.bindFromRequest().fold(
      formWithError => {
        Future.successful(BadRequest(s"${formWithError.errors}"))
      },
      data => {
        cache.get(data.email.email).flatMap { OptionUser =>
          OptionUser.fold {
            val dbPayload = UserInfo(0L, data.firstname, data.lastname, data.email.email, data.password, data.mobile, data.gender)
            cache.store(dbPayload).map(_ => Ok("Registration Successfull"))
          } {
            _ => Future.successful(Ok("user already exist"))
          }
        }
      }
    )
  }

  def forgetpassword: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.forgetpassword())
  }

  def viewAllUser: Action[AnyContent] = Action.async({ implicit request =>
      cache.getAllUserInfo.map {
        data => Ok(views.html.viewalluser(data))
      }
  })

  def homepage: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.home())
  }

  def changepassword: Action[AnyContent] = Action.async { implicit request =>
    forget.changepassword.bindFromRequest().fold(
      formWithError => {
        Future.successful(BadRequest(s"${formWithError.errors}"))
      },
      data => {
        cache.get(data.email).flatMap {
          OptionUser => OptionUser.fold {
            Future.successful(NotFound("User doesn't exist!"))
          }{
             user=> cache.updatePassword(data.email,data.password.password)
             Future.successful(Ok("Password Update successful"))
          }
        }
      }
    )
  }


  }
