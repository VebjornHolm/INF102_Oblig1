package compulsory1.generators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import compulsory1.management.Job;
import compulsory1.management.Location;

/**
 * 1) This Generator generates one or more Uniformly distributed center points
 *
 * 2) This Generator generate Random points around the center points.
 *    The Random points are chosen relative to a center point such that the distance 
 *    from the point follows a folded Normal distribution 
 *    {@link https://en.wikipedia.org/wiki/Folded_normal_distribution}
 *    
 * 3) This class generates jobs and robots on these random points
 * 	  Jobs till be assigned a time drawn uniformly from a time interval
 * 
 * 4) This writes data to a file that can be read by other methods such as
 *    {@link compulsory1.management.TestClient} and {@link compulsory1.generators.Vizualizer}.
 *    
 *    TODO: This class does not adhere to the Single responsibility principle
 *    TODO: create abstract class to extract code common with the other generators
 * 
 * @author Olav Bakken og Martin Vatshelle
 */
public class GenerateNormal {
	
	private static final int coordRange = 1000;
	private double stdDeviation;
	private double stdCenter;
	private Random random = new Random();
	
	GenerateNormal(double stdDeviation, double stdCenter, long seed){
		this.stdCenter = stdCenter;
		this.stdDeviation = stdDeviation;
		this.random = new Random();
		this.random.setSeed(seed);
	}
	
	/**
	 * prints model description to stdout, coordinates are chosen according to a normal
	 * distribution centered at <x-center, y-center> (picked uniformly at random in range
	 * 0- coordRange) with the the distribution of the distance chosen according to a
	 * normal distribution centered at stdCenter, with standard deviation stdDeviation,
	 * job.t is chosen uniformly at random in the range 0 - 10*numberOfJobs,
	 * job.k is chosen uniformly at random in the range 1-10
	 * 
	 * reads <number of robots> <number of tasks> <number of centers> from stdin
	 * 
	 * @param numberOfRobots
	 * @param numberOfJobs
	 * @param numberOfCenters
	 * @param file
	 * @throws IOException
	 */
	public void generate(int numberOfRobots, int numberOfJobs, int numberOfCenters, String file) throws IOException{
		ArrayList<Location> centers = new ArrayList<>();
		for (int i = 0; i < numberOfCenters; i++) {
			centers.add(new Location(random.nextDouble()*coordRange, random.nextDouble()*coordRange));
		}
		
		ArrayList<Location> robots = new ArrayList<>();
		ArrayList<Job> jobs = new ArrayList<>();
		for (int i = 0; i < numberOfRobots; i++) {
			robots.add(generateLocation(centers.get(random.nextInt(numberOfCenters))));
		}
		for (int i = 0; i < numberOfJobs; i++) {
			Location location = generateLocation(centers.get(random.nextInt(numberOfCenters)));
			double t = random.nextDouble()*numberOfJobs*10;
			int k = random.nextInt(10)+1;
			jobs.add(new Job(location, i, t, k));
		}
		
		Collections.sort(jobs, (u, v)->{
			if (u.t == v.t) return 0;
			else if (u.t < v.t) return -1;
			else return 1;
		});
		
		//printout
		File output = new File(file);
		BufferedWriter out = new BufferedWriter(new FileWriter(output));
		out.write(String.format("%d %d\n", numberOfRobots, numberOfJobs));
		for (Location location: robots) out.write(String.format("%f %f\n", location.x, location.y));
		for (Job job: jobs) out.write(String.format("%f %f %f %d\n", job.location.x, job.location.y, job.t, job.robotsNeeded));
		out.flush();
		out.close();
	}
	
	private Location generateLocation(Location center) {
		double distance = random.nextGaussian()*stdDeviation + stdCenter;
		double angle = random.nextDouble()*360;
		double x = center.x + Math.cos(angle)*distance;
		double y = center.y + Math.sin(angle)*distance;
		return new Location(x, y);
	}
}
