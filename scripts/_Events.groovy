
eventWarStart = { args ->
    
    try {

        def revision = "svnversion .".execute()
        revision.waitFor()
        
        def revFile = new File("web-app/WEB-INF/REVISION").newWriter()
        String revString = revision.text?.toString()?.split(':')[-1]
        revFile.write( "Revision: ${revString?.replaceAll('M', '') }" )
        revFile.close()
        
    } catch (e) {
        System.err.println("Failed to execute svnversion -- SVN build number may not be available because of this: ${e}")
    }
}
