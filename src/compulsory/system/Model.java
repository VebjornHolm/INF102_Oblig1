package compulsory.system;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import compulsory1.management.IStrategy;
import compulsory1.management.Job;
import compulsory1.management.Location;
import compulsory1.management.Robot;

/**
 * This class implements RobotState and keeps track of all Robots.
 * Instead of the real time system that gets positions from the GPS senders mounted on the robots,
 * this system models the robot positions. 
 * The system assumes robots move with a fixed speed in a straight line
 * This model is made in order to test out Robot control strategies before deploying them in the real world.
 * 
 * Each Robot has a target they are moving towards, if target == null it means the robot is awaiting orders.
 * Each Robot has a location and a time describing the last known location.
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public class Model implements RobotStateManager {
	
	/**
	 * Constructor which creates a RobotState model from a file.
	 * The accepted file format is....
	 * <numberOfRobots> <numberOfJobs>
	 * <xPos> <yPos>
	 * ... repeat numberOfRobots times
	 * <xPos> <yPos> <time> <numberOfRobotsRequired>
	 * ... repeat numberOfJobsTime
	 * 
	 * @param file
	 */
	public Model(String file, IStrategy strategy) throws Exception{ // Read problem from file
		Scanner sc = new Scanner(new FileReader(new File(file)));
		this.numberOfRobots = sc.nextInt(); int numberOfJobs = sc.nextInt();
		this.events = new PriorityQueue<>();
		this.strategy = strategy;
		
		//setup robot;
		for (int i = 0; i < numberOfRobots; i++) {
			Location location = new Location(sc.nextDouble(), sc.nextDouble());
			robotInfo.add(new RobotInfo(robotInfo.size(), location));
		}
		
		//set jobs
		this.jobs = new ArrayList<>();
		this.robotsPresent = new ArrayList<>();
		this.jobsFulfilled = new ArrayList<>();
		for (int i = 0; i < numberOfJobs; i++) {
			Job job = new Job(new Location(sc.nextDouble(), sc.nextDouble()), i, sc.nextDouble(), sc.nextInt());
			this.jobs.add(job);
			this.robotsPresent.add(new HashSet<>());
			this.jobsFulfilled.add(false);
			events.add(new Event(-1, job.id, -1, job.t, EventType.NEWJOB));
		}
		sc.close();
	}
	
	/**
	 * Initializes a Model for simulation of Robots
	 * The simulation will score the given strategy
	 * 
	 * @param startLocations - positions of Robots at time 0
	 * @param jobs - list of Jobs to be simulated
	 * @param strategy - reference to a implementation of Strategy interface
	 */
	public Model(ArrayList<Location> startLocations, ArrayList<Job> jobs, IStrategy strategy){
		this.numberOfRobots = startLocations.size();
		this.events = new PriorityQueue<>();
		this.strategy = strategy;
		
		// setup robots
		for (Location location: startLocations) {
			robotInfo.add(new RobotInfo(robotInfo.size(), location));
		}
		
		// setup jobs, ids should run in order from 0 -> jobs-1
		this.jobs = new ArrayList<>();
		this.robotsPresent = new ArrayList<>();
		this.jobsFulfilled = new ArrayList<>();
		for (Job job: jobs) {
			this.jobs.add(job);
			this.robotsPresent.add(new HashSet<>());
			this.jobsFulfilled.add(false);
			events.add(new Event(-1, job.id, -1, job.t, EventType.NEWJOB));
		}
	}
	
	
	/*
	 * Alternative way to supply problem
	 * Model (ProblemGenerator generator) {
	 * }
	*/
	
	// miscellaneous fields
	private int numberOfRobots;
	private double currentTime = 0d; // Updated at each event
	private double score = 0d; // total unavailable time across all jobs, smaller is better
	
	// Info describing each Robot 
	private ArrayList<RobotInfo> robotInfo = new ArrayList<RobotInfo>(numberOfRobots);
	
	// fields for tracking status of jobs
	private ArrayList<Job> jobs;
	private ArrayList<HashSet<Integer>> robotsPresent;
	private ArrayList<Boolean> jobsFulfilled;
	
	// event tracker
	private PriorityQueue<Event> events;
	private IStrategy strategy;
	
	/*
	 *  Loop for running simulation, will update the simulation to current timestep,
	 *  before calling student code, to update robot behaviour.
	 */
	public void runSimulation() {
		while (!events.isEmpty()) {
			Event next = events.poll();
			
			if(next.time<currentTime)
				throw new IllegalStateException("Events must come in correct order!");
			
			//System.out.println("Time: "+currentTime+" Event job id: "+next.jobID);
			
			switch (next.eventType) {

			// This case is when enough Robots are at the correct location and a Job is completed
			// Then all these Robots will be available for new orders
			case COMPLETEJOB:
				if (isValid(next)) { 
					currentTime = next.time;
					updatePosition(next.robotID);
					robotsPresent.get(next.jobID).add(next.robotID);
					//robotsPresent.set(next.jobID, robotsPresent.get(next.jobID)+1);
					//System.out.println("Time "+currentTime+" Robot "+next.robotID+" arrived.");
					if (robotsPresent.get(next.jobID).size() >= jobs.get(next.jobID).robotsNeeded && !jobsFulfilled.get(next.jobID)) {
						jobsFulfilled.set(next.jobID, true);
						score += currentTime - jobs.get(next.jobID).t;
						//System.out.println("Time: "+currentTime+" Job: "+next.jobID+" is completed.");
						for (Integer i: robotsPresent.get(next.jobID)) {
							robotInfo.get(i).assignJob(null);
						}
						// Call to student code
						strategy.jobFulfilled(jobs.get(next.jobID));
					}
					
				}
				break;
				
			//This case is when the solar power company discovers a new job needed to be done
			case NEWJOB:
				currentTime = next.time;

				// Call to student code
				strategy.registerJob(jobs.get(next.jobID));
				break;
				
			//this case is when a Robot which had a Job assigned now gets new orders
			// the robot gets a new target and will no longer contribute to the original job	
			case ABORTJOB:
				if (robotsPresent.get(next.jobID).contains(next.robotID))
					robotsPresent.get(next.jobID).remove(next.robotID);
				break;
				
			case TARGETREACHED:
				robotInfo.get(next.robotID).setLocation(null);
				strategy.updateOrders();
				break;
				
			default:
				throw new IllegalArgumentException("Unexpected value: " + next.eventType);
			}

		}
	}

	/**
	 * If the robot has received new orders since the event was created it is considered invalid 
	 * @param next - The event that has been triggered
	 * @return
	 */
	private boolean isValid(Event next) {
		return next.checkID == robotInfo.get(next.robotID).getEventId();
	}

	/**
	 * Creates a list of Robots giving access to this model
	 * @return
	 */
	public List<Robot> listRobots() {
		List<Robot> robots = new ArrayList<>();
		for (int i = 0; i < numberOfRobots; i++) {
			robots.add(new Robot(i, this));
		}
		return robots;
	}

	/**
	 * Sum over all jobs, the time from the Job started till it was completed.
	 * @return The score, a positive number Lower number is better score
	 */
	public double score() {
		for(boolean jobDone : jobsFulfilled) {
			if(!jobDone) {
				throw new IllegalStateException("Not all jobs were finished!");
			}
		}
		return score;
	}

	@Override
	public Location getPosition(int id) {
		updatePosition(id);
		return robotInfo.get(id).getLastKnownLocation();
	}

	@Override
	public Job getTarget(int id) {
		return robotInfo.get(id).getAssignedJob();
	}

	@Override
	public void move(int id, Location location) {
		//compute current Position
		updatePosition(id);
		
		//abort old job
		Job oldJob = robotInfo.get(id).getAssignedJob();
		if (oldJob != null){
			events.add(new Event(id, oldJob.id, -1, currentTime, EventType.ABORTJOB));
		}
		
		//assign new
		robotInfo.get(id).setLocation(location);
		
		double eventTime = getEventTime(robotInfo.get(id).getLastKnownLocation(), location);
		events.add(new Event(id, -1, robotInfo.get(id).getEventId(), eventTime,EventType.TARGETREACHED));

	}
	
	/**
	 * @param id - id of the robot
	 */
	private void updatePosition(int id) {
		robotInfo.get(id).updatePosition(currentTime);
	}

	@Override
	public void move(int id, Job job) {
		RobotInfo info = robotInfo.get(id);

		//compute current Position
		info.updatePosition(currentTime);
		
		//abort old job
		Job oldJob = robotInfo.get(id).getAssignedJob();
		if (oldJob != null){
			events.add(new Event(id, oldJob.id, -1, currentTime, EventType.ABORTJOB));
		}
				
		//assign new target (position)
		info.assignJob(job);
		
		//add event for contributing to job
		double eventTime = getEventTime(info.getLastKnownLocation(), job.location);
		events.add(new Event(id, job.id, info.getEventId(), eventTime,EventType.COMPLETEJOB));
		
		/*
		Location loc1 = robotLocations.get(id);
		Location loc2 = job.location;
		System.out.printf("%.2f %.2f %.2f %.2f\n", loc1.x, loc1.y, loc2.x, loc2.y);
		System.out.println(robotLocations.get(id).dist(job.location) / robotSpeed);*/
	}
	
	/**
	 * The robot starts at location from at time currentTime.
	 * This method computes when the Robot reaches the location target
	 * @param from
	 * @param target
	 * @return
	 */
	private double getEventTime(Location from, Location target) {
		return currentTime + from.dist(target) / RobotInfo.robotSpeed;		
	}
	
}