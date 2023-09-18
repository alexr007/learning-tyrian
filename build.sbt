Global / onChangedBuildSource := ReloadOnSourceChanges

// https://github.com/PurpleKingdomGames/tyrian-todo
// https://github.com/PurpleKingdomGames/tyrian
// https://github.com/PurpleKingdomGames/tyrian/releases/tag/v0.7.0
lazy val frontend = (project in file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaVersion := "3.3.1",
    name         := "app",
    version      := "0.1.0",
    libraryDependencies ++= Seq(
      "io.indigoengine" %%% "tyrian-io"     % "0.7.1",
      "com.armanbilge"  %%% "fs2-dom"       % "0.2.1",
      "io.circe"        %%% "circe-parser"  % "0.14.6",
      "io.circe"        %%% "circe-generic" % "0.14.6"
    ),
    // just to track transitive dependencies
    dependencyOverrides ++= Seq(
      "co.fs2"       %%% "fs2-core"    % "3.9.2",
      "org.scala-js" %%% "scalajs-dom" % "2.6.0"
    ),
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
    semanticdbEnabled := true,
    autoAPIMappings   := true
  )
