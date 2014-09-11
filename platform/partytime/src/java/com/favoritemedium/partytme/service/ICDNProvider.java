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

package com.favoritemedium.partytime;

/**
 * CDNProvider class interface.
 * This interace outlines the basic workings of saving, retriving and deleting 
 * of media objects with the CDN provider.
 *
 */
public interface ICDNProvider {


    /**
     * Retrieve the object saved in CDN by using the key identifier.
     *
     * @param key identifier for the object saved in CDN
     * @return object with the given key identifier
     */
    public Object get(String key);

    /** 
     * To save the object to CDN provider.
     * On successful save, the CDN should return back the identifier key
     * to the saved media object. 
     *
     * @param obj
     * @return String identifier
     */
    public String save(Object obj);

    /**
     * To delete the object saved in CDN provider.
     * 
     * @param obj
     * @return true if object deleted, false otherwise
     */
    public boolean delete(Object obj);

    /**
     * To update the object saved in CDN.
     * 
     * @param obj
     * @return String identifier
     */
    public String update(Object object);
    
    /**
     * To rename the objects saved in CDN.
     * This operation is specifically for renaming file or media 
     * object saved in CDN.
     *
     * @param key identifier for the object in CDN
     * @param newName new name for the object
     * @param optional param for the old name -- mainly to validate
     * @return true if object deleted, false otherwise
     */
    public boolean rename(String key, String newName, String oldName);

}
