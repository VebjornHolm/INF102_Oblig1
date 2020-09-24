package compulsory1.management;

import java.util.ArrayList;
import java.util.List;

public class ClosestStrategy implements IStrategy{

	List<Robot> allRobots = new ArrayList<>();
	List<Robot> freeRobotList = new ArrayList<Robot>();
	List<Robot> allRobotList = new ArrayList<Robot>();
	List<Job> jobsPending = new ArrayList<Job>();
	List<Robot> busyBots = new ArrayList<Robot>();
	//List<Job> hardJobList = new ArrayList<Job>();
	@Override
	public void registerRobots(List<Robot> robots) {
		//Add alle robotene fra "robots" til "allRobotList" listen og de ledige til "freeRobotList" listen. Likt som i Randomstrategy
		for (Robot robot : robots) {
			allRobotList.add(robot); // O(n^2)
			if (!robot.isBusy()) {
				freeRobotList.add(robot); // O(n^2)
			}
		}
	}

	public void assignClosestBot() {
		List<Robot> sentBots = new ArrayList<Robot>();
		Job newJob = jobsPending.get(0);
		if (freeRobotList.size() < newJob.robotsNeeded){
			return;
		}
		for (int i = 0; i <= newJob.robotsNeeded; i++) {
			System.out.println(freeRobotList.size() + " " + newJob.id + " " + newJob.robotsNeeded + " " + sentBots);
			Robot closestBot = freeRobotList.get(0);
			closestBot.move(newJob);
			freeRobotList.remove(0);
			sentBots.add(closestBot); // O(n^2)
			if (newJob.robotsNeeded == sentBots.size()){
				jobsPending.remove(0); // O(n^2)
			}
			busyBots.addAll(sentBots);
		}
	}

	@Override
	public void registerJob(Job job) {
		jobsPending.add(job); // O(n)
		assignClosestBot();
	}

	@Override
	public void jobFulfilled(Job job) {
		for (Robot robot : allRobots) {
			if (robot.getLocation() == job.location) {
				freeRobotList.add(robot); // O(n^2)
				busyBots.remove(robot); // O(n^2)
			}
		}
		jobsPending.remove(job);
		if (jobsPending.size() > 0) // sørger for at det fortsatt er ledige bots til å få en ny jobb
			assignClosestBot();
	}

}
