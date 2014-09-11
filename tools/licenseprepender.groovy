/**
 * Append license text at top of Groovy/Java files.
 * http://www.greggbolinger.com/blog/2008/04/24/1209064020000.html
 */

if (args.length != 2) { 

  println 'Usage:' 
  println 'groovy licenseprepender.groovy /license/file/path /src/path' 

} else { 
  def licenseText = new File( args[0] ).text
  new File( args[ 1 ] ).eachFileRecurse { file -> 
    if( file.isFile() 
        && ( file.name.endsWith( ".java" ) 
            || file.name.endsWith(".groovy") ) { 
      print "\n  * Processing ${file.name} .."
      tempFile = new File( "tempFileToStoreFileToWrite" )
      print ".."
      tempFile.withWriter { w ->
        print ".."
        w.writeLine( licenseText )
        print ".."
        w.writeLine( file.text )
        print ".."
      } 
      print ".."
      file.delete() 
      print ".."
      tempFile.renameTo( new File( file.getAbsolutePath() ) ) 
      print ".. Done"
    } 
  } 
  print "\n"
} 
