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
 * Creates Job file with Jobs distributed at random around 2 points
 * The timing of the Jobs is evenly spaced and alternates between the two centers.
 * 
 * A good strategy would be to station each Robot in each of the centers and wait for a job.
 * 
 * The optimal strategy is actually to send both robots back and forth between the two centers
 * (you would need to send the robot before the job is made available to reach in time) 
 * since the time between each job is large enough to reach the other center.
 * Once at a center they can wait on two strategic points on opposite sides of the center.
 * But this strategy relies on knowledge specific to this input that and can not work in general.
 * 
 * @see GenerateNormal
 * 
 * @author Olav Bakken og Martin Vatshelle
 */
public class GenerateBinarySlow {

	private double stdDeviation;
	private double stdCenter;
	static Random random = new Random();

	GenerateBinarySlow(double stdDeviation, double stdCenter, long seed){
		this.stdDeviation = stdDeviation;
		this.stdCenter = stdCenter;
		random = new Random();
		random.setSeed(seed);
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
	public void generate(int numberOfJobs, String file) throws IOException{
		ArrayList<Location> centers = new ArrayList<>();
		centers.add(new Location(400, 400));
		centers.add(new Location(400, 200));
		
		ArrayList<Location> robots = new ArrayList<>();
		ArrayList<Job> jobs = new ArrayList<>();
		
		int numberOfRobots = 2;
		robots.add(new Location(400,300));
		robots.add(new Location(100,300));

		for (int i = 0; i < numberOfJobs; i++) {
			Location location = generateLocation(centers.get(i%2));
			double t = i*500;
			jobs.add(new Job(location, i, t, 1));
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

