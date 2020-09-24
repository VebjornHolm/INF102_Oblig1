package compulsory.system;

import compulsory1.management.Job;
import compulsory1.management.Location;

/**
 * This interface describes a class that keeps track of the state of all robots.
 * A centralized system keeps track of all robots and their positions and which Job they are doing next.
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public interface RobotStateManager {

	/**
	 * Returns the position of the Robot with given ID
	 */
	public Location getPosition(int id);

	/**
	 * Return the Job this Robot is moving towards or waiting to complete
	 */
	public Job getTarget(int id);
	
	/**
	 * Starts moving the Robot with given ID towards the given Location
	 */
	public void move(int id, Location location);

	/**
	 * Starts moving the robot towards the given Job
	 * The Robot is now assigned to this Job and will keep being assigned
	 * to this Job until the Job is completed or the Robot receives a different command
	 */
	public void move(int id, Job job);
}
