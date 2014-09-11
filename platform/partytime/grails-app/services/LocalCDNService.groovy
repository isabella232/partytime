import com.favoritemedium.partytime.*


class LocalCDNService implements ICDNProvider {

    boolean transactional = true

    public String save(Object obj) {
        println " [LocalCDNService]  Saving ${obj}"
        return null
    }

    public boolean delete(Object obj) {
        println " [LocalCDNService]  Deleting ${obj}"
        return false
    }

    public String update(Object obj) {
        println "  [LocalCDNService]  Updating ${obj}"
        return null
    }

    public Object get(String key) {
        println "  [LocalCDNService]  get ${key}"
        return null
    }

    public boolean rename(String key, String newName, String oldName=null) {
        println "  [LocalCDNService]  renaming ${key}"
        return false
    }

}
