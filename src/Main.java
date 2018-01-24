import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import com.fazecast.jSerialComm.SerialPort;

public class Main implements ActionListener{
    Graph gr;
    JSlider vSlide;
    JSlider hSlide;
    JSlider nSlide;

    JButton toggleD1, toggleD2;
    public static void main(String[] args) {
        Main m = new Main();
        m.go();
        
    }
    public void go(){
        System.out.println("Starting up");
        Timer clock = new Timer(30, this);
        JFrame frame = new JFrame("Frame");
        JPanel pane = new JPanel();
        frame.add(pane);
        gr = new Graph(1000, 500, 1000);
        
        
        pane.setLayout(new BorderLayout());
        pane.add(gr, BorderLayout.CENTER);
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());
        pane.add(controls, BorderLayout.SOUTH);
        hSlide = new JSlider();
        vSlide = new JSlider();
        nSlide = new JSlider();
        
        
        controls.add(hSlide);
        controls.add(vSlide);
        controls.add(nSlide);
        
        vSlide.setMaximum(50);
        vSlide.setMinimum(1);
        vSlide.setValue((int)gr.getYScale());
        vSlide.setMajorTickSpacing(5);
        hSlide.setMaximum(20);
        hSlide.setMinimum(1);
        hSlide.setValue((int)gr.getTimeScale());
        hSlide.setMajorTickSpacing(5);
        nSlide.setMaximum(1000);
        nSlide.setMinimum(10);
        nSlide.setValue((int)gr.getNumPoints());
        nSlide.setMajorTickSpacing(50);
        
        
        
        
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clock.start();
        
        System.out.println("Getting ports...");
        SerialPort ports[] = SerialPort.getCommPorts();
        System.out.println("Select a port");
        int i = 0;
        for (SerialPort p : ports){
            System.out.println(i + ") " + p.getDescriptivePortName());
        }
        
        Scanner s = new Scanner(System.in);
        SerialPort p = ports[s.nextInt()];
        
        while (!p.openPort()){
            System.err.println("Unable to open port " + p.getSystemPortName() + " = " + p.getDescriptivePortName());
        }
        
        System.out.println("Successfully opened port!");
        
        p.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        p.setBaudRate(9600);
        Scanner data = new Scanner(p.getInputStream());

        i = 0;
        
        

        int d1, d2;
        while(true){
            i = 0;
            while(!data.hasNextLine()) {System.out.println("Waiting...");}
            try{
                String str = data.nextLine();
                d1 = Integer.parseInt(str.substring(0, str.indexOf(" ")));

                d2 = Integer.parseInt(str.substring(str.indexOf(" ")+1));
                System.out.println(str + "-> " + str.substring(0, str.indexOf(" ")) + " / " + 
                                   str.substring(str.indexOf(" ")) + " -> (" + d1 + ", " + d2 + ")");
                gr.addPoint((float)(d1*5.0/1023.0), (float)(d2*5.0/1023.0));  
                
            } 
            catch(Exception e){
                System.err.println("Something went wrong...");
                e.printStackTrace();
            }
        }
        
        
     
    }

    
    public void actionPerformed(ActionEvent e) {
        gr.repaint();
//        gr.setTimeScale(hSlide.getValue());
        gr.setYScale(vSlide.getValue());
        gr.setNumPoints(nSlide.getValue());
    }

}
