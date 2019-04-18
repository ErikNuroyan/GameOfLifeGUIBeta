import java.util.*;
public class ArrayWorld extends World implements Cloneable{
private boolean [][]world;
boolean [] deadRow;
public ArrayWorld(String arg)throws PatternFormatException {
    super(arg);
    String [] arr=arg.split(":");
    deadRow=new boolean[getPattern().getWidth()];
    this.world=new boolean [Integer.parseInt(arr[3])][Integer.parseInt(arr[2])];
    getPattern().initialise(this);
    
    for(int i =0;i<world.length;i++) {
    	boolean broken=false;
    	for(int z=0;z<world[i].length;z++) {
    		if(world[i][z]) {
    			broken=true;
    			break;
    		}
    	}
    	if(!broken) {
    		world[i]=deadRow;
    	}
    }
    
    
}
public ArrayWorld(Pattern pat) throws PatternFormatException {
	super(pat);
	deadRow=new boolean[getPattern().getWidth()];
	this.world=new boolean[pat.getHeight()][pat.getWidth()];
	getPattern().initialise(this);
    for(int i =0;i<world.length;i++) {
    	boolean broken=false;
    	for(int z=0;z<world[i].length;z++) {
    		if(world[i][z]) {
    			broken=true;
    			break;
    		}
    	}
    	if(!broken) {
    		world[i]=deadRow;
    	}
    }
}
public ArrayWorld(World aw) throws PatternFormatException {
	super(aw);
	ArrayWorld arrWorld=(ArrayWorld)aw;
	boolean [][] clone_1=arrWorld.world.clone();
	this.deadRow=new boolean[getPattern().getWidth()];
	boolean toBeAssigned [][]=new boolean[clone_1.length][clone_1[0].length];
	for(int i=0;i<clone_1.length;i++) {
		toBeAssigned[i]=clone_1[i].clone();
	}
	this.world=toBeAssigned;
    for(int i =0;i<world.length;i++) {
    	boolean broken=false;
    	for(int z=0;z<world[i].length;z++) {
    		if(world[i][z]) {
    			broken=true;
    			break;
    		}
    	}
    	if(!broken) {
    		world[i]=deadRow;
    	}
    }
	
	
	
}
public Object clone() throws CloneNotSupportedException {
		Object object1=super.clone();
	
	
		ArrayWorld obj1=(ArrayWorld)object1;
		boolean [][] clone_1=obj1.world.clone();
		boolean toBeAssigned [][]=new boolean[clone_1.length][clone_1[0].length];
		for(int i=0;i<clone_1.length;i++) {
			toBeAssigned[i]=clone_1[i].clone();
		}
		obj1.world=toBeAssigned;
	    for(int i =0;i<obj1.world.length;i++) {
	    	boolean broken=false;
	    	for(int z=0;z<obj1.world[i].length;z++) {
	    		if(obj1.world[i][z]) {
	    			broken=true;
	    			break;
	    		}
	    	}
	    	if(!broken) {
	    		obj1.world[i]=deadRow;
	    	}
	    }
		return obj1;
	
}
public boolean getCell(int col,int row) {
	if(row<0||row>=super.getHeight())return false;
	if(col<0||col>=super.getWidth())return false;
	return world[row][col];
	}

public void  setCell(int col,int row,boolean value) {

if(this.world[row]==deadRow) {
	this.world[row]=deadRow.clone();
}

this.world[row][col]=value;
}



}
