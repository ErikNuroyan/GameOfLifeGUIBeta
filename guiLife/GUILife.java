package guiLife;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class GUILife extends JFrame implements ListSelectionListener {
	private World world;
	private ArrayList<World> cachedWorlds=new ArrayList<>();
	private PatternStore store;
	private int currentWorld=0;
	private GamePanel gp;
	private JButton playButton;
	private boolean playing;
	private Timer timer= new Timer();
	private JLabel generation=new JLabel("Generation:0",JLabel.LEFT);
	public GUILife(PatternStore ps) throws PatternFormatException, IOException {
		super("Game of Life");
		store=ps;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024,768);
		add(createPatternsPanel(),BorderLayout.WEST);
		add(createControlPanel(),BorderLayout.SOUTH);
		gp=(GamePanel)createGamePanel();
		gp.add(generation,BorderLayout.SOUTH);
		add(gp,BorderLayout.CENTER);
	}
	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch,title);
		component.setBorder(tb);
		}
	
	
	
	private JPanel createGamePanel() {
		GamePanel gamePanel = new GamePanel();
		addBorder(gamePanel,"Game Panel");
		gamePanel.setLayout(new BorderLayout());
		return gamePanel;
		}
	
	private JPanel createPatternsPanel() {
		JPanel patt = new JPanel(new BorderLayout());
		addBorder(patt,"Patterns");
		JList<Pattern> patList=new JList(store.getPatternsNameSorted().toArray());
		JScrollPane scrollPane=new JScrollPane(patList);
		patList.addListSelectionListener(this);
		
		
		patt.add(scrollPane,BorderLayout.CENTER);
		
		return patt;
		}
	
	
	private JPanel createControlPanel() {
		JPanel ctrl = new JPanel(new GridLayout(1,3));
		addBorder(ctrl,"Controls");
		JButton back=new JButton("<Back");
		back.addActionListener(e->moveBack());
		ctrl.add(back);
		playButton =new JButton("Play");
		playButton.addActionListener(e->runOrPause());
		ctrl.add(playButton);
		JButton forward=new JButton("Forward>");
		forward.addActionListener(e->{
			try {
				moveForward();
			} catch (PatternFormatException e1) {
				
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				
				e1.printStackTrace();
			}
		});
		ctrl.add(forward);
	
		return ctrl;
		}
	
	private void moveBack() {
		if(currentWorld!=0) {
		world=cachedWorlds.get(--currentWorld);
		}
		else {
		world=cachedWorlds.get(currentWorld);
		}
		gp.display(this.world);
		generation.setText("Generation: "+this.world.getGenerationCount());
	}
	private void moveForward() throws PatternFormatException, CloneNotSupportedException {
		
		if (world != null) {
				if(currentWorld<cachedWorlds.size()-1) {
				++currentWorld;
				world=cachedWorlds.get(currentWorld);
				gp.display(this.world);
				generation.setText("Generation: "+this.world.getGenerationCount());
				}
				else {
					
					world=copyWorld(true);
					world.nextGeneration();
					cachedWorlds.add(world);
					currentWorld++;
					
					
					gp.display(world);
					generation.setText("Generation: "+this.world.getGenerationCount());
				}
			}
	}
	private void runOrPause() {
		if (playing) {
		timer.cancel();
		playing=false;
		playButton.setText("Play");
		}
		else {
		playing=true;
		playButton.setText("Stop");
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
		@Override
		public void run() {
		try {
			moveForward();
		} catch (PatternFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
					}
				}
			}, 0, 500);
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
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
	JList<Pattern> list = (JList<Pattern>) e.getSource();
	Pattern p = list.getSelectedValue();
	// TODO
	// Based on size, create either a PackedWorld or ArrayWorld
	if(p.getHeight()*p.getWidth()>64) {
		try {
			this.world=new ArrayWorld(p);
		} catch (PatternFormatException e1) {
			
			e1.printStackTrace();
		}
	}
	else if(p.getHeight()*p.getWidth()<=64) {
		try {
			
			this.world=new PackedLife(p);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (PatternFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	cachedWorlds.clear();
	try {
		cachedWorlds.add(copyWorld(true));
	} catch (PatternFormatException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (CloneNotSupportedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	currentWorld=0;
	timer.cancel();
	playButton.setText("Play");
	playing=false;
	gp.display(world);
	generation.setText("Generation: "+this.world.getGenerationCount());
	
	

	
	}
	
	
	
	
	public static void main(String[] args) throws IOException, PatternFormatException {
		PatternStore patStore=new PatternStore("https://bit.ly/2FJERFh"); 
		GUILife gui = new GUILife(patStore);
		gui.setVisible(true);
	}

}