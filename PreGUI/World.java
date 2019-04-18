

import java.io.IOException;
import java.util.Stack;

public abstract class World implements Cloneable{
private int generation=0;
Pattern pattern=null;

public World (String pat)throws PatternFormatException
{
        this.pattern = new Pattern(pat);

}
public World(Pattern p) {
	this.pattern=p;
}
public World(World w) {
	this.pattern=w.pattern;
	this.generation=w.getGenerationCount();
}
public int getWidth() {
	return pattern.getWidth();
}
public void nextGeneration() {
	this.nextGenerationImpl();
	incrementGenerationCount();
}
protected int countNeighbours(int col, int row){
    int number=0;
    for(int i=row-1;i<=row+1;i++){
        for(int z=col-1;z<=col+1;z++){

            if(i!=row||z!=col) {

                if (this.getCell(z, i)) number++;
            }
        }
    }
    return number;
}
protected boolean computeCell(int col, int row){
    int neighbours=countNeighbours(col,row);
 if(this.getCell(col,row)==false)
 {
     if(neighbours==3)
         return true;
     else
         return false;
 }
 else
 {

     if(neighbours==2||neighbours==3)return true;
     else
         return false;
 }
}
protected  void nextGenerationImpl() {
	Stack <Integer> myStack=new Stack <Integer>();
    for(int i=0;i<getHeight();i++){
        for(int z=0;z<getWidth();z++){

            if(getCell(z,i)!=computeCell(z,i)){
            	
                if(computeCell(z,i))myStack.push(1);else myStack.push(0);
                myStack.push(i);
                myStack.push(z);


            }
        }
    }
 while(!myStack.empty()){
	 
     setCell(myStack.pop(),myStack.pop(),myStack.pop()==1);
 }
 
}
public Object clone() throws CloneNotSupportedException {
	return super.clone();
}
public int getHeight() {return pattern.getHeight();}
public int getGenerationCount() {return this.generation;}
protected void incrementGenerationCount() {this.generation+=1;}
protected Pattern getPattern() {return this.pattern;}
public abstract void setCell(int a,int b,boolean B);
public abstract boolean getCell(int a,int b);


}
