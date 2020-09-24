package compulsory.system;

/**
 * Events represent times when an action is needed from the Strategy class.
 * An event is either when a task occurs an Job is created for the robots to do,
 * or when a Job is completed. The type of event is described by an EventType.
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public class Event implements Comparable<Event>{
	public final int robotID, jobID, checkID;
	public final double time;
	public final EventType eventType;
	
	Event(int robotID, int jobID, int checkID, double t, EventType eventType){
		this.robotID = robotID;
		this.jobID = jobID;
		this.checkID = checkID;
		this.time = t;
		this.eventType = eventType;
	}
	
	@Override
	public int compareTo(Event e) {
		if (this.time == e.time) 
			return eventTypeToInt(this.eventType) - eventTypeToInt(e.eventType);
		return (this.time < e.time ? -1 : 1);
	}
	
	private int eventTypeToInt(EventType type) {
		switch (type) {
		case ABORTJOB: return 0;
		case TARGETREACHED: return 1;
		case COMPLETEJOB: return 2;
		case NEWJOB: return 3;
		default: return -1;
		}
	}
}
