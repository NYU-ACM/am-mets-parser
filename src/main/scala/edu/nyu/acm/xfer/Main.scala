package edu.nyu.acm.xfer

import java.io._
import java.util.UUID
import javax.xml.stream._
import javax.xml.stream.events._

object Main extends App {
	
	case class FileObject(uuid: UUID, size: Long, fileType: String)

  val file = new File(args(0))

  val factory = XMLInputFactory.newInstance()
  val eventReader = factory.createXMLEventReader(new FileReader(file))
  var fileObjects = Vector.empty[FileObject]
  
  //parse METS file 
  while(eventReader.hasNext){ getObject() }
  
  //process the summary into a map
  
  var map = Map.empty[String, Tuple2[Int, Long]]

  fileObjects.foreach { fo =>
  	map.contains(fo.fileType) match {
  		case true => { 
  			val values = map(fo.fileType)
  			map += (fo.fileType -> new Tuple2(values._1 + 1, values._2 + fo.size))
	  	}
  		case false => { 
  			map +=  (fo.fileType -> Tuple2(1, fo.size))
  		}
  	}
  }
  
  //format a report
  var totalCount = 0
  var totalSize = 0L
  val writer = new FileWriter(new File("output.csv"))
  writer.write("\"key\",\"count\",\"size(Bytes)\",\"size(Human)\"\n")
	writer.flush

  val keys = collection.immutable.SortedSet[String]() ++ map.keySet

  keys.foreach { key => 
  	val values = map(key)

		totalSize = totalSize + values._2
  	totalCount = totalCount + values._1

  	writer.write("\"" + key 
  		+ "\",\"" + values._1.toString 
  		+ "\",\"" + values._2.toString 
  		+ "\",\"" + formatSize(values._2)
  		+ "\"\n")
  	writer.flush
  }
  
  //write the totals
  writer.write(
  	"\"\",\"" + totalCount.toString 
  	+ "\",\"" + totalSize.toString 
  	+ "\",\"" + formatSize(totalSize)
  	+ "\"\n")
  writer.flush
  writer.close

  def formatSize(v: Long): String = {
  	//this function is not outputting the correct results
    if (v < 1024) {  v + " B" }
    else {
      val z: Int = (63 - java.lang.Long.numberOfLeadingZeros(v)) / 10
      ((v / (1L << (z*10))).toDouble).toString + " " + "KMGTPE".charAt(z - 1) + "B"
		}
  }


  def getObject() {
  	val event = eventReader.nextEvent()
	
		event.getEventType() match {
		  case XMLStreamConstants.START_ELEMENT => {
		  	val startElement = event.asStartElement()
		  	val qName = startElement.getName()
		  	if(qName.getLocalPart.equals("objectIdentifierValue")){
		  		val uuid = UUID.fromString(eventReader.nextEvent().toString)
		  		fileObjects = fileObjects ++ Vector(new FileObject(uuid, getSize, getType))		  		
		  	}
		  }
		  case _ => 
		}
  }

  def getType(): String = {
  	
  	val event = eventReader.nextEvent()
		
		event.getEventType() match {
  		case XMLStreamConstants.START_ELEMENT => {
  			val startElement = event.asStartElement()
  			val qName = startElement.getName()
  			qName.getLocalPart.equals("formatName") match {
  				case true => {
  				  eventReader.nextEvent().toString
  				}
  				case false => getType
  			}
  		}
  		case _ => getType
  	}
  }

  def getSize(): Long = {
  	
  	val event = eventReader.nextEvent()
		
		event.getEventType() match {
  		case XMLStreamConstants.START_ELEMENT => {
  			val startElement = event.asStartElement()
  			val qName = startElement.getName()
  			qName.getLocalPart.equals("size") match {
  				case true => {
  				  eventReader.nextEvent().toString.toLong
  				}
  				case false => getSize
  			}
  		}
  		case _ => getSize
  	}
  	
  }

}