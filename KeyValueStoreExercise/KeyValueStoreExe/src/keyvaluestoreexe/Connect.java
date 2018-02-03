/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyvaluestoreexe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

/**
 *
 * @author philliphbrink
 */
class Connect  {
    
    RandomAccessFile rafDb;
    HashMap<String, Long> hash;
    //Uses or Create Directory
    File database = new File(System.getProperty("user.dir")+"/Database");
    File hashmap = new File(System.getProperty("user.dir")+"/HashMap");
   
    //Consructor Create the database
    public Connect () throws FileNotFoundException, IOException{
        rafDb = new RandomAccessFile(database, "rw");
        hash = getHashMap();
    }
    
    // Appends binary data to the DB and store location in hashmap
    public void write (String key, String value) throws IOException {
        rafDb.seek(database.length());
        String data = key+","+value;
        byte[] infoBin = data.getBytes("UTF-8");
        String byteData = "";
        for (byte b : infoBin) {
            byteData += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0').concat(" ");
        }
        hash.put(key,rafDb.getFilePointer());
        rafDb.writeBytes(byteData);
        rafDb.writeBytes(System.getProperty("line.separator"));
        saveHashMap(hash);
    } 
   
    //returns the value by location
    public String read (String key) throws IOException{
        rafDb.seek(hash.get(key));
        String currentLine = rafDb.readLine();
        String[] lines = currentLine.split("\\s+");
        String data = new String();
        for (String line : lines) {
            data += (char) Integer.parseInt(line, 2);
        }
        return data.replaceAll(".*,", "");
    }

    // Looks if hashmap exists and uses it or creates a new
    private HashMap<String, Long> getHashMap() throws FileNotFoundException, IOException {
        if (hashmap.exists()){
            RandomAccessFile rafHm = new RandomAccessFile(hashmap, "rw");
            String data = rafHm.readLine();
            String[] hashData = data.split(",\\s+");
            HashMap<String, Long> map = new HashMap<>();
            for (String string : hashData) {
                String[] index = string.split("=");
                map.put(index[0], Long.parseLong(index[1]));
            }
            return map;
        } else {
            return new HashMap<>();
        }
        
    }

    //Store the hashmap in a file
    private void saveHashMap(HashMap<String, Long> hash) throws FileNotFoundException, IOException {
            RandomAccessFile rafHm = new RandomAccessFile(hashmap, "rw");
            String data = hash.toString();
            rafHm.writeBytes(data.substring(1, data.length()-1));
    }

}
