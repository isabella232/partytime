import groovy.net.xmlrpc.*

String DEFAULT_URL = "http://localhost:8080/pebble/xmlrpc"

def server = new XMLRPCServerProxy(DEFAULT_URL)
def pebble = server.pebble

println " --- "

println pebble.addBlog("test-blog-1", "sey", [:])

