/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyvaluestoreexe;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author philliphbrink
 */
public class main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    // Write
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Connect connect = new Connect();
        if (args.length >= 2){
            connect.write(args[0], args[1]);
        } else {
            System.out.println(connect.read(args[0]));
        }
    }
}
