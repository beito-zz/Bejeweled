
package proyectoii;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tablero extends JFrame implements ActionListener 

{
    
    public Timer timer;
    public Field joyas[][];
    public int columnas;
    public int filas;
    public int score;
    
    public JLabel scorelabel = new JLabel();
    
    public Tablero(int filas, int columnas) 
    
    {
        
        this.score = -1;
        this.columnas = columnas;
        this.filas = filas;
    
        joyas = new Field[filas][columnas];
        
    }
    
    public void template(Bejeweled parent)
    
    {
        
        GridBagConstraints con = new GridBagConstraints();//Crea un objeto GridBagConstraints, para delimitar las rendijas del tablero 
        JPanel area = new JPanel();
        Container contenedor = this.getContentPane();
        
        this.setTitle("Bejeweled");//Nombre
        
        parent.setEnabled(false);//Cuando se muestra el tablero de juego, no se muestra el menu.

        area.setLayout(new GridBagLayout());//Prepara el tablero para recibir los delimitadores.
        
        for (int i = 0; i < filas; i++) 
        
        {
            
            for (int j = 0; j < columnas; j++) 
            
            {
                
                Field field = new Field();
                field.setPreferredSize(new Dimension(90, 90));//Heredado de JPanel: Asigna las dimensiones al Field
                field.setBackground(Color.LIGHT_GRAY);//Heredado de JPanel: Asigna el color de fondo al Field
                
                field.setJewel(new Jewel());
                field.setBorder(BorderFactory.createLineBorder (Color.DARK_GRAY, 2));//Heredado de JPanel: Traza el contorno del Field
                //field.addMouseListener(new FieldMouseListener());//Heredado de JPanel: Le asigna la clase anonima "FieldMouseListener"
                
                joyas[i][j] = field;
                
                con.fill = GridBagConstraints.HORIZONTAL;//Estable un delimitante horizontal (fila).
                con.gridx = j;//Asigna una posicion, en x, al delimitante
                con.gridy = i;//Asigna una posicion, en Y, al delimitante
                area.add(field, con);//Asigna el delimitante al tablero
                
            }
            
        }
        
        contenedor.add(area, BorderLayout.CENTER);//Asigna el tablero a la ventana y la centra.
        
        scorelabel.setText("Score: "+score);//Muestra el score del juego.
        scorelabel.setFont(new Font("Verdana",Font.BOLD,21));//Tipo de fuente del score.
        
        con.fill = GridBagConstraints.HORIZONTAL;//Estable un delimitante horizontal (fila).
        con.gridx = 0;//Asigna una posicion, en x, al delimitante
        con.gridy = 9;//Asigna una posicion, en Y, al delimitante
        con.gridwidth=3;//Establece la anchura del delimitante
        
        area.add(scorelabel, con);//Asigna el delimitante del Score, fuera del tablero.
        
        this.prepararTablero();
        
        score = 0;
        
        this.init();
        
    }
    
    public void prepararTablero()
    
    {
    
        while(this.hayCombinaciones()||!this.estaLleno())
        
        {
            
            this.eliminarCombos();
            
            this.bajar();
            
            this.llenar();
            
        }
        
    }
    
    public boolean hayCombinaciones()
    
    {
        
        for (int i = 0; i < filas; i++) 
        
        {
            
            for (int j = 1; j < (columnas-1); j++) 
            
            {
                
                if ((joyas[i][j].getColor().equals(joyas[i][j-1].getColor()))&&(joyas[i][j].getColor().equals(joyas[i][j+1].getColor())))
                
                {
                    
                    return true;
                    
                }
                
            }
            
        }
        
        for (int i = 1; i < (filas-1); i++) 
        
        {
            
            for (int j = 0; j < columnas; j++) 
            
            {
                
                if ((joyas[i][j].getColor().equals(joyas[i-1][j].getColor()))&&(joyas[i][j].getColor().equals(joyas[i+1][j].getColor())))
                
                {
                    
                    return true;
                    
                }
                
            }
            
        }
        
        return false;
        
    }
    
    public void eliminarCombos()//PreparePlayArea Play Area
    
    {
        
        for (int i = 0; i < filas; i++) 
        
        {
            
            for (int j = 1; j < (columnas-1); j++) 
            
            {
                
                if ((joyas[i][j].getColor().equals(joyas[i][j-1].getColor()))&&(joyas[i][j].getColor().equals(joyas[i][j+1].getColor())))
                
                {
                    
                    joyas[i][j-1].setMarkedForDel(true);
                    joyas[i][j].setMarkedForDel(true);
                    joyas[i][j+1].setMarkedForDel(true);
                    
                }
                
            }
            
        }
        
        for (int i = 1; i < (filas-1); i++) 
        
        {
            
            for (int j = 0; j < columnas; j++) 
            
            {
                
                if ((joyas[i][j].getColor().equals(joyas[i-1][j].getColor()))&&(joyas[i][j].getColor().equals(joyas[i+1][j].getColor())))
                
                {
                    
                    joyas[i-1][j].setMarkedForDel(true);
                    joyas[i][j].setMarkedForDel(true);
                    joyas[i+1][j].setMarkedForDel(true);
                    
                }
                
            }
            
        }
        
        for (int i = 0; i < filas; i++)
        
        {
            
            for (int j = 0; j < columnas; j++) 
            
            {
                
                if(joyas[i][j].isMarkedForDel())
                
                {
                    
                    joyas[i][j].clearJewel();
                    joyas[i][j].setMarkedForDel(false);
                    
                    if (score >= 0) 
                    
                    {
                        
                        score += 10;
                        this.scorelabel.setText("Score: "+score);
                        
                    }
                
                }
                
            }
        
        }
        
        this.repaint();
        
    }
    
    public void bajar()
    
    {
        
        int concurrente;
        boolean disparador;
        
        for (int i = 0; i < columnas; i++)
        
        {
            
            concurrente = (filas-1);
            disparador = false;
            
            for (int j = (filas-1); j >= 0 ; j--) 
            
            {
                
                if(!joyas[j][i].getHasJewel())
                
                {
                    
                    if (!disparador) 
                    
                    {
                        
                        concurrente = j;
                        
                    }
                    
                    disparador = true;
                
                }
                
                else
                
                {
                
                    if (disparador)
                    
                    {
                        
                        joyas[concurrente][i].setJewel(joyas[j][i].getJewel());
                        joyas[j][i].clearJewel();
                        
                        concurrente--;
                                
                    }
                    
                }
                
            }
            
        }
        
        this.repaint();
       
    }

    public void llenar()
    
    {
            
        for (int i = 0; i < filas; i++)
        
        {
            
            for (int j = 0; j < columnas; j++) 
            
            {
            
                if (!joyas[i][j].getHasJewel()) 
                
                {
                    
                    joyas[i][j].setJewel(new Jewel());
                    
                }
                
            }
        
        }
        
    }
    
    public void init()
    
    {
        
        System.out.println(score);
        timer = new Timer(25, this);
        timer.start();
        Toolkit.getDefaultToolkit().sync();
        this.pack();
        this.setLocation(200, 100);
        this.setResizable(false);
        this.setVisible(true);
                
    }
    
    public boolean estaLleno()//Si el tablero esta lleno: True
    
    {
                
        for (int i = 0; i < filas; i++)
        
        {
            
            for (int j = 0; j < columnas; j++) 
            
            {
            
                if (!joyas[i][j].getHasJewel()) 
                
                {
                    
                    return false;
                    
                }
                
            }
        
        }
        
        return true;
        
    }
    
    public boolean posiblesCombos()
    
    {
        
        for (int i = 0; i < filas; i++) 
        
        {
            
            for (int j = 0; j < (columnas-2); j++) 
            
            {
                
                if (this.movimientosHorizontales(i,j)) 
                
                {
                    
                    return true;
                    
                }
                
            }
            
        }
        
        for (int i = 0; i < columnas; i++) 
        
        {
            
            for (int j = 0; j < (filas-2); j++) 
            
            {
                
                if(this.movimientosVerticales(j,i)) 
                
                {
                    
                    return true;
                    
                }
                
            }
            
        }
    
        return false;
        
    }
    
    public boolean movimientosHorizontales(int pfila, int pcolumna)
    {
        if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila][pcolumna+1].getColor())) 
        {
            if ((pcolumna+3) <= (columnas-1))   
            {
                if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila][pcolumna+3].getColor()))
                {
                    
                    joyas[pfila][pcolumna+2].setBackground(Color.BLUE);
                    joyas[pfila][pcolumna+3].setBackground(Color.RED);
                    joyas[pfila][pcolumna].setBackground(Color.RED);
                    joyas[pfila][pcolumna+1].setBackground(Color.RED);
                    
                    JOptionPane.showMessageDialog(null,"Antes del Cambio: ");
                    
                    Jewel joyaIncorrecta = joyas[pfila][pcolumna+2].getJewel();
                    Jewel joyaCorrecta = joyas[pfila][pcolumna+3].getJewel();
                    joyas[pfila][pcolumna+2].clearJewel();
                    joyas[pfila][pcolumna+3].clearJewel();
                    joyas[pfila][pcolumna+2].setJewel(joyaCorrecta);
                    joyas[pfila][pcolumna+3].setJewel(joyaIncorrecta);
                    
                    joyas[pfila][pcolumna+2].setBackground(Color.RED);
                    joyas[pfila][pcolumna+3].setBackground(Color.BLUE);
                    joyas[pfila][pcolumna].setBackground(Color.RED);
                    joyas[pfila][pcolumna+1].setBackground(Color.RED);
                    
                    JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                    
                    return true;

                }
            }
        }
        else
        {
            if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila][pcolumna+2].getColor()))
            {
                if ((pcolumna+3) <= (columnas-1))   
                {
                    if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila][pcolumna+3].getColor()))
                    {
                        
                        joyas[pfila][pcolumna+1].setBackground(Color.BLUE);
                        joyas[pfila][pcolumna].setBackground(Color.RED);
                        joyas[pfila][pcolumna+2].setBackground(Color.RED);
                        joyas[pfila][pcolumna+3].setBackground(Color.RED);

                        JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                        Jewel joyaIncorrecta = joyas[pfila][pcolumna+1].getJewel();
                        Jewel joyaCorrecta = joyas[pfila][pcolumna].getJewel();
                        joyas[pfila][pcolumna+1].clearJewel();
                        joyas[pfila][pcolumna].clearJewel();
                        joyas[pfila][pcolumna+1].setJewel(joyaCorrecta);
                        joyas[pfila][pcolumna].setJewel(joyaIncorrecta);

                        joyas[pfila][pcolumna+1].setBackground(Color.RED);
                        joyas[pfila][pcolumna].setBackground(Color.BLUE);
                        joyas[pfila][pcolumna+2].setBackground(Color.RED);
                        joyas[pfila][pcolumna+3].setBackground(Color.RED);

                        JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                        
                        return true;

                    }
                }
                else
                {
                    if ((pfila > 0)&&(pfila < (filas-1)))
                    {
                        if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila-1][pcolumna+1].getColor()))
                        {
                            joyas[pfila-1][pcolumna+1].setBackground(Color.RED);
                            joyas[pfila][pcolumna+1].setBackground(Color.BLUE);
                            joyas[pfila][pcolumna+2].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                            Jewel joyaIncorrecta = joyas[pfila][pcolumna+1].getJewel();
                            Jewel joyaCorrecta = joyas[pfila-1][pcolumna+1].getJewel();
                            joyas[pfila][pcolumna+1].clearJewel();
                            joyas[pfila-1][pcolumna+1].clearJewel();
                            joyas[pfila][pcolumna+1].setJewel(joyaCorrecta);
                            joyas[pfila-1][pcolumna+1].setJewel(joyaIncorrecta);

                            joyas[pfila-1][pcolumna+1].setBackground(Color.BLUE);
                            joyas[pfila][pcolumna+1].setBackground(Color.RED);
                            joyas[pfila][pcolumna+2].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                            return true;

                        }
                        if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+1][pcolumna+1].getColor()))
                        {
                            joyas[pfila+1][pcolumna+1].setBackground(Color.RED);
                            joyas[pfila][pcolumna+1].setBackground(Color.BLUE);
                            joyas[pfila][pcolumna+2].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                            Jewel joyaIncorrecta = joyas[pfila][pcolumna+1].getJewel();
                            Jewel joyaCorrecta = joyas[pfila+1][pcolumna+1].getJewel();
                            joyas[pfila][pcolumna+1].clearJewel();
                            joyas[pfila+1][pcolumna+1].clearJewel();
                            joyas[pfila][pcolumna+1].setJewel(joyaCorrecta);
                            joyas[pfila+1][pcolumna+1].setJewel(joyaIncorrecta);

                            joyas[pfila+1][pcolumna+1].setBackground(Color.BLUE);
                            joyas[pfila][pcolumna+1].setBackground(Color.RED);
                            joyas[pfila][pcolumna+2].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                            return true;

                        }
                    }
                    else
                    {
                        if (pfila == 0)
                        {
                            if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+1][pcolumna+1].getColor()))
                            {
                                joyas[pfila+1][pcolumna+1].setBackground(Color.RED);
                                joyas[pfila][pcolumna+1].setBackground(Color.BLUE);
                                joyas[pfila][pcolumna+2].setBackground(Color.RED);
                                joyas[pfila][pcolumna].setBackground(Color.RED);

                                JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                                Jewel joyaIncorrecta = joyas[pfila][pcolumna+1].getJewel();
                                Jewel joyaCorrecta = joyas[pfila+1][pcolumna+1].getJewel();
                                joyas[pfila][pcolumna+1].clearJewel();
                                joyas[pfila+1][pcolumna+1].clearJewel();
                                joyas[pfila][pcolumna+1].setJewel(joyaCorrecta);
                                joyas[pfila+1][pcolumna+1].setJewel(joyaIncorrecta);

                                joyas[pfila+1][pcolumna+1].setBackground(Color.BLUE);
                                joyas[pfila][pcolumna+1].setBackground(Color.RED);
                                joyas[pfila][pcolumna+2].setBackground(Color.RED);
                                joyas[pfila][pcolumna].setBackground(Color.RED);

                                JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                                return true;

                            }
                        }
                        if (pfila == (filas-1))
                        {
                            if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila-1][pcolumna+1].getColor()))
                            {
                                joyas[pfila-1][pcolumna+1].setBackground(Color.RED);
                                joyas[pfila][pcolumna+1].setBackground(Color.BLUE);
                                joyas[pfila][pcolumna+2].setBackground(Color.RED);
                                joyas[pfila][pcolumna].setBackground(Color.RED);

                                JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                                Jewel joyaIncorrecta = joyas[pfila][pcolumna+1].getJewel();
                                Jewel joyaCorrecta = joyas[pfila-1][pcolumna+1].getJewel();
                                joyas[pfila][pcolumna+1].clearJewel();
                                joyas[pfila-1][pcolumna+1].clearJewel();
                                joyas[pfila][pcolumna+1].setJewel(joyaCorrecta);
                                joyas[pfila-1][pcolumna+1].setJewel(joyaIncorrecta);

                                joyas[pfila-1][pcolumna+1].setBackground(Color.BLUE);
                                joyas[pfila][pcolumna+1].setBackground(Color.RED);
                                joyas[pfila][pcolumna+2].setBackground(Color.RED);
                                joyas[pfila][pcolumna].setBackground(Color.RED);

                                JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                                return true;

                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean movimientosVerticales(int pfila, int pcolumna)
    {
        if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+1][pcolumna].getColor())) 
        {
            if ((pfila+3) <= (filas-1))   
            {
                if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+3][pcolumna].getColor()))
                {
                    
                    joyas[pfila+2][pcolumna].setBackground(Color.BLUE);
                    joyas[pfila+3][pcolumna].setBackground(Color.RED);
                    joyas[pfila][pcolumna].setBackground(Color.RED);
                    joyas[pfila+1][pcolumna].setBackground(Color.RED);
                    
                    JOptionPane.showMessageDialog(null,"Antes del Cambio: ");
                    
                    Jewel joyaIncorrecta = joyas[pfila+2][pcolumna].getJewel();
                    Jewel joyaCorrecta = joyas[pfila+3][pcolumna].getJewel();
                    joyas[pfila+2][pcolumna].clearJewel();
                    joyas[pfila+3][pcolumna].clearJewel();
                    joyas[pfila+2][pcolumna].setJewel(joyaCorrecta);
                    joyas[pfila+3][pcolumna].setJewel(joyaIncorrecta);
                    
                    joyas[pfila+2][pcolumna].setBackground(Color.RED);
                    joyas[pfila+3][pcolumna].setBackground(Color.BLUE);
                    joyas[pfila][pcolumna].setBackground(Color.RED);
                    joyas[pfila+1][pcolumna].setBackground(Color.RED);
                    
                    JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                    
                    return true;

                }
            }
        }
        else
        {
            if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+2][pcolumna].getColor()))
            {
                if ((pfila+3) <= (filas-1))   
                {
                    if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+3][pcolumna].getColor()))
                    {
                        joyas[pfila+1][pcolumna].setBackground(Color.BLUE);
                        joyas[pfila][pcolumna].setBackground(Color.RED);
                        joyas[pfila+2][pcolumna].setBackground(Color.RED);
                        joyas[pfila+3][pcolumna].setBackground(Color.RED);

                        JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                        Jewel joyaIncorrecta = joyas[pfila+1][pcolumna].getJewel();
                        Jewel joyaCorrecta = joyas[pfila][pcolumna].getJewel();
                        joyas[pfila+1][pcolumna].clearJewel();
                        joyas[pfila][pcolumna].clearJewel();
                        joyas[pfila+1][pcolumna].setJewel(joyaCorrecta);
                        joyas[pfila][pcolumna].setJewel(joyaIncorrecta);

                        joyas[pfila+1][pcolumna].setBackground(Color.RED);
                        joyas[pfila][pcolumna].setBackground(Color.BLUE);
                        joyas[pfila+2][pcolumna].setBackground(Color.RED);
                        joyas[pfila+3][pcolumna].setBackground(Color.RED);

                        JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                        return true;

                    }
                }
                else
                {
                    if ((pcolumna > 0)&&(pcolumna < (columnas-1)))
                    {
                        if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+1][pcolumna-1].getColor()))
                        {
                            joyas[pfila+1][pcolumna-1].setBackground(Color.RED);
                            joyas[pfila+1][pcolumna].setBackground(Color.BLUE);
                            joyas[pfila+2][pcolumna].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                            Jewel joyaIncorrecta = joyas[pfila+1][pcolumna].getJewel();
                            Jewel joyaCorrecta = joyas[pfila+1][pcolumna-1].getJewel();
                            joyas[pfila+1][pcolumna].clearJewel();
                            joyas[pfila+1][pcolumna-1].clearJewel();
                            joyas[pfila+1][pcolumna].setJewel(joyaCorrecta);
                            joyas[pfila+1][pcolumna-1].setJewel(joyaIncorrecta);

                            joyas[pfila+1][pcolumna-1].setBackground(Color.BLUE);
                            joyas[pfila+1][pcolumna].setBackground(Color.RED);
                            joyas[pfila+2][pcolumna].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                            return true;

                        }
                        if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+1][pcolumna+1].getColor()))
                        {
                            joyas[pfila+1][pcolumna+1].setBackground(Color.RED);
                            joyas[pfila+1][pcolumna].setBackground(Color.BLUE);
                            joyas[pfila+2][pcolumna].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                            Jewel joyaIncorrecta = joyas[pfila+1][pcolumna].getJewel();
                            Jewel joyaCorrecta = joyas[pfila+1][pcolumna+1].getJewel();
                            joyas[pfila+1][pcolumna].clearJewel();
                            joyas[pfila+1][pcolumna+1].clearJewel();
                            joyas[pfila+1][pcolumna].setJewel(joyaCorrecta);
                            joyas[pfila+1][pcolumna+1].setJewel(joyaIncorrecta);

                            joyas[pfila+1][pcolumna+1].setBackground(Color.BLUE);
                            joyas[pfila+1][pcolumna].setBackground(Color.RED);
                            joyas[pfila+2][pcolumna].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                            return true;

                        }
                    }
                    else
                    {
                        if (pcolumna == 0)
                        {
                            if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+1][pcolumna+1].getColor()))
                            {
                            joyas[pfila+1][pcolumna+1].setBackground(Color.RED);
                            joyas[pfila+1][pcolumna].setBackground(Color.BLUE);
                            joyas[pfila+2][pcolumna].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                            Jewel joyaIncorrecta = joyas[pfila+1][pcolumna].getJewel();
                            Jewel joyaCorrecta = joyas[pfila+1][pcolumna+1].getJewel();
                            joyas[pfila+1][pcolumna].clearJewel();
                            joyas[pfila+1][pcolumna+1].clearJewel();
                            joyas[pfila+1][pcolumna].setJewel(joyaCorrecta);
                            joyas[pfila+1][pcolumna+1].setJewel(joyaIncorrecta);

                            joyas[pfila+1][pcolumna+1].setBackground(Color.BLUE);
                            joyas[pfila+1][pcolumna].setBackground(Color.RED);
                            joyas[pfila+2][pcolumna].setBackground(Color.RED);
                            joyas[pfila][pcolumna].setBackground(Color.RED);

                            JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                                return true;

                            }
                        }
                        if (pcolumna == (columnas-1))
                        {
                            if (joyas[pfila][pcolumna].getColor().equals(joyas[pfila+1][pcolumna-1].getColor()))
                            {
                                joyas[pfila+1][pcolumna-1].setBackground(Color.RED);
                                joyas[pfila+1][pcolumna].setBackground(Color.BLUE);
                                joyas[pfila+2][pcolumna].setBackground(Color.RED);
                                joyas[pfila][pcolumna].setBackground(Color.RED);

                                JOptionPane.showMessageDialog(null,"Antes del Cambio: ");

                                Jewel joyaIncorrecta = joyas[pfila+1][pcolumna].getJewel();
                                Jewel joyaCorrecta = joyas[pfila+1][pcolumna-1].getJewel();
                                joyas[pfila+1][pcolumna].clearJewel();
                                joyas[pfila+1][pcolumna-1].clearJewel();
                                joyas[pfila+1][pcolumna].setJewel(joyaCorrecta);
                                joyas[pfila+1][pcolumna-1].setJewel(joyaIncorrecta);

                                joyas[pfila+1][pcolumna-1].setBackground(Color.BLUE);
                                joyas[pfila+1][pcolumna].setBackground(Color.RED);
                                joyas[pfila+2][pcolumna].setBackground(Color.RED);
                                joyas[pfila][pcolumna].setBackground(Color.RED);

                                JOptionPane.showMessageDialog(null,"Despues del Cambio: ");
                                return true;

                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public void update()
    
    {
    
        this.prepararTablero();
        if(!this.posiblesCombos())
        {
            
            JOptionPane.showMessageDialog(null,"Perdiste");
            Tablero.this.processWindowEvent(new WindowEvent(Tablero.this, WindowEvent.WINDOW_CLOSING));
            timer.stop();
            
        }
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                joyas[i][j].setBackground(Color.LIGHT_GRAY);
            }
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    
    {
        
        this.update();
        this.repaint();
        
    }

}
