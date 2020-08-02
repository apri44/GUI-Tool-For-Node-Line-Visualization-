
package com.mycompany.verys1mple;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import javax.swing.SwingUtilities;
import java.util.Timer;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

public class DragListener extends MouseInputAdapter
{
    Point location;
    MouseEvent pressed;
    Component remove;
    Boolean isAlreadyOneClick;
    Timer timer;
    Component comp1;
    Component comp2;

    public void mousePressed(MouseEvent me)
    {
        pressed = me;
    }
    
    public void mouseClicked(MouseEvent me) {
        
        //Remove Action With Right Click --- Sağ Tıkla Silme Aksiyonu
        if (SwingUtilities.isRightMouseButton(me) == true) {
            remove = me.getComponent();
            
            //Remove The Node --- Düğümü Sil
            Main.removeNode(remove.getName());
            
            //Update Lines --- Bağlantıları Güncelle
            Line.update();
        }
        
        //Line Draw Action With 2x Middle Click --- Çift Tekerlek Tıklaması İle Bağlantı Çizme Aksiyonu
        else if (SwingUtilities.isMiddleMouseButton(me) == true) {
            
            //Initialize the "isAlreadyOneClick" --- isAlreadyOneClick'i Hazırla
            if (isAlreadyOneClick == null) {
                isAlreadyOneClick = false;
            }
            
            //First Click --- İlk Tıklama
            else if (isAlreadyOneClick == false) {
                comp1  =  me.getComponent();
                isAlreadyOneClick = true;
                System.out.println("isAlreadyOneClick is now true");
                Main.leftLabel.setText("Now Select Another Node");

                //Create Timer --- Zamanlayıcı Oluştur
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isAlreadyOneClick = false;
                        System.out.println("isAlreadyOneClick is now false");
                        Main.leftLabel.setText("Selection Failed");
                        timer.cancel();
                        comp1 = null;
                    }
                }, 5000);
            }
            
            //Second Click --- İkinci Tıklama
            else if (isAlreadyOneClick == true) {
                isAlreadyOneClick = false;
                timer.cancel();
                comp2  = me.getComponent();
                
                if(comp1 != comp2) { //Check Whether User Clicked On Same Node Twice --- Kullanıcının Aynı Düğüme Tıklama Durumunu Kontrol Et
                    
                    //Pop Option Pane --- Option Pane Oluştur
                    String dialog;
                    int weight;
                    dialog = JOptionPane.showInputDialog("Weight of Line: ");
                    try { 
                        weight = Integer.parseInt(dialog); } 
                    catch(NumberFormatException e) {
                        weight = 0;
                        java.awt.Toolkit.getDefaultToolkit().beep();
                    }
                    
                    System.out.println(comp1.getName() + " and " + comp2.getName() + " are selected.");
                    Main.leftLabel.setText(comp1.getName() + " and " + comp2.getName() + " are selected");
                    Main.connectLine(comp1, comp2, weight);
                }
                
                else {
                    comp1 = comp2 = null;
                }
            }
        } 
    }

    public void mouseDragged(MouseEvent me)
    {
        if (SwingUtilities.isLeftMouseButton(me) == true) {
            
            Component component = me.getComponent();
            location = component.getLocation(location);
            int x = location.x - pressed.getX() + me.getX();
            int y = location.y - pressed.getY() + me.getY();
            component.setLocation(x, y);
            
            Line.update();
        }
    }
}