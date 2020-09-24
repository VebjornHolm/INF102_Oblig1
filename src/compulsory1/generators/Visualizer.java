package compulsory1.generators;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import compulsory1.management.Location;

/**
 * A Visualizer for the input files generated by the Generate.java script
 * If the Visualizer crashes with an InputMismatchException there is likely a mismatch between the files we generated
 * and your system locale, running Generate.java to refresh the tests should fix the problem
 * Axis are 0 - 1000, robots are white, jobs are red
 * a - d, or LEFT - RIGHT toggles input
 * q exits
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public class Visualizer extends Frame{
	private static final long serialVersionUID = 1L;
	ArrayList<Location> robots;
	ArrayList<Location> jobs;
	BufferedImage buffer;
	Graphics bufferGraphics;
	String[] input = {"01.in", "02.in", "03.in", "04.in", "05.in", "06.in"};
	int fileNumber = 0;
	
	public static void main(String args[]) throws Exception{
		Visualizer v = new Visualizer(400, 400);
		v.readFile("input/"+v.input[v.fileNumber]);
		v.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e){
				if (e.getKeyChar() == 'q') System.exit(0);
				if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
					v.fileNumber = (v.fileNumber + v.input.length-1) % v.input.length;
					v.readFile("input/"+v.input[v.fileNumber]);
				}
				if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					v.fileNumber = (v.fileNumber + v.input.length+1) % v.input.length;
					v.readFile("input/"+v.input[v.fileNumber]);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		v.setVisible(true);
		while (true) {
			v.repaint();
			Thread.sleep(30);
		}
	}
	
	Visualizer(int width, int height){
		super();
		this.setSize(width, height);
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		bufferGraphics = buffer.getGraphics();
	}
	
	private void readFile(String file){
		this.setTitle(file + "          press q to exit");
		File input = new File(file);
		Scanner sc = null;
		try {
			sc = new Scanner(new FileReader(input));
		}
		catch (Exception e) {
			System.exit(0);
		}
		int n = sc.nextInt();
		int m = sc.nextInt();
		robots = new ArrayList<>();
		jobs = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			robots.add(new Location(sc.nextDouble(), sc.nextDouble()));
		}
		for (int i = 0; i < m; i++) {
			jobs.add(new Location(sc.nextDouble(), sc.nextDouble()));
			sc.next(); sc.next(); // Consume job.t and job.k
		}
		sc.close();
	}
	
	@Override
	public void update(Graphics g) {
		if (this.getWidth() != buffer.getWidth() || this.getHeight() != buffer.getHeight()) {
			buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
			bufferGraphics = buffer.createGraphics();
		}
		paint(bufferGraphics);
		g.drawImage(buffer, 0, 0, null);
	}
	
	@Override
	public void paint(Graphics g) {
		if (robots == null) return;
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		for (Location location: robots) {
			int x = (int) (location.x*(this.getWidth()/1000.));
			int y = (int) (location.y*(this.getHeight()/1000.));
			g.drawLine(x, y, x, y);
		}
		g.setColor(Color.red);
		for (Location location: jobs) {
			int x = (int) (location.x*(this.getWidth()/1000.));
			int y = (int) (location.y*(this.getHeight()/1000.));
			g.drawLine(x, y, x, y);
		}
	}
}
