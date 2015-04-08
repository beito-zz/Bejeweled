
package proyectoii;

import javax.swing.JPanel;

public class Field extends JPanel 

{
    
    private boolean hasJewel=true;
    private Jewel jewel;
    private boolean markedfordel;
    private boolean isSelected;
    
    public boolean getHasJewel()
    {
        return hasJewel;
    }
    
    public boolean getIsSelected(){
        return isSelected;
    }
    
    public void setIsSelected(boolean selected){
        this.isSelected=selected;
    }
    
    public Jewel getJewel(){
        return this.jewel;
    }
    
    public void setMarkedForDel(boolean mark){
        this.markedfordel=mark;
    }
    
    public boolean isMarkedForDel(){
        return markedfordel;
    }
    
    public void setJewel(Jewel jewel){
            this.jewel=jewel;
            this.add(jewel);
            this.hasJewel = true;
    }
    
    public void setNotAnimated(){
        if(this.jewel!=null){
            this.getJewel().setNotAnimated();
        }
    }
    
    public void setAnimated(){
        if(this.jewel!=null){
            this.jewel.setAnimated();
        }
    }
    
    public void clearJewel(){
        this.hasJewel = false;
        if(this.jewel!=null){
            this.remove(this.jewel);
        }
        this.jewel=null;
    }
    
    public String getColor(){
        String res = "";
        if(jewel!=null){
            res = jewel.getColor();
        }
        return res;
    }
}
