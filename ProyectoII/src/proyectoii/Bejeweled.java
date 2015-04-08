/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoii;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class Bejeweled extends JFrame implements WindowListener 

{
    private Container container = this.getContentPane();
    private JMenuBar menubar = new JMenuBar();
    private JButton startb = new JButton("Comienza Inteligencia Artificial.");
    
    public Bejeweled()
    
    {
        
        super("Bejeweled");
        this.setJMenuBar(menubar);
        this.setLayout(new GridLayout(3,1));
        container.add(startb);
        addWindowListener(this);
        setLocationRelativeTo(null);
        this.setPreferredSize(new Dimension(300,300));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        startb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Tablero tabla = new Tablero(8,8);
                tabla.template(Bejeweled.this);
            }
        });
    
        this.pack();
        this.setVisible(true);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        int res = JOptionPane.showConfirmDialog(null,"Are you sure you want to close the application?","Exit?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(res==JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
    
}

