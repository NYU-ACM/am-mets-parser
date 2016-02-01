package edu.nyu.acm.xfer

import java.io._
import org.jfree.chart._
import org.jfree.data.general._

trait ChartSupport {
  
  def createChart(map: Map[String, Tuple2[Int, Long]]) {
  	
  	val dataset = new DefaultPieDataset
  	val dataset2 = new DefaultPieDataset

  	val keys = collection.immutable.SortedSet[String]() ++ map.keySet
  	keys.foreach { key => 
  	  val values = map(key)	
  	  dataset.setValue(key, values._1)
  	  dataset2.setValue(key, values._2)
  	}

    val width = 640
    val height = 480

    val chart = ChartFactory.createPieChart("File Format Count", dataset, true, true, false)
    val pieChart = new File("counts.jpg") 
    ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height)

    val chart2 = ChartFactory.createPieChart("File Format Size", dataset2, true, true, false)
    val pieChart2 = new File("sizes.jpg") 
    ChartUtilities.saveChartAsJPEG(pieChart2, chart2, width, height)
  }
}