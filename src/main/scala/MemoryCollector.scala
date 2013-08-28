package com.bounscale

import java.lang.management._

class MemoryCollector extends BaseCollector{
  var memory:Long = _
  //var freeMemory:Long = _

  val mbean:MemoryMXBean = ManagementFactory.getMemoryMXBean()
  //val totalMemoryMethod = bean.getClass().getDeclaredMethod("getTotalPhysicalMemorySize")
  //val freeMemoryMethod  = bean.getClass().getDeclaredMethod("getFreePhysicalMemorySize")
  //totalMemoryMethod.setAccessible(true)
  //freeMemoryMethod.setAccessible(true)


  override def pre(){
  }

  override def post(){
    memory = (mbean.getNonHeapMemoryUsage().getCommitted() + mbean.getHeapMemoryUsage().getCommitted()) / 1024 / 1024
    //totalMemory = totalMemoryMethod.invoke(bean).asInstanceOf[Long]
    //freeMemory  = freeMemoryMethod.invoke(bean).asInstanceOf[Long]
  }

  override def getName():String = {
    return "memory"
  }

  override def getValue():String = {
    return memory.toString()
  }
}
