package compulsory.system;

/**
 * EventType describes which kind of event occurs.
 * Events are times when an action is needed from the Strategy class.
 * 
 * NEWJOB - a new job has been identified
 * COMPLETEJOB - a robot reaches a job, if the job can be completed by 1 robot it will be completed, otherwise robot waits for more robots to arrive 
 * ABORTJOB - job is aborted, only has an effect if robot is currently waiting to complete job
 * TARGETREACHED - a robot was given a target location that was not a Job and has now reached that point ready for new orders
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public enum EventType {
	NEWJOB,
	COMPLETEJOB,
	ABORTJOB,
	TARGETREACHED
}
