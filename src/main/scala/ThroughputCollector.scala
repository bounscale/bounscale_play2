package com.bounscale

class ThroughputCollector extends BusynessCollector{
  override def pre(){}
  override def post(){}

  override def getName():String = {
    return "throughput"
  }

  override def getValue():String = {
    if(History.list.length < 2) return "0"
    ((History.list.length / wholeSec()) * 60).toString()
  }
}
