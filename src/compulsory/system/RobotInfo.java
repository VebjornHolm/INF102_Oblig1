package compulsory.system;

import compulsory1.management.Job;
import compulsory1.management.Location;

/**
 * This class stores information about a robot needed by the Simulation.
 * Each Robot has a target they are moving towards, if target == null
 * the robot is awaiting orders.
 * The simulation is updating Location irregularly and the last update is
 * stored in lastUpdateTime and lastKnownLocation
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public class RobotInfo {
	public static final double robotSpeed = 1d; // How far each robot moves in a unit of time
	public final int robotId;
	private Job assignedJob;
	private double lastUpdateTime;
	private Location lastKnownLocation;
	private Location target;
	private int eventId;
	
	/**
	 * Constructor creating a RobotInfo with default
	 * @param robotId
	 * @param start
	 */
	public RobotInfo(int robotId, Location start) {
		this.robotId = robotId;
		this.assignedJob = null;
		lastUpdateTime = 0d;
		lastKnownLocation = start;
		eventId = 0;
		target = null;
	}

	/**
	 * @return the job this Robot is assigned to do, null if robot is free
	 */
	public Job getAssignedJob() {
		return assignedJob;
	}
	
	/**
	 * This method is called to add information that this Robot
	 * has been assigned a Job to do and is moving towards.
	 * If this Robot already was assigned to do another Job that Job is aborted
	 * if assignedJob == null it means the robot is waiting for orders
	 * @param job - The Job this Robot should do
	 * @return The Job this Robot had previously
	 */
	public Job assignJob(Job job) {
		Job previousJob = assignedJob;
		assignedJob = job;
		if(job == null)
			target = null;
		else
			target = job.location;
		eventId++;
		return previousJob;
	}

	/**
	 * Sends robot to a new location.
	 * If input is null it means stop the robot and await new orders
	 * @param location
	 */
	public void setLocation(Location location) {
		target = location;
		
		//if input is null it means stop robot and wait
		if(location == null) {
			eventId++;
		}
		
		//abandon job and go to new location
		if(assignedJob != null && !assignedJob.location.equals(location)) {
			assignedJob = null;
			eventId++;
		}
	}
	
	/**
	 * Moves the robot to the position it will be at currentTime
	 * Robot can be standing still waiting, reaching the target 
	 * before currentTime or still be moving at currentTime.
	 * If robot already has a job and location sends it to a different location
	 * the robot will abandon the job
	 * 
	 * @param currentTime
	 */
	public void updatePosition(double currentTime) {
		
		//update time
		double deltaTime = currentTime - lastUpdateTime;
		lastUpdateTime = currentTime;
	
		// No target
		if (target == null) {
			return; // Robot is stationary waiting for orders or for job to complete
		}
		
		double distance = lastKnownLocation.dist(target);
		double deltaDistance = deltaTime*robotSpeed;
		
		// Will reach target
		if (distance-0.0001 <= deltaDistance) {
			lastKnownLocation = target;
			target = null;
			return;
		}
		
		// Move some distance
		Location newLocation = new Location(
				lastKnownLocation.x + (target.x-lastKnownLocation.x)*deltaDistance/distance,
				lastKnownLocation.y + (target.y-lastKnownLocation.y)*deltaDistance/distance);
		//System.out.println("Moved from "+lastKnownLocation+" to "+newLocation);
		lastKnownLocation = newLocation;
	}

	/**
	 * Last known location will always be the location the robot was at lastUpdateTime
	 */
	public Location getLastKnownLocation() {
		return lastKnownLocation;
	}

	/**
	 * Returns the eventId counter for this robot.
	 * Each time this robot changes target this counter is increased
	 * @return
	 */
	public int getEventId() {
		return eventId;
	}

}
