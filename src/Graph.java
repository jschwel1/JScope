import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Graph extends JPanel{
    
    int maxPoints;
    ArrayList<Float> d1List;
    ArrayList<Float> d2List;
    ArrayList<Long> timeList;
    long lastTime;
    float timeScale, yScale;
    
    final static Color c1 = new Color((float)0.0,(float)0.0,(float)0.0,(float)0.5);
    final static Color c2 = new Color((float)0.2,(float)0.9,(float)0.2,(float)0.5);
    boolean toggleD1, toggleD2;
    
    public Graph(int w, int h, int l){
        this.setSize(new Dimension(w, h));
        d1List = new ArrayList<Float>();
        d2List = new ArrayList<Float>();
        for (int i = 0; i < l; i++){
            d1List.add((float)0);
            d2List.add((float)0);
        }
        toggleD1 = true;
        toggleD2 = true;
        maxPoints = l;
        timeScale = w/l;
        yScale = 1;
        lastTime=System.currentTimeMillis();
    }
    
    void addPoint(float d1, float d2){
        
        if (d1List.size() > maxPoints){
            d1List.remove(0);
            d2List.remove(0);
            timeList.remove(0);
            for(long t: timeList){
               t = t-timeList.get(0); 
            }
        }
        d1List.add(d1);
        d2List.add(d2);
        timeList.add(System.currentTimeMillis());
//        System.out.println("NEW POINTS: " + d1 + " " + d2);
    }
    
    public void setTimeScale(float ts){
//        timeScale = ts;
    }
    public float getTimeScale(){
        return timeScale;
    }
    public void setYScale(float ts){
        yScale = ts;
    }
    public float getYScale(){
        return yScale;
    }
    public void setNumPoints(int p){
//        if (p > maxPoints){
//            for (int i = maxPoints; i < p; i++){
//                d1List.add(0, (float)0);
//                d2List.add(0, (float)0);
//            }
//        }
        d1List.clear();
        d2List.clear();
        for (int i = 0; i < p; i++){
          d1List.add((float)0);
          d2List.add((float)0);
      }
        maxPoints = p;

        timeScale = (float)this.getWidth()/(float)p;
        
        
        System.out.println("y - " + yScale + ", t - " + timeScale + ", maxPoints - " + maxPoints);
    }
    public int getNumPoints(){
        return maxPoints;
    }
    
    public boolean toggleD1(){
        toggleD1 = !toggleD1;
        return toggleD1;
    }
    public boolean toggleD2(){
        toggleD2 = !toggleD2;
        return toggleD2;
    }
    
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        int offsetV = this.getHeight()/2;
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());
        
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.orange);
        g2.drawLine(0, offsetV, this.getWidth(), offsetV);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(0, 0, this.getWidth(), this.getHeight());
        for(int i = 0; i < maxPoints-1; i++){
            if (toggleD1){
//                System.out.println("PAINING D1");
                g2.setColor(c1);
                g2.drawLine((int)(i*timeScale), (int)(((-d1List.get(i)*yScale)+offsetV)), 
                            (int)(i*timeScale), (int)(((-d1List.get(i+1)*yScale)+offsetV)));
            }
            if (toggleD2){
//                System.out.println("PAINING D2");
                g2.setColor(c2);
                g2.drawLine((int)(i*timeScale), (int)(((-d2List.get(i)*yScale)+offsetV)), 
                            (int)(i*timeScale), (int)(((-d2List.get(i+1)*yScale)+offsetV)));
            }
            
        }
        
    }
}
