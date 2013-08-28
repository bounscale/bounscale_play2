package com.bounscale

import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._

class BounscaleFilter extends Filter {
  def apply(next: (RequestHeader) => Result)(rh: RequestHeader) = {
    val collectors = List(
      new CpuCollector(),
      new MemoryCollector(),
      new BusynessCollector(),
      new ThroughputCollector()
    )

    def post(result: PlainResult): Result = {
      collectors.foreach(collector => collector.post())
      val writer = new HerokuWriter()
      writer.write(collectors)
      result
    }

    collectors.foreach(collector => collector.pre())

    next(rh) match {
      case plain: PlainResult => post(plain)
      case async: AsyncResult => async.transform(post)
    }
  }
}
