package compulsory1.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import compulsory.system.Model;
import compulsory1.management.ClosestStrategy;
import compulsory1.management.IStrategy;
import compulsory1.management.Job;
import compulsory1.management.Location;

class ClosestTest {

	IStrategy random;
	
	@BeforeEach
	void setUp() throws Exception {
		random = new ClosestStrategy();
	}
	
	Model make10Job1RobotModel() {
		ArrayList<Location> startLocation = new ArrayList<>();
		ArrayList<Job> jobs = new ArrayList<>();
		startLocation.add(new Location(0, 0));
		for (int i = 0; i < 10; i++) jobs.add(new Job(new Location(i+1, i+1), i, 1e-10*i, 1));
		return new Model(startLocation, jobs, random);
	}
	
	Model make2Job1RobotModel() {
		ArrayList<Location> startLocation = new ArrayList<>();
		ArrayList<Job> jobs = new ArrayList<>();
		startLocation.add(new Location(0, 0));
		jobs.add(new Job(new Location(1, 1), 0, 1d, 1));
		jobs.add(new Job(new Location(2, 2), 1, 10d, 1));
		return new Model(startLocation, jobs, random);		
	}

	Model make1DoubleJob2RobotModel() {
		ArrayList<Location> startLocation = new ArrayList<>();
		ArrayList<Job> jobs = new ArrayList<>();
		startLocation.add(new Location(0, 0));
		startLocation.add(new Location(1, 1));
		jobs.add(new Job(new Location(2, 2), 0, 1d, 2));
		return new Model(startLocation, jobs, random);		
	}
	
	Model make2DoubleJob2RobotModel() {
		ArrayList<Location> startLocation = new ArrayList<>();
		ArrayList<Job> jobs = new ArrayList<>();
		startLocation.add(new Location(0, 1));
		startLocation.add(new Location(10, 0));
		jobs.add(new Job(new Location(10, 1), 0, 1d, 2));
		jobs.add(new Job(new Location(12, 1), 1, 5d, 2));
		return new Model(startLocation, jobs, random);		
	}
	
	Model make4Job4RobotModel() {
		ArrayList<Location> startLocation = new ArrayList<>();
		ArrayList<Job> jobs = new ArrayList<>();
		startLocation.add(new Location(1, 1));
		startLocation.add(new Location(1, 2));
		startLocation.add(new Location(2, 1));
		startLocation.add(new Location(2, 2));
		jobs.add(new Job(new Location(0, 0), 0, 1d, 1));
		jobs.add(new Job(new Location(3, 0), 1, 1d, 1));
		jobs.add(new Job(new Location(0, 3), 2, 1d, 1));
		jobs.add(new Job(new Location(3, 3), 3, 1d, 1));
		return new Model(startLocation, jobs, random);		
	}
	
	@Test
	void testClosestStrategyOn10Job1RobotModel(){
		Model model = make10Job1RobotModel();
		random.registerRobots(model.listRobots());
		model.runSimulation();
		assertEquals(Math.sqrt(2)*((10)*(11)/2),model.score(),0.001);
	}

	@Test
	void testClosestStrategyOn2Job1RobotModel(){
		Model model = make2Job1RobotModel();
		random.registerRobots(model.listRobots());
		model.runSimulation();
		assertEquals(2.828,model.score(),0.001);
	}
	
	@Test
	void testClosestStrategyOn1DoubleJob2RobotModel(){
		Model model = make1DoubleJob2RobotModel();
		random.registerRobots(model.listRobots());
		model.runSimulation();
		assertEquals(2.828,model.score(),0.001);
	}

	@Test
	void testClosestStrategyOn2DoubleJob2RobotModel(){
		Model model = make2DoubleJob2RobotModel();
		random.registerRobots(model.listRobots());
		model.runSimulation();
		assertEquals(18d,model.score(),0.001);
	}

	@Test
	void testClosestStrategyOn4Job4RobotModel(){
		Model model = make4Job4RobotModel();
		random.registerRobots(model.listRobots());
		model.runSimulation();
		assertEquals(4*1.414,model.score(),0.001);
	}
}