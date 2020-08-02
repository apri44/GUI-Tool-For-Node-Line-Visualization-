/*
apri44
06/06/2020 13.00
*/

package com.mycompany.verys1mple;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class Main extends JFrame implements ActionListener {
    
    private static final DragListener drag = new DragListener();
    public static JPanel panel;
    private final JPanel leftPanel;
    private final JPanel leftBottomPanel;
    private final JPanel leftTopPanel;
    private final JPanel leftCenterPanel;
    private final JButton button;
    private final JButton uselessButton;
    private final JButton buttonSave;
    private final JButton buttonLoad;
    static JLabel leftLabel;
    private static JLabel statsLabel;
    private static JLabel totWeightLabel;
    static int nodesCount = 0;
    
    //Node Objects (JPanel) Will Be Added To This --- Düğümlerin Ekleneceği Liste
    public static ArrayList<Square> nodeList = new ArrayList();
    
    //Constructor --- Kurucu
    public Main() {
        super("Final Re.");
        setLayout(new BorderLayout());
        
        //Left Panel and It's Child Panels --- Sol Panel ve Barındırdığı Alt Panelleri
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);
        this.add(leftPanel, BorderLayout.LINE_START);
        
            //leftBottomPanel  (Create ve Clear Butonlarını Barındırır)
            leftBottomPanel = new JPanel();
            leftBottomPanel.setLayout(new BoxLayout(leftBottomPanel, BoxLayout.X_AXIS));
            leftBottomPanel.setBackground(Color.GREEN);
            leftPanel.add(leftBottomPanel, BorderLayout.PAGE_END);
            
            //leftTopPanel  (Save ve Load Butonlarını Barındırır)
            leftTopPanel = new JPanel();
            leftTopPanel.setLayout(new BoxLayout(leftTopPanel, BoxLayout.X_AXIS));
            leftTopPanel.setBackground(Color.LIGHT_GRAY);
            leftPanel.add(leftTopPanel, BorderLayout.PAGE_START);
            
            //leftCenterPanel   (Tüm JLabel Komponentlerini Barındırır)
            leftCenterPanel = new JPanel();
            leftCenterPanel.setLayout(new BoxLayout(leftCenterPanel, BoxLayout.Y_AXIS));
            leftCenterPanel.setBackground(Color.LIGHT_GRAY);
            leftPanel.add(leftCenterPanel, BorderLayout.CENTER);
        
        //Board Panel --- Ana Panel
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.panel.setBackground(Color.WHITE);
        this.add(panel, BorderLayout.CENTER);
        
        //leftBottomPanel COMPONENTS--------------------
            //"Create" Button
            button = new JButton("  Create  ");
            leftBottomPanel.add(button);
            button.addActionListener(this);

            //"Clear" Button
            uselessButton = new JButton("    Clear    ");
            leftBottomPanel.add(uselessButton);
            uselessButton.addActionListener(this);
        //-----------------------------------------------
        
        //leftTopPanel COMPONENTS------------------------
            //Save Button
            buttonSave = new JButton("    Save    ");
            leftTopPanel.add(buttonSave);
            buttonSave.addActionListener(this);
            
            //Load Button
            buttonLoad = new JButton("    Load    ");
            leftTopPanel.add(buttonLoad);
            buttonLoad.addActionListener(this);
        //-----------------------------------------------
        
        //leftCenterPanel COMPONENTS---------------------
            //leftLabel
            leftLabel = new JLabel("Info");
            leftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftLabel.setOpaque(true);
            leftLabel.setBackground(Color.LIGHT_GRAY);

            //statsLabel
            statsLabel = new JLabel();
            statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            statsLabel.setOpaque(true);
            statsLabel.setBackground(Color.LIGHT_GRAY);
            
            //totWeightLabel
            totWeightLabel = new JLabel();
            totWeightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            totWeightLabel.setOpaque(true);
            totWeightLabel.setBackground(Color.LIGHT_GRAY);

            leftCenterPanel.add(Box.createVerticalGlue());
            leftCenterPanel.add(leftLabel);
            leftCenterPanel.add(Box.createVerticalGlue());
            leftCenterPanel.add(totWeightLabel);
            leftCenterPanel.add(statsLabel);
        //-----------------------------------------------        
        
        //JFrame Specifications --- JFrame Özellikleri
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    //Button Listener --- Buton Dinleyici
    public void actionPerformed(ActionEvent evt) {
        
        if (evt.getSource() == button) {
            
            //Create Node --- Düğüm Oluştur
            Square square = createNode();
            
            //Add Drag Listener To New Created Node --- Düğüme Sürükleme Dinleyicisi Ekle
            square.addMouseMotionListener( drag );
            square.addMouseListener( drag );
            panel.repaint();
        }
        
        else if (evt.getSource() == uselessButton) {
            
            //Clear The Board --- Ana Paneli Temizle
            panel.removeAll();
            panel.repaint();
            
            //Clear The nodeList --- Düğüm Listesini Temizle
            clearNL();
            System.out.println("\nBoard Was Cleared.\n");
            leftLabel.setText("Board Was Cleared.");
            
            //Update Lines --- Bağlantıları Güncelle
            Line.update();
            
            //Set The Counter To 0 --- Sayacı Sıfırla
            nodesCount = 0;
            stats();
        }
        
        else if (evt.getSource() == buttonSave) {
            try {
                new Save();
                leftLabel.setText("Saved.");
            } catch(Exception e) {}
        }
        
        else if (evt.getSource() == buttonLoad) {
            try {
                new Load();
                leftLabel.setText("Load Success");
                stats();
            } catch (IOException ex) {
                Main.leftLabel.setText("yooo.txt Does not exist");
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    
    Square createNode() {
        Square square = new Square();
        nodesCount++;
        System.out.println(square.getName());
        addNL(square);
        panel.add(square);
        leftLabel.setText(square.getName() + " was created");
        
        stats();
        
        return square;
    }
    
    //Node Creator For Load Class --- Load Sınıfı İçin Düğüm Oluşturucu
    static void createNode(int name, int x, int y) {
        Square square = new Square(name, x, y);
        nodesCount++;
        System.out.println(square.getName());
        addNL(square);
        panel.add(square);
        leftLabel.setText(square.getName() + " was created");
        
        //Add Mouse Listeners
        square.addMouseMotionListener( drag );
        square.addMouseListener( drag );
        
        panel.repaint();
        stats();
    }
    
    static void removeNode(String name) {
        //Match the name with node --- Komponent ismini düğüm ile eşleştir
        for (Square node : nodeList) {
            if (node.getName() == name) {
                
                removeNL(node);
                panel.remove(node);
                leftLabel.setText(name + " was removed.");
                panel.repaint();
                stats();
                break;
            }
        }
    }
    
    //Add Line Components --- Bağlantı Komponentleri Ekle
    static void connectLine(Component comp1, Component comp2, int weight) {
     
    //Check If Line is Already Drawn, By Name (x<y  --->  x:y) --- Bağlantının Varlığını Kontrol Et
        Boolean flag = false;
        
        int i = Integer.parseInt( comp1.getName() );
        int j = Integer.parseInt( comp2.getName() );
        if (i > j) {
            String name = j + ":" + i; 
            for (Line l : Line.lineList) {
                
                if ( l.getName().equals(name) ) {
                    flag = true;
                }
            }
        }
        
        else {
            String name = i + ":" + j;
            for (Line l : Line.lineList) {
                
                if ( l.getName().equals(name) ) {
                    flag = true;
                }
            }
        }
        
        if (flag == true) {
            leftLabel.setText("Nodes Are Already Connected.");
            return;
        }
    //-----------------------------------------------------------
        
        Line line = new Line(comp1, comp2, weight);
        line.setSize(line.getPreferredSize()); //This must be invoked if panel layout is null! --- Layout null ise bu fonksiyon çağrılmalı!
        panel.add(line);
        panel.repaint();
        stats();
    }
      
    //List Functions --- Liste Fonksiyonları
    static void addNL(Square item) {
        nodeList.add(item);
    }

    static void removeNL(Square item) {
        nodeList.remove(item);
        item.setName("null");
    }

    void clearNL() {
        nodeList.clear();
    }
    
    static void stats() {
        int totalWeight = 0;
        for (Line l : Line.lineList)
            totalWeight = totalWeight + l.weight;
        statsLabel.setText("Nodes: " + nodeList.size() + "  Lines: " + Line.lineList.size());
        totWeightLabel.setText("Total Weight: " + totalWeight);
    }
    
    public static void main(String[] args) {
        new Main();
        stats();
    }
}