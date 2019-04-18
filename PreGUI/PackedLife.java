import java.io.IOException;
import java.util.*;
public class  PackedLife extends World implements Cloneable{

    private  long world;
   
    public PackedLife (String format)throws IOException,PatternFormatException{
        super(format);
        getPattern().initialise(this);
        int width=getPattern().getWidth();
        int height=getPattern().getHeight();
        if(height*width>=64){
            throw new IOException(""+width+" by "+height+" is too big for packed long!" );
        }
    }
    public PackedLife (Pattern pat)throws IOException,PatternFormatException{
        super(pat);
        getPattern().initialise(this);
        int width=getPattern().getWidth();
        int height=getPattern().getHeight();
        if(height*width>64){
            throw new IOException(""+width+" by "+height+" is too big for packed long!" );
        }
    }
    public PackedLife(World pl) throws PatternFormatException {
    	super(pl);
    	PackedLife packLife= (PackedLife)pl;
    	this.world=packLife.world;
    }
    
    public boolean getCell(int col, int row) {
        if (row < 0 || row >= super.getHeight()) {
            return false;
        }
        if (col < 0 || col >= super.getWidth()) {
            return false;
        }
        if (((world >>> (row * super.getWidth() + col)) & 1L) == 1L)
            return true;
        else
            return false;
    }

 public void setCell(int col, int row, boolean value){
     if (value == true) {

         if(getCell(col,row)==false){world=world+(1L<<row*super.getWidth()+col);};
     }
     else{
         if(getCell(col,row)==true)world=world-(1L<<row*super.getWidth()+col);
     }

 }

 public Object clone() throws CloneNotSupportedException {
		Object object1=super.clone();
		return (PackedLife)object1;
	
	}



}
