package compulsory1.generators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import compulsory1.management.Location;
import compulsory1.management.Job;

/**
 * Creates Job file with Jobs distributed uniformly at random
 * @see GenerateNormal
 * 
 * @author Olav Bakken og Martin Vatshelle
 */
public class GenerateUniform {
	
	static final int coordRange = 1000;
	private Random random = new Random();
	
	GenerateUniform(long seed){
		this.random = new Random();
		random.setSeed(seed);
	}
	
	/**
	 * prints model description to stdout, coordinates are chosen uniformly at
	 * random from the range 0-coordRange, job.t is chosen uniformly at random in the
	 * range 0 - 10*numberOfJobs, job.k is chosen uniformly at random in the
	 * range 1-10
	 * 
	 * reads <number of robots> <number of tasks> from stdin
	 * 
	 * @param numberOfRobots
	 * @param numberOfJobs
	 * @throws IOException 
	 */
	public void generate(int numberOfRobots, int numberOfJobs, String file) throws IOException {
		ArrayList<Location> robots = new ArrayList<>();
		ArrayList<Job> jobs = new ArrayList<>();
		for (int i = 0; i < numberOfRobots; i++) {
			robots.add(new Location(random.nextDouble()*coordRange, random.nextDouble()*coordRange));
		}
		for (int i = 0; i < numberOfJobs; i++) {
			Location location = new Location(random.nextDouble()*coordRange, random.nextDouble()*coordRange);
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
}