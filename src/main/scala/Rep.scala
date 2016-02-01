
object Rep extends App {
  val types = Vector("Acrobat PDF 1.3", "Acrobat PDF 1.4", "Adobe Photoshop","EXIF Compressed Image 2.2.1 (big-endian)","Exchangeable Image File Format (Compressed) 2.2","JPEG 1.00","JPEG 1.01","JPEG 1.02","MPEG-4 Video","Microsoft Word Document 97-2003","PNG 1.0","PNG 1.2","RTF 1.5-1.6","RTF 1.7","Raw JPEG Stream","Stuffit","Unknown","XML 1.0")

  val pdf = "Acrobat".r

  types.foreach { t =>
  	t match {
  	  case pdf(_*) => println(t)
  	  case _ =>
  	}

  }
}