package mill.contrib.papermill

import mill._, scalalib._, define._, modules._
import mill.eval.Result

trait PapermillModule extends ScalaModule {
  def hello = T {
    println("hiya!")
  }

  private val parent = this

  def bookSources = millSourcePath / "book"
  def bookOut = millSourcePath / "book-out"

  object makeDocs extends ScalaModule {

    override def defaultCommandName(): String = "buildDocs" 

    def buildDocs = T.task {
      java.nio.file.Files.createDirectories(parent.bookOut.wrapped)
      val cp = parent.runClasspath().map(_.path)
      val mainArgs = Seq(
        "--in", parent.bookSources.toString,
        "--out", parent.bookOut.toString,
        "--classpath", cp.mkString(":")
      )
      println(mainArgs)      

      val (jvmArgs, props) =
        forkArgs() -> Map()

      Jvm.runSubprocess(
        mainClass = "mdoc.Main",
        classPath = runClasspath().map(_.path),
        jvmArgs = jvmArgs,
        envArgs = forkEnv(),
        mainArgs = mainArgs,
        workingDir = forkWorkingDir(),
        useCpPassingJar = false
      )
    }

    override def moduleDeps: Seq[JavaModule] = Seq(parent)
    override def mainClass = Some("mdoc.Main")
    override def ivyDeps = Agg(
      ivy"org.scalameta::mdoc:2.2.22"
    )

    def scalaVersion = parent.scalaVersion

    import coursier.Repository

    override def repositories: Seq[Repository] = parent.repositories
    override def repositoriesTask: Task[Seq[Repository]] = parent.repositoriesTask
    override def resolutionCustomizer: Task[Option[coursier.Resolution => coursier.Resolution]] = parent.resolutionCustomizer
    override def javacOptions: T[Seq[String]] = parent.javacOptions
    override def zincWorker: ZincWorkerModule = parent.zincWorker
    override def skipIdea: Boolean = parent.skipIdea

    override def scalaOrganization: T[String] = parent.scalaOrganization()
    override def scalacPluginIvyDeps = parent.scalacPluginIvyDeps
    override def scalacPluginClasspath = parent.scalacPluginClasspath
    override def scalacOptions = parent.scalacOptions
  
  }

  def throwTantrum = T {
    throw new RuntimeException(s"Woe is ${this.getClass().getName()}");
//    sys.exit(1)
    ()
  }

}
