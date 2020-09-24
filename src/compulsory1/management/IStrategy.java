package compulsory1.management;

import java.util.List;

/**
 * This interface describes the 3 methods you need to implement in order to design a strategy for the robots
 * 
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public interface IStrategy {
	
	/**
	 * This method informs the strategy of which robots are available.
	 * Each robot is placed at a starting position and can be moved with the move commands.
	 * 
	 * @param robots - list of available robots
	 */
	public void registerRobots(List<Robot> robots);
	
	/**
	 * Whenever a solar panel failure is detected this method is called to inform the strategy of the job.
	 * The Strategy needs to make sure to at some point send robots to this job.
	 * There is no requirement to the order of when the jobs are completed 
	 * so if the robot best suited for this job is busy you may save this job and assign robots at a later stage.
	 * 
	 * @param job
	 */
	public void registerJob(Job job);

	/**
	 * Whenever a job is completed this method is called. It assures that all robots used for this job is free
	 * and the strategy now can use these robots for another job.
	 * 
	 * @param job - the job that was completed
	 */
	public void jobFulfilled(Job job);

	public default void updateOrders() {
		return;
	}
}
