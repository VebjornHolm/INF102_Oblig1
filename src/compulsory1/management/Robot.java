package compulsory1.management;

import compulsory.system.RobotStateManager;

/**
 * Robot able to move around performing jobs.
 * This class keeps track of the position and activity of a Robot.
 * It also works as a communcation interface where Strategy implementations
 * can send commands through the robot to the system modelling the Simulation.
 * 
 * For the runtime information N: numberOfRobots M: numberOfJobs
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public class Robot {
	private RobotStateManager state;
	private final int id;
	
	/**
	 * Contructor for creating a new robot.
	 * All robots are created by the system, 
	 * there should be no need to create new robots
	 * 
	 * @param id
	 * @param state
	 */
	public Robot(int id, RobotStateManager state){
		if (state == null) throw new NullPointerException();
		this.id = id;
		this.state = state;
	}
	
	/**
	 * This method tells the robot to move towards a Job location
	 * If the same robot has received previous move commands that are not completed
	 * these commands will be cancelled.
	 * Once a move command is given the Robot will immediately find the optimal route to the job location.
	 * Robots always move in a straight line between the current location and their target.
	 * 
	 * runtime: O(log M)
	 *  
	 * @param job - the job you want the robot to focus on
	 */
	public void move(Job job) {
//		System.out.println(this+" moving to "+job);
		state.move(id, job);
	}
	
	/**
	 * This method tells the robot to start moving towards a location 
	 * The Robot will continue to move towards this location until 
	 * either it reaches the location or it gets stopped by another command.
	 * 
	 * If the robot had previously received commands that were not yet complete
	 * these commands will be cancelled.
	 * 
	 * runtime: O(log M) 
	 * 
	 * @param location
	 */
	public void move(Location location) {
		//System.out.println(this+" moving to "+location);
		state.move(id, location);
	}
	
	/**
	 * Gets the last known position of the robot
	 * 
	 * runtime: O(1)
	 * 
	 * @return
	 */
	public Location getLocation() {
		return state.getPosition(id);
	}
	
	/**
	 * The Robot is only able to keep orders for one Job at the time.
	 * The Robot is continuously moving towards the location of that Job 
	 * and once reached there it waits until the job is done.
	 * When the job is done the system sets the job to null indicating
	 * that the robot is free to move.
	 * 
	 * If robot is moving towards a job or is waiting for a job to complete that job will be returned,
	 * if robot is either moving to a point not related to a job or waiting for orders null is returned
	 * 
	 * runtime: O(1)
	 * 
	 * @return null if robot is free, otherwise the job this robot is working towards.
	 * 
	 */
	public Job getJob() {
		return state.getTarget(id);
	}
	
	/**
	 * Check if the robot is busy with a job
	 * 
	 * runtime: O(1)
	 * 
	 * @return true if the robot is busy, false otherwise
	 */
	public boolean isBusy() {
		return getJob()!=null;
	}
	
	@Override
	public String toString() {
		return "Robot "+ id;
	}
}
