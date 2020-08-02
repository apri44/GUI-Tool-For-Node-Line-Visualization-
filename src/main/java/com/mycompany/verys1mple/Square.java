
package com.mycompany.verys1mple;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Square extends JPanel {
        
    public Square() {
        
        this.setLayout(null);
        
        //Color Procedure --- Renk Prosedürü
        Random colorGen = new Random();
        int color = colorGen.nextInt();
        if (color % 5 == 0) {
            setBackground(Color.CYAN);
        }
        
        else if(color % 5 == 1) {
            setBackground(Color.RED);
        }
        
        else if(color % 5 == 2) {
            setBackground(Color.BLUE);
        }
        
        else if(color % 5 == 3) {
            setBackground(Color.YELLOW);
        }
        
        else {
            setBackground(Color.GREEN);
        }
                
        //Name Procedure --- İsim Prosedürü
        String ID;
        ID = Integer.toString(Main.nodesCount);
        setName(ID);
        
        //Miscellaneous --- Diğer
        setSize(58, 58);
        setLocation(0,0);
        
        //Print Number of Panel With JLabel --- Panelin Numarasını JLabel İle Yazdır
        JLabel nameLabel = new JLabel();
        nameLabel.setText(ID);
        nameLabel.setSize(58, 58);
        nameLabel.setFont(new Font("Arial Black", Font.PLAIN, 34));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(nameLabel);
    }
    
    //Constructor For Load Class --- Load Sınıfı İçin Kurucu
    public Square(int name, int x, int y) {
        this();
        this.setName(Integer.toString(name));
        this.setLocation(x, y);
    }
}
