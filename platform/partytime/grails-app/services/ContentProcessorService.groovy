/**
 * Party Time Project (http://code.google.com/p/party-time)
 *
 * Copyright 2009 Favorite Medium LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.imageio.*
import java.awt.image.*
import java.security.MessageDigest

import org.codehaus.groovy.grails.commons.*

import com.favoritemedium.partytime.domain.PostItem
import com.favoritemedium.partytime.ICDNProvider


class ContentProcessorService {

    boolean transactional = false

   // private ICDNProvider cdnProvider
   // def cdnProvider
    def localCDNService


    def config = ConfigurationHolder.config


    /**
     * Process the content from the given URL.
     *
     * @param url 
     */
    def processUrl(String url) {
        assert url, "URL must not be null"
        
    //  if ( isYoutube(url) ) {
    //      //
    //  } else 
        if ( (new URL(url).openConnection().getHeaderField('Content-Type')) ==~ /image.*/ ) {
            return processImage(url)
        } else {
            //Do nothing by default for now
        }
    }

    /**
     * Process the image from the given URL.
     *
     * @param imgUrl URL of the image
     */
    def processImage(String imgUrl) {
        assert imgUrl, "URL must not be null"

        def cal = Calendar.instance
        int year = cal.get(Calendar.YEAR)
        int month = cal.get(Calendar.MONTH)
        int day = cal.get(Calendar.DAY_OF_MONTH)
        String md5CheckSum

        try {

            // Create a URL for the image's location
            URL url = new URL(imgUrl);
        
            // Get the image
            BufferedImage image = ImageIO.read(url)
            
            def outputStream = new ByteArrayOutputStream()
            ImageIO.write(image, "PNG", outputStream)
            def data = outputStream.toByteArray()
            def messageMd5 = MessageDigest.getInstance("md5")
            messageMd5.update(data)

            def md5sum = messageMd5.digest()
            BigInteger bigInt = new BigInteger(1, md5sum)
            md5CheckSum = bigInt.toString(16).padLeft(32, '0')
            
            def file = config.image.dir ?: new File(System.getProperty("user.home") 
                                            + "/.partytime/images/postItem/${year}/${month}/${day}/")
            file.mkdirs() 

            ImageIO.write(image, "PNG", new File(file.canonicalPath + "/${md5CheckSum}.png") )
            resizeImage(file, "${md5CheckSum}")

            assert cdnProvider, "cdnProvider must not be null"
            cdnProvider.save(file)

        } catch (e) {
            log.error(e)
            System.err.println( e )
        }
        return md5CheckSum
            
    }

    def resizeImage(File filePath, String fileName) {
        assert filePath, "File must be valid and not null"
        log.debug("  ==> Resizing image: ${fileName}")
        // 1. Get the file
        // 2. Resize
        // 3. Save back to the thumbnail directory with the same name
        def file = config.image.dir ?: new File(System.getProperty("user.home") + "/.partytime/images/postItem/thumbnails")
        if (!file.exists()) file.mkdirs()

        def imageTool = new ImageTool()

        imageTool.load( filePath.getAbsolutePath() + "/${fileName}.png" )
        imageTool.thumbnailSpecial(120,90,1,2)
        imageTool.writeResult( file.getAbsolutePath() + "/${fileName}-thumb.png", "PNG")
    }

    def process(PostItem item) {
        if (item.type == "link") {
           String sig = processUrl(item.value) 
           item.signature = sig
        }
    }

    def isYoutube(String url) {
        return ( url ==~ /.*youtube.com\/.*/)
    }

}
