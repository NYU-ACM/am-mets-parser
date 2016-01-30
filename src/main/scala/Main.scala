package edu.nyu.acm.xfer

import java.io._
import java.util.UUID
import javax.xml.stream._
import javax.xml.stream.events._
import collection.JavaConversions._
import com.sun.xml.internal.stream.util.ReadOnlyIterator

object Main extends App {
  val file = new File("test.xml")
  val factory = XMLInputFactory.newInstance()
  val eventReader = factory.createXMLEventReader(new FileReader(file))
  var uuids = Vector.empty[UUID]
  
  while(eventReader.hasNext){ getObjectIdentifier() }

  println(uuids.size)
  
  def getObjectIdentifier() {
  	val event = eventReader.nextEvent()
	
	event.getEventType() match {
	  case XMLStreamConstants.START_ELEMENT => {
	  	val startElement = event.asStartElement()
	  	val qName = startElement.getName()
	  	if(qName.getLocalPart.equals("objectIdentifierValue")){
	  		var uuid = UUID.fromString(eventReader.nextEvent().toString)
	  		uuids = uuids ++ Vector(uuid)
	  	}
	  }
	  case _ => 
	}
  }
}