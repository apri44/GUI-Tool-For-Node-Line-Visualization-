
package com.mycompany.verys1mple;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Load {
    
    private String path;
    int counter;
    int name;
    int x;
    int y;
    int weight;
    
    public Load() throws IOException {
        
        String username = System.getProperty("user.name");
        
        if (System.getProperty("os.name").startsWith("Windows")){
           path = "C:\\users\\".concat(username).concat("\\Desktop\\yooo.txt");
        }
        else {
            path = "/home/".concat(username).concat("/Desktop");
        }
        
        File fileSave = new File(path);
        
        //Initialize --- Hazırla
        Main.nodeList.clear();
        Main.panel.removeAll();
        Main.panel.repaint();
        Main.nodesCount = 0;
        Line.lineList.clear();
        
        System.out.println("------------Reading Starts------------");
        
        Scanner sc = new Scanner(fileSave);
        counter = Integer.parseInt(sc.nextLine());
        System.out.println("Counter: " + counter);
        
        while (sc.hasNextInt()) {
            name = sc.nextInt();
            x = sc.nextInt();
            y = sc.nextInt();
            
            Main.createNode(name, x, y);
            System.out.format("Name: %d CoorX: %d CoorY: %d\n", name, x, y);
            
            sc.nextLine();
        }
        
        sc.nextLine();
        System.out.println("<---TEST--->"); //TEST (Line Specs Reading Phase)

        while (sc.hasNextInt()) {
            x = sc.nextInt();
            y = sc.nextInt();
            weight = sc.nextInt();
            
            Line.load(Integer.toString(x), Integer.toString(y), weight);
            sc.nextLine();
        }
        
        sc.close();
        Main.nodesCount = counter; 
    }
}
