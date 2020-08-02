//Directory: Desktop/yooo.txt (Platform independent)

package com.mycompany.verys1mple;

import java.io.FileWriter;
import java.io.IOException;

public class Save {
    private String path;
    
    public Save() throws IOException {
        String username = System.getProperty("user.name"); //Kullanıcı Adını Al
        
        //Dizini Belirle
        if (System.getProperty("os.name").startsWith("Windows")){
           path = "C:\\users\\".concat(username).concat("\\Desktop\\yooo.txt");
        }
        else {
            path = "/home/".concat(username).concat("/Desktop");
        }
        
        FileWriter writer = new FileWriter(path);
        
        //Starts Writing --- Yazmaya Başla
        writer.write(Main.nodesCount + "\n");
        
        for (Square node : Main.nodeList) {
            writer.write(node.getName() + " " + node.getX() + " " + node.getY() + "\n");
        }
        
        writer.write("---\n");
        for (Line line : Line.lineList) {
            writer.write(line.c1.getName() + " " + line.c2.getName() + " " + line.weight + "\n");
        }
        writer.close();
    }
}
