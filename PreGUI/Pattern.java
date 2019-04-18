
public class Pattern implements Comparable <Pattern> {
	private String name;
	private String author;
	private int width;
	private int height;
	private int startCol;
	private int startRow;
	private String cells; 
	private String []cellsToBeInitialised;
	
	
	public Pattern(String format) throws PatternFormatException{
		 checkExceptions(format);
		 String[] arr=format.split(":");
		 cellsToBeInitialised=arr[6].trim().split(" ");
		 startRow=Integer.parseInt(arr[5]);
		 startCol=Integer.parseInt(arr[4]);
		 width=Integer.parseInt(arr[2]);
		 height=Integer.parseInt(arr[3]);
		 cells=arr[6];
		 name=arr[0];
		 author=arr[1];
         
	}
	private void checkExceptions(String info) throws PatternFormatException{
		if(info.equals("")) {
			throw new PatternFormatException("Please specify a pattern.");
		}
		else if(info.indexOf(":")==-1) {
			throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern (found 1).");
		}
		else if(info.indexOf(":")!=-1) {
			String [] splitInfo=info.split(":");
			if(splitInfo.length!=7) {
				throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern(found "+splitInfo.length+").");
			}
			else {
				String [] splitCells=splitInfo[6].trim().split(" ");
				for(int i=0;i<splitInfo[2].length();i++) {
					if(!Character.isDigit(splitInfo[2].charAt(i))) {
						throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number ('"+splitInfo[2]+"' given).");
					}
				}
				for(int i=0;i<splitInfo[3].length();i++) {
					if(!Character.isDigit(splitInfo[3].charAt(i))) {
						throw new PatternFormatException("Invalid pattern format: Could not interpret the height field as a number ('"+splitInfo[3]+"' given).");
					}
				}
				for(int i=0;i<splitInfo[4].length();i++) {
					if(!Character.isDigit(splitInfo[4].charAt(i))) {
						throw new PatternFormatException("Invalid pattern format: Could not interpret the StartCol field as a number ('"+splitInfo[4]+"' given).");
					}
				}
				for(int i=0;i<splitInfo[5].length();i++) {
					if(!Character.isDigit(splitInfo[5].charAt(i))) {
						throw new PatternFormatException("Invalid pattern format: Could not interpret the StartRow field as a number ('"+splitInfo[5]+"' given).");
					}
				}
				for(int i=0;i<splitCells.length;i++) {
					for(int z=0;z<splitCells[i].length();z++) {
						if(splitCells[i].charAt(z)=='0'||splitCells[i].charAt(z)=='1')continue;
						else throw new PatternFormatException("Invalid pattern format: Malformed pattern ’"+splitInfo[6]+"’.");
					}
				}
				if(Integer.parseInt(splitInfo[5])+splitCells.length>Integer.parseInt(splitInfo[3])) {
					throw new PatternFormatException("Invalid number of rows of the cells :Out of bounds exception 01!(rows)");
				}
				
				for(int i=0;i<splitCells.length;i++) {
					if(Integer.parseInt(splitInfo[4])+splitCells[i].length()>Integer.parseInt(splitInfo[2])) {
						throw new PatternFormatException("Invalid number of  cells :Out of bounds exception 02!(columns)");
					}
				}
					
				
				
			}
		}
	}
	
	public void initialise(World world)throws PatternFormatException {
	

		
		for(int i=0;i<cellsToBeInitialised.length;i++){
			for(int z=0;z<cellsToBeInitialised[i].length();z++){
				
				if(cellsToBeInitialised[i].charAt((z))=='0') {
					world.setCell(startCol, startRow, false);startCol++;
					}
				else if(cellsToBeInitialised[i].charAt((z))=='1') {
					world.setCell(startCol, startRow, true);startCol++;
					}
			}
			startRow++;
			startCol=startCol-cellsToBeInitialised[i].length();
			
		}
		startRow=startRow-cellsToBeInitialised.length;
		
	}
	@Override
	public int compareTo(Pattern arg0) {
		return this.getName().compareTo(arg0.getName());
	}
	public String getName() {return name;}
	public String getAuthor(){return author;}
	public int getStartCol(){return startCol;}
	public int getStartRow(){return startRow;}
	public String getCells() {return cells;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	




}

	

