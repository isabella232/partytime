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

import javax.activation.FileDataSource 


class MediaViewerController {

    String cacheDirectory = "${System.getProperty("user.home")}/.partytime/images/postItem"


    def index = { 
        println params    
    }

    def image = {
        String signature = params.id ?: params.sig
        String type = params.type

        def img

        if ("full" == type) {
            img = new File(cacheDirectory + "/thumbnails/${signature}-thumb.png")
        } else {
            img = new File(cacheDirectory + "/thumbnails/${signature}-thumb.png")
        }
        outputImage(img)
    }

    def viewPhoto = {
        println params
        def path = params.path
        def name = params.name
        def fullpath = params.fullpath
        def imgPath 

        if (fullpath && ( new File(fullpath).exists() )) {
            imgPath = fullpath
        } else {
            imgPath = grailsApplication.config.images.location.toString() +  path + File.separatorChar + name
            def file = new File(imgPath)
            if (!file.exists()) {
                imgPath = grailsApplication.config.images.location.toString() + "thumb.jpg"
            }
        }
        outputImage(imgPath)

    }

    def outputImage(imgfile) {

        def ds
        def is
        try {
            ds = new FileDataSource( imgfile ) 
            is = ds.inputStream
        } catch (e) {
            log.error(" Failed to load image: " + e ) 
            System.err.println( e )
        }

        response.setContentLength( is.available() )
        response.setContentType( ds.contentType )
        OutputStream out = response.outputStream
        out << is
        out.close() 
    }
}
