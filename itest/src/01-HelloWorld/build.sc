import $exec.plugins

import mill._
import mill.scalalib._
import mill.contrib.papermill._
import mill.define.Command

object DemoModule extends ScalaModule with PapermillModule {
  override def scalaVersion: T[String] = "2.12.7"
}

def verify(): Command[Unit] = T.command {
  DemoModule.makeDocs.buildDocs()
  ()
}
