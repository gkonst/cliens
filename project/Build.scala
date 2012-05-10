import sbt._

object Build extends Build {

  // profile you’re going to build against
  case class Profile(name: String)

  object Profile {
    val dev = Profile("dev")
    val ci = Profile("ci")
    val prod = Profile("prod")

    implicit def toOption(profile: Profile): Option[Profile] = Option(profile)
  }

  // setting to make the functionality be accessible from the outside (e.g., the terminal)
  val selectedProfile = SettingKey[Option[Profile]]("selected-profile", "Uses resources for the specified profile.")

  lazy val IntegrationTest = config("it") extend (Test)

  lazy val root =
    Project("root", file("."))
      .configs(IntegrationTest)
      .settings(Defaults.itSettings: _*)

}
