/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcomparemd5_sha1_sha256;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Анатолий
 */
public class HASHCompareMD5_SHA1_SHA256 {

    private static final Logger log = Logger.getLogger(HASHCompareMD5_SHA1_SHA256.class.getName());
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            log.log(Level.INFO, "Input file: " + args[1] + args[0]);
            File file = new File(args[1] + args[0]);
            String[] fileNameArray = args[0].split(" ");
            if(file.exists()){
                if (fileNameArray.length ==3){
                    Set<String> hashTypeSet = new HashSet<>();
                    hashTypeSet.add("MD5");
                    hashTypeSet.add("SHA1");
                    hashTypeSet.add("SHA256");
                    if(hashTypeSet.contains(fileNameArray[1].toUpperCase()))
                    {
                        MessageDigest digest = MessageDigest.getInstance(fileNameArray[1].toUpperCase());
                        if (fileNameArray[2].toUpperCase().equals(getFileChecksum(digest, file).toUpperCase())){
                            log.log(Level.INFO, fileNameArray[0] + " OK");
                        }
                        else{
                            log.log(Level.INFO, fileNameArray[0] + " FAIL");
                        }
                    }
                    else{
                        log.log(Level.INFO, "The correct entrance hash type: MD5, SHA1, SHA256" );
                    }
                }
                else{
                    log.log(Level.INFO, "The correct entrance fileName example: file_01.bin md5 aaeab83fcc93cd3ab003fa8bfd8d8906");                    
                }
            }
            else{
                log.log(Level.INFO, fileNameArray[0] + " NOT FOUND");
            }
        }
        catch(ArrayIndexOutOfBoundsException ex){
            log.log(Level.INFO, "The correct entrance format : <path to the input file> <path to the directory containing the files to check>");
        }
        catch(Exception ex){
            log.log(Level.SEVERE, ex.getMessage());
        }
    }
    
    private static String  getFileChecksum(MessageDigest digest, File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];
        int bytesCount = 0; 
        
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };
        fis.close();
     
        byte[] bytes = digest.digest();
     
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
