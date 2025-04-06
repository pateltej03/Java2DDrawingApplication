/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
@author: Tej Jaideep Patel, tfp5304@psu.edu , 975793441
**/

public class DrawingApplicationFrame extends JFrame {
    
    // Creating Panels
        public JPanel toppanel;
        public JPanel topP1;
        public JPanel topP2;

    // top1 widgets 
        public JLabel shapeL;
        public JComboBox shapesComB;
        public JButton color1B;
        public JButton color2B;
        public JColorChooser colorchooser;
        public JButton undoB;
        public JButton clearB;
        
    // top2 widgets
        public JLabel optionsL;
        public JCheckBox filledCheckB;
        public JCheckBox gradientCheckB;
        public JCheckBox dashedCheckB;
        public JLabel widthL;
        public JSpinner widthSp;
        public JLabel lengthL;
        public JSpinner lengthSp;

    // Variables for drawPanel.
        public ArrayList<MyShapes> shapesarray;
        public Point startP;
        public Point endP;
        public DrawPanel drawP;
        public Color DPcolor1;
        public Color DPcolor2;
        public Paint DPpaint;
        public Stroke DPstroke;
        public Boolean DPfilled;
        public MyShapes currentS;

    // Status label
        public JLabel statusL;
  
    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame()
    {
        setTitle("Java 2D Drawings");
        setLayout(new BorderLayout());
    
        // TOPPANEL DEFINING /////////////////////////////////////////////////////
        
        toppanel = new JPanel();
        toppanel.setLayout(new GridLayout(2,1));
        add(toppanel,BorderLayout.NORTH);
        
        // TOPPANEL DEFINING ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
       
        
        // TOPP1 WIDGETS AND HANDLERS /////////////////////////////////////////////////////
        
        shapeL = new JLabel("Shape:");
        String shapearraystrings[] = {"Line","Oval","Rectangle"};
        shapesComB = new JComboBox(shapearraystrings);
        color1B = new JButton("1st Color...");
        color2B = new JButton("2nd Color...");
        undoB = new JButton("Undo");
        clearB = new JButton("Clear");
        
        topP1 = new JPanel();
        topP1.setLayout(new FlowLayout());
        topP1.add(shapeL);
        topP1.add(shapesComB);
        topP1.add(color1B);
        topP1.add(color2B);
        topP1.add(undoB);
        topP1.add(clearB);
        topP1.setBackground(Color.decode("#A0FFEF"));
        toppanel.add(topP1);
        
        color1B.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        DPcolor1 = JColorChooser.showDialog(toppanel, "Choose Color 1", DPcolor1);
                        if (DPcolor1 == null)
                            DPcolor1 = Color.RED;
                    }
                }
        );
        
        color2B.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        DPcolor2 = JColorChooser.showDialog(toppanel, "Choose Color 2", DPcolor2);
                        if (DPcolor2 == null)
                            DPcolor2 = Color.BLUE;
                        }
                }
        );
        
        undoB.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        shapesarray.remove(shapesarray.size()-1);
                        repaint();
                    }
                }
        );
        
        clearB.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        shapesarray.clear();
                        repaint();
                    }
                }
        );
        
        // TOPP1 WIDGETS AND HANDLERS ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        
        // TOPP2 WIDGETS AND HANDLERS /////////////////////////////////////////////////////
        
        optionsL = new JLabel("Options:");
        filledCheckB = new JCheckBox("Filled");
        gradientCheckB = new JCheckBox("Use Gradient");
        dashedCheckB = new JCheckBox("Dashed");
        widthL = new JLabel("Line Width:");
        widthSp = new JSpinner(new SpinnerNumberModel(1,1,99,1));
        lengthL = new JLabel("Dash Length:");
        lengthSp = new JSpinner(new SpinnerNumberModel(1,1,99,1));
        
        topP2 = new JPanel();
        topP2.setLayout(new FlowLayout());
        topP2.add(optionsL);
        topP2.add(filledCheckB);
        topP2.add(gradientCheckB);
        topP2.add(dashedCheckB);
        topP2.add(widthL);
        topP2.add(widthSp);
        topP2.add(lengthL);
        topP2.add(lengthSp);
        topP2.setBackground(Color.decode("#A0FFEF"));
        toppanel.add(topP2);
        
        // TOPP2 WIDGETS AND HANDLERS ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        
        // DRAWP WIDGETS AND HANDLERS /////////////////////////////////////////////////////
        
        drawP = new DrawPanel();
        DPcolor1 = Color.RED;
        DPcolor2 = Color.BLUE;
        shapesarray = new ArrayList();
        startP = new Point();
        endP = new Point();
        add(drawP,BorderLayout.CENTER);
        
        // DRAWP WIDGETS AND HANDLERS ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        
        // STATUSL WIDGETS AND HANDLERS /////////////////////////////////////////////////////
        
        statusL = new JLabel();
        statusL.setText("(,)");
        add(statusL,BorderLayout.SOUTH);
        
        // STATUSL WIDGETS AND HANDLERS ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        
    }
    //DrawPanel class
    public class DrawPanel extends JPanel
    {
        // DrawPanel constructor
        public DrawPanel()
        {
            MouseHandler MH = new MouseHandler();
            addMouseListener(MH);
            addMouseMotionListener(MH);
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //loop through and draw each shape in the shapes arraylist
            for (MyShapes shape : shapesarray){
                shape.draw(g2d);
            }

        }
        
        public MyShapes makecurrentS() {
            String shape = (String) shapesComB.getSelectedItem();
            float[] dashA = {(float) (int) lengthSp.getValue()};
            
            if (filledCheckB.isSelected()) {
                DPfilled = true;
            }
            else {
                DPfilled = false;
            }
            
            if (gradientCheckB.isSelected()) {
                DPpaint = new GradientPaint(0, 0, DPcolor1, 50, 50, DPcolor2, true);
            }
            else {
                DPpaint = DPcolor1;
            }
            
            if (dashedCheckB.isSelected()) {
                DPstroke = new BasicStroke((float) (int)widthSp.getValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashA, 0);
            }
            else {
                DPstroke = new BasicStroke((float) (int)widthSp.getValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            }
            
            if (shape.equals("Line")){
                currentS = new MyLine(startP,endP, DPpaint, DPstroke);
            }
            else if (shape.equals("Oval")){
                currentS = new MyOval(startP,endP, DPpaint, DPstroke, DPfilled);
            }
            else if (shape.equals("Rectangle")){
                currentS = new MyRectangle(startP,endP, DPpaint, DPstroke, DPfilled);
            }
            
            return currentS;
        }


        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {

            public void mousePressed(MouseEvent event)
            {
                startP = event.getPoint();
                endP = event.getPoint();
                makecurrentS();
                shapesarray.add(currentS);
                repaint();
            }

            public void mouseReleased(MouseEvent event)
            {
                shapesarray.get(shapesarray.size()-1).setEndPoint(event.getPoint());
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {   
                shapesarray.get(shapesarray.size()-1).setEndPoint(event.getPoint());
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                statusL.setText(String.format("(%d,%d)", event.getX(), event.getY()));
            }
        }

    }
}
