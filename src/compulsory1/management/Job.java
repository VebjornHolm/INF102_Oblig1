package compulsory1.management;

/**
 * This class describes a Job needed to be done by one or more robots.
 * In order to complete a job the required number of robots needs 
 * to be at the job location at the same time.
 * Once the required number of robots are in place we consider the job done immediately.
 * 
 * All variables of Job are final meaning that they can not be changed once they have been initialized
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public class Job {
	public final Location location;
	public final int robotsNeeded, id;
	public final double t;
	
	/**
	 * Constructor creates a new job
	 * 
	 * @param location - the location which robots are needed to go to
	 * @param id - id just used for identifying a job in the system
	 * @param t - time the job was registered in the system
	 * @param robotsNeeded - number of robots that need to be at the locations at once to do the job
	 */
	public Job(Location location, int id, double t, int robotsNeeded){
		this.location = location;
		this.id = id;
		this.t = t;
		this.robotsNeeded = robotsNeeded;
	}
	
	@Override
		public String toString() {
			return "Job start time: " + t+" at "+location;
		}
}
