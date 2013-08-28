package com.bounscale

import java.util.Date
import scala.util.parsing.json.{JSONObject, JSONArray}

class BaseWriter{
  val formatVersion = 0
  val bounscaleOpenUuid = "b74e646e-7e55-448f-814d-e36eedc44ea9"
  val bounscaleCloseUuid = "4a061908-db52-4224-ad4b-9850a47c7edf"

  def write(collectorInstances : List[BaseCollector]):String = {
    var data = List.empty[JSONObject]
    val currentDate = (new Date()).toString()
    val playVer = play.core.PlayVersion.current

    collectorInstances.foreach(collector => {
      data = data :+ JSONObject(Map("name" -> collector.getName(), "value" -> collector.getValue()))
    })

    val json = JSONObject(Map("format_ver" -> formatVersion, "datetime" -> currentDate, "data" -> JSONArray(data), "framework_ver" -> ("PlayFramework " + playVer)))
    val result_str = bounscaleOpenUuid + json.toString() + bounscaleCloseUuid
    output(result_str)
    return result_str
  }

  protected def output(str : String){}
}
