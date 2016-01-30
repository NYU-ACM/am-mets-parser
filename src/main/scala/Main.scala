package edu.nyu.acm.xfer

import java.io._
import java.util.UUID
import javax.xml.stream._
import javax.xml.stream.events._
import collection.JavaConversions._
import com.sun.xml.internal.stream.util.ReadOnlyIterator

object Main extends App {
	
	case class FileObject(uuid: UUID, size: Long, fileType: String)

  val file = new File("test.xml")
  val factory = XMLInputFactory.newInstance()
  val eventReader = factory.createXMLEventReader(new FileReader(file))
  var fileObjects = Vector.empty[FileObject]
  
  while(eventReader.hasNext){ getObject() }

  fileObjects.foreach { fileObject => println(fileObject) }

  def getObject() {
  	val event = eventReader.nextEvent()
	
		event.getEventType() match {
		  case XMLStreamConstants.START_ELEMENT => {
		  	val startElement = event.asStartElement()
		  	val qName = startElement.getName()
		  	if(qName.getLocalPart.equals("objectIdentifierValue")){
		  		val uuid = UUID.fromString(eventReader.nextEvent().toString)
		  		//val size = getSize()
		  		//val fileType = getType()
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