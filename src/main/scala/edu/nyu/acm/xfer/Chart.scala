package edu.nyu.acm.xfer

import java.io._
import org.jfree.chart._
import org.jfree.data.general._

object Chart extends App {

  val dataset = new DefaultPieDataset
  dataset.setValue("IPhone 5s", 20D) 
  dataset.setValue("SamSung Grand", 20D)   
  dataset.setValue("MotoG", 40D)
  dataset.setValue("Nokia Lumia", 10D)

  val chart = ChartFactory.createPieChart("Mobile Sales", dataset, true, true, false)
  val width = 640
  val height = 480 
  val pieChart = new File("PieChart.jpeg") 
  ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height)

}