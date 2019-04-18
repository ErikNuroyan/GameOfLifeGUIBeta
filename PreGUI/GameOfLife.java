import java.io.IOException;
import java.util.*;
import java.io.*;

public class GameOfLife {
private World world;
private ArrayList<World> cachedWorlds=new ArrayList<>();
private PatternStore store;
public GameOfLife(PatternStore patstore) {
	this.store=patstore;
}

public void play()throws IOException, PatternFormatException, CloneNotSupportedException{
	String response = "";
	int currentWorld=0;
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Please select a pattern to play (l to list):");
	while (!response.equals("q")) {
	response = in.readLine();
	System.out.println(response);
	if (response.equals("f")) {
		
		
	if (world == null) {
	System.out.println("Please select a pattern to play (l to list):");
	
	}
	else {
		if(currentWorld<cachedWorlds.size()-1) {
		++currentWorld;
		world=cachedWorlds.get(currentWorld);
		print();
		}
		else {
			if(currentWorld==0) {cachedWorlds.add(copyWorld(false));}
			world=copyWorld(false);
			world.nextGeneration();
			cachedWorlds.add(world);
			currentWorld++;
			
			
			print();
		}
	}
	
	}
	else if(response.equals("b")) {
		
		if(currentWorld!=0) {
		world=cachedWorlds.get(--currentWorld);
		}
		else {
		world=cachedWorlds.get(currentWorld);
		}
		print();
	}
	else if (response.equals("l")) {
		
		
		
	ArrayList<Pattern> names = store.getPatternsNameSorted();
	int i = 0;
	for (Pattern p : names) {
	System.out.println(i + " " + p.getName() + " (" + p.getAuthor() + ")");
	i++;
	}
	
	
	}
	else if (response.startsWith("p")) {
	ArrayList <Pattern> names = store.getPatternsNameSorted();
	int numberOfPattern=Integer.parseInt(response.split(" ")[1]);
	
	
	if(names.get(numberOfPattern).getHeight()*names.get(numberOfPattern).getWidth()<=64) {
		this.world=new PackedLife(names.get(numberOfPattern));
	}
	else {
		this.world=new ArrayWorld(names.get(numberOfPattern));
	}
	
	print();
	if(!cachedWorlds.isEmpty()) {
		cachedWorlds.clear();
		currentWorld=0;
		System.out.println(cachedWorlds.size());
	}
	}

	
	}

}
public void print() {
	System.out.println("- "+this.world.getGenerationCount());
	for(int i=0;i<world.getHeight();i++){
		
		for(int z=0;z<world.getWidth();z++){
			if(world.getCell(z,i))System.out.print("#");
			else System.out.print("-");
		}
		
		System.out.println();
	}
}


private World copyWorld(boolean useCloning) throws PatternFormatException, CloneNotSupportedException {
	World toBeReturned=null;
	if(!useCloning) {
		if(world instanceof PackedLife) {
		toBeReturned=new PackedLife(world);
		return toBeReturned;
		}
		else if(world instanceof ArrayWorld){
		System.out.println("ArrayWorld");
		toBeReturned=new ArrayWorld(world);
		System.out.println(toBeReturned+"\n"+world);
		return toBeReturned;
		}
		else return null;
	}
	else {
		World returnValue = (World) world.clone();
		return returnValue;
	}
}


public static void main(String[] args) throws IOException,PatternFormatException, CloneNotSupportedException  {
	
	if (args.length != 1) {
		
		System.out.println("Usage: java GameOfLife <path/url to store>");
		return;
	}
	try {
		PatternStore ps = new PatternStore(args[0]);
		GameOfLife gol = new GameOfLife(ps);
		gol.play();
	}
	catch (IOException ioe) {
		System.out.println("Failed to load pattern store");
	}
	
	
}
}
