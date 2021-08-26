import mill._, scalalib._, publish._
import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest_mill0.9:0.4.1`
import de.tobiasroeser.mill.integrationtest._

object PapermillModule extends ScalaModule with PublishModule {
  override def scalaVersion = "2.13.6"
  // ...
  override def compileIvyDeps = Agg(
    // scala-steward:off
    ivy"com.lihaoyi::os-lib:0.6.3",
    ivy"com.lihaoyi::mill-main:0.9.9",
    ivy"com.lihaoyi::mill-scalalib:0.9.9"
    // scala-steward:on
  )

  def publishVersion = "0.0.1"
  def pomSettings = PomSettings(
    description = "Papermill",
    organization = "com.luketebbs",
    url = "https://github.com/ltbs/papermill",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("ltbs", "papermill"),
    developers = Seq(
      Developer("ltbs", "Luke Tebbs", "https://github.com/ltbs")
    )
  )

}

object itest extends MillIntegrationTestModule {
  def millTestVersion = "0.9.9"
  def pluginsUnderTest = Seq(PapermillModule)
}

