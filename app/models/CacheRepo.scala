package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class UserInfo(id: Long, firstname: String, lastname: String, email: String, password: String, mobile: String, gender: String)

trait UserTable extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val userProfileQuery: TableQuery[UserProfile] = TableQuery[UserProfile]

  private[models] class UserProfile(tag: Tag) extends Table[UserInfo](tag, "Signup") {

    def * : ProvenShape[UserInfo] = (id, firstname, lastname, email, password, mobile, gender) <> (UserInfo.tupled, UserInfo.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def firstname: Rep[String] = column[String]("firstname")

    def lastname: Rep[String] = column[String]("lastname")

    def email: Rep[String] = column[String]("email")

    def password: Rep[String] = column[String]("password")

    def mobile: Rep[String] = column[String]("mobile")

    def gender: Rep[String] = column[String]("gender")
  }

}

class CacheRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends Impl with UserTable {}

trait Impl {

  self: UserTable =>

  import profile.api._

  def store(userRepo: UserInfo): Future[Long] = {

    db.run {
      userProfileQuery returning userProfileQuery.map(_.id) += userRepo
    }
  }

  def get(email: String): Future[Option[UserInfo]] = {
    db.run(userProfileQuery.filter(_.email === email.toLowerCase).result.headOption)
  }

  def getAllUserInfo: Future[Seq[UserInfo]] = {

    db.run(userProfileQuery.result)
  }

  def updatePassword(email: String, password: String): Future[Int] = {
    db.run {
      val q = {
        for {user <- userProfileQuery if user.email === email} yield user.password
      }
      q.update(password)
    }
  }
}
