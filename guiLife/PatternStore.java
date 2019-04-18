package guiLife;
import java.io.*;
import java.net.*;
import java.util.*;
public class PatternStore {
private ArrayList <Pattern> patterns = new ArrayList<>();
	
	
public PatternStore(String source) throws IOException {
if (source.startsWith("http://") || source.startsWith("https://")) {
loadFromURL(source);
}
else {
loadFromDisk(source);
}
}
public PatternStore(Reader source)throws IOException {
load(source);
}
private void load(Reader r) throws IOException {
String line="";
BufferedReader b=new BufferedReader(r);
while((line=b.readLine())!=null){
	try {
	patterns.add(new Pattern(line));
	}
	catch(PatternFormatException e) {
		System.out.println("Warning: "+ e.getMessage()+" For line : "+line);
	}
}
}


private void loadFromURL(String url) throws IOException {
URL link=new URL(url);
URLConnection conn=link.openConnection();
try {
Reader r=new InputStreamReader(conn.getInputStream());
load(r);
}
catch(FileNotFoundException e) {
	throw new IOException("Could not get an input stream!");
}



}


private void loadFromDisk(String filename) throws IOException {
	Reader r = new FileReader(filename);
	load(r);
	

}

public ArrayList<Pattern> getPatternsNameSorted() {
	Collections.sort(patterns);
	return new ArrayList<Pattern>(patterns);
}







}
