/*
*
* @author Asri Jaffar
*/

class YahooTagLib {
	static namespace = "yahoo"
	
	def login = {
		out << link(controller:'yahoo', action:'login') {'Login with Yahoo!'}
	}
	        
}