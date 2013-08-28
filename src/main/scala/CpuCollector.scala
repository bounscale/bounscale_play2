package com.bounscale

import java.lang.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory

class CpuCollector extends BaseCollector{
  var preTime:Long  = _
  var postTime:Long = _

  val bean = ManagementFactory.getOperatingSystemMXBean()
  val cpuMethod = bean.getClass().getDeclaredMethod("getProcessCpuTime")
  cpuMethod.setAccessible(true)

  override def pre(){
    preTime = cpuMethod.invoke(bean).asInstanceOf[Long]
  }

  override def post(){
    postTime = cpuMethod.invoke(bean).asInstanceOf[Long]
  }

  override def getName():String = {
    return "cpu"
  }

  override def getValue():String = {
    return ((postTime - preTime) / 1000000).toString()
  }
}
