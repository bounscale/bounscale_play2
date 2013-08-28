package com.bounscale

import java.util.Date

object History{
  var list = List.empty[List[Date]]
}

class BusynessCollector extends BaseCollector{
  val historyHoldingSec = 10

  var preDate:Date  = _
  var postDate:Date = _

  override def pre(){
    preDate = new Date()
  }

  override def post(){
    postDate = new Date()
    History.list = History.list :+ List(preDate, postDate)
    fixHistory()
  }

  override def getName():String = {
    return "busyness"
  }

  override def getValue():String = {
    if(History.list.length < 2) return "0"
    ((estimateSec() / wholeSec()) * 100).toString()
  }

  protected def fixHistory(){
    History.list = History.list.filter(h => {
      !(((postDate.getTime() - h(1).getTime()) / 1000.0) > historyHoldingSec)
    })
  }

  protected def wholeSec():Double = {
    val oldestPre = History.list(0)(0).getTime()
    val newestPost = History.list((History.list.length - 1))(1).getTime()
    ((newestPost - oldestPre) / 1000.0)
  }

  protected def estimateSec():Double = {
    var result = 0.0
    History.list.foreach(h => {
      result = result + ((h(1).getTime() - h(0).getTime()) / 1000.0)
    })
    result
  }
}
