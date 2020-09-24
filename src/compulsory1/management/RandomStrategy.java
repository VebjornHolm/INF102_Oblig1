package compulsory1.management;

import java.util.ArrayList;
import java.util.List;

public class RandomStrategy implements IStrategy{

	List<Robot> freeRobotList = new ArrayList<Robot>();
	List<Robot> allRobotList = new ArrayList<Robot>();
	List<Job> jobList = new ArrayList<Job>();
	List<Job> hardJobList = new ArrayList<Job>();

	@Override
	public void registerRobots(List<Robot> robots) {

		//Add alle robotene fra "robots" til "allRobotList" listen og de ledige til "freeRobotList" listen
		for (Robot robot : robots) {
			allRobotList.add(robot); // O(n^2)
			if (!robot.isBusy()) {
				freeRobotList.add(robot); // O(n^2)
			}
		}
	}


	@Override
	public void registerJob(Job job) {

		//Add alle jobbene. som ikke allerede er der, til listen "jobList"
		if (!jobList.contains(job)) {
			jobList.add(job); // O(n)
		}

		//Sjekk at det er nok roboter i listen "freeRobotList" for jobben,
		if (freeRobotList.size() >= job.robotsNeeded) {
			//Add første robot fra "freeRobotList" på jobben, og yeet den fra listen
			for (int i = 0; i < job.robotsNeeded; i++) {
				freeRobotList.get(0).move(job);
				freeRobotList.remove(0); // O(n^2)
			}
		}

		//Jobber vi ikke har capacity til å fullføre blir addet til listen "hardJobList"
		else {
			if (!hardJobList.contains(job))
				hardJobList.add(job); // O(n)
		}
	}

	@Override
	public void jobFulfilled(Job job) {

		//Remove alle fullførte jobber fra listene "jobList" og "hardJobList"
		jobList.remove(job);  // O(n)
		hardJobList.remove(job);  // O(n)

		//Sjekk om noen roboter i "allRobotList" er ledige, og add de til "freeRobotList"
		for (Robot robot : allRobotList) {
			if (!robot.isBusy() && !freeRobotList.contains(robot)) {
				freeRobotList.add(robot);  // O(n^2)
			}
		}

		//Søk gjennom "hardJobList" og send de til registerJob()
		for (Job j : hardJobList) {
			registerJob(j);
		}
	}
}