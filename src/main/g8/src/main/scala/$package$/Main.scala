package $package$

object Main extends App with Logging with SparkEnv {
  import spark.implicits._

  val c = spark.sqlContext.range(1, 10).count()

  logger.info(s"Done - count is \$c")
}
