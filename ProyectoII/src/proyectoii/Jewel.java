/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoii;

import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Jewel extends JLabel

{
    
    private String color;
    private Icon pic;
    
    public Jewel()
    
    {
        this.RandomJewel();
        this.pic = new ImageIcon(this.getClass().getResource("gems/" + color + ".png"));
        this.setIcon(this.pic);
        
    }
    
    public void RandomJewel()
            
    {
        Random rand = new Random();
        int n = rand.nextInt(7);
        
        switch (n)
        {
            case 0:
            {
                this.color = "blue";
                break;
            }
            case 1:
            {
                this.color = "red";
                break;
            }           
            case 2:
            {
                this.color = "yellow";
                break;
            }
            case 3:
            {
                this.color = "green";
                break;
            }
            case 4:
            {
                this.color = "white";
                break;
            }           
            case 5:
            {
                this.color = "purple";
                break;
            }
            case 6:
            {
                this.color = "orange";
                break;
            }
            default: 
            {
                break;
            }
        }
        
    }
    
    public String getColor()
    
    {
        
        return this.color;
        
    }
    
    public void setAnimated()
    
    {
        
        this.setIcon(new ImageIcon(this.getClass().getResource("gems/animated/" + color + ".gif")));
        
    }
    
    public void setNotAnimated()
    
    {
        
        this.setIcon(pic);
        
    }
    
}
