
package com.mycompany.verys1mple;
  
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList; 
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

  
public class Line extends JComponent {
    
    static ArrayList<Line> lineList = new ArrayList<>();
    private String name;
    private Point start, end;
    private Line2D.Double line;
    final Component c1, c2;
    int weight;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Set  Anti-Aliasing --- Kenar Yumuşatmayı Aç
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON); 
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3.0F));
        g2.draw(new Line2D.Double(start, end));
    }
    
    @Override
    public Dimension getPreferredSize() {
        Rectangle bounds = line.getBounds();

        int width = bounds.x + bounds.width;
        int height = bounds.y + bounds.height;

        return new Dimension(width, height);
    }

    
    @Override
    public boolean contains(int x, int y)
    {
        double distance = line.ptSegDist( new Point2D.Double(x, y) );

        return distance < 5;
    }
    
    //Constructor For a New Line --- Yeni Bağlantı İçin Kurucu
    public Line(Component node1, Component node2, int weight) {
        
        //Assign Coordinates as an Instance Attribute --- Koordinatları Line Özelliği Olarak Ata
        start = node1.getLocation();
        end = node2.getLocation();
        
        //Assign Components To Object As An Instance Attribute --- Komponentleri Line Özelliği Olarak Ata
        this.weight = weight;
        this.c1 = node1;
        this.c2 = node2;
        
        //Add 28 To Both X and Y (To Make Lines Drawn From Centers of Panels)
        start.setLocation(start.getX() +28, start.getY() + 28);
        end.setLocation(end.getX() + 28, end.getY() + 28);
        
        line = new Line2D.Double(start, end);

        //Add Mouse Listener To Remove By Right-Click --- Sağ-Tık İle Silmek İçin Fare Dinleyicisi Ata.
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(SwingUtilities.isRightMouseButton(e)) {
                    removeLine(e.getComponent());
                }
                
                else {
                    for (Line l : lineList)
                        if (e.getComponent().getName().equals(l.getName()) ) {
                            Main.leftLabel.setText("Çizgi " + e.getComponent().getName() + "  Weight: " + l.weight);
                        }
                }
            }
        });
        
        
        //Name Procedure Of The Line, x:y  --->  x < y --- İsimlendirme Prosedürü
        int from = Integer.parseInt(node1.getName());
        int to = Integer.parseInt(node2.getName());
        
        if (to > from) {
            this.name = from + ":" + to;
        }

        else {
            this.name = to + ":" + from;
        }
        
        //Set The Name --- İsimlendir
        this.setName(name);
        
        //Add The Line Object To List --- Listeye Ekle
        lineList.add(this);
    }
    
    void removeLine(Component remove) {
        lineList.remove(remove);
        Main.panel.remove(remove);
        Main.panel.repaint(); //Not redundant --- Gereksiz değil
        
        //Inform The User --- Kullanıcıyı Bilgilendir
        Main.leftLabel.setText("Line " + remove.getName() + " was removed.");
        Main.stats();
    } 
    
    
    static void update() {
        ArrayList<Line> copy = new ArrayList<>(lineList);
        lineList.clear();
        
        for (Line temp : copy) {
            Main.panel.remove(temp);
        }
        
        for (Line temp : copy) {
            if (Main.nodeList.contains(temp.c1) && Main.nodeList.contains(temp.c2)) { //Don't Connect If Node No Longer Exists. --- Düğüm Artık Yoksa Bağlama.
                Main.connectLine(temp.c1, temp.c2, temp.weight);
            }
        }
        Main.stats();
    }
    
    //Function For Load Class --- Load Sınıfı İçin Fonksiyon
    static void load(String a, String b, int weight) {
        Component cp1 = null;
        Component cp2 = null;
        
        for (Square sq : Main.nodeList) {
            if (sq.getName().equals(a)) {
                cp1 = sq;
            }
            
            if (sq.getName().equals(b)) {
                cp2 = sq;
            }
        }
        
        if (cp1 != null && cp2 != null) {
            Main.connectLine(cp1, cp2, weight);
        }
    }
}
