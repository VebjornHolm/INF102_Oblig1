package compulsory1.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import compulsory.system.Model;
import compulsory1.management.IStrategy;


/*
 * Tests for which testcases your strategy beats both random and closest
 */

public class BetterStrategyTest {
	IStrategy random;
	IStrategy closest;
	IStrategy better;
	
	@BeforeEach
	void setUp() throws Exception {
		/**
		 *  Insert your own classes here
		 *  random = new RandomStrategy();
		 *  closest = new ClosestStrategy();
		 *  better = new BetterStrategy();
		 */
	}
	
	@Test
	void testBetterOnInput1() throws Exception{
		String inputFile = "input/01.in";
		Model rmodel = new Model(inputFile, random);
		Model cmodel = new Model(inputFile, closest);
		Model bmodel = new Model(inputFile, better);
		random.registerRobots(rmodel.listRobots());
		closest.registerRobots(cmodel.listRobots());
		better.registerRobots(bmodel.listRobots());
		rmodel.runSimulation();
		cmodel.runSimulation();
		bmodel.runSimulation();
		assertTrue(rmodel.score() > bmodel.score() && cmodel.score() > bmodel.score());
	}
	
	@Test
	void testBetterOnInput2() throws Exception{
		String inputFile = "input/02.in";
		Model rmodel = new Model(inputFile, random);
		Model cmodel = new Model(inputFile, closest);
		Model bmodel = new Model(inputFile, better);
		random.registerRobots(rmodel.listRobots());
		closest.registerRobots(cmodel.listRobots());
		better.registerRobots(bmodel.listRobots());
		rmodel.runSimulation();
		cmodel.runSimulation();
		bmodel.runSimulation();
		assertTrue(rmodel.score() > bmodel.score() && cmodel.score() > bmodel.score());
	}
	
	@Test
	void testBetterOnInput3() throws Exception{
		String inputFile = "input/03.in";
		Model rmodel = new Model(inputFile, random);
		Model cmodel = new Model(inputFile, closest);
		Model bmodel = new Model(inputFile, better);
		random.registerRobots(rmodel.listRobots());
		closest.registerRobots(cmodel.listRobots());
		better.registerRobots(bmodel.listRobots());
		rmodel.runSimulation();
		cmodel.runSimulation();
		bmodel.runSimulation();
		assertTrue(rmodel.score() > bmodel.score() && cmodel.score() > bmodel.score());
	}
	
	@Test
	void testBetterOnInput4() throws Exception{
		String inputFile = "input/04.in";
		Model rmodel = new Model(inputFile, random);
		Model cmodel = new Model(inputFile, closest);
		Model bmodel = new Model(inputFile, better);
		random.registerRobots(rmodel.listRobots());
		closest.registerRobots(cmodel.listRobots());
		better.registerRobots(bmodel.listRobots());
		rmodel.runSimulation();
		cmodel.runSimulation();
		bmodel.runSimulation();
		assertTrue(rmodel.score() > bmodel.score() && cmodel.score() > bmodel.score());
	}
	
	@Test
	void testBetterOnInput5() throws Exception{
		String inputFile = "input/05.in";
		Model rmodel = new Model(inputFile, random);
		Model cmodel = new Model(inputFile, closest);
		Model bmodel = new Model(inputFile, better);
		random.registerRobots(rmodel.listRobots());
		closest.registerRobots(cmodel.listRobots());
		better.registerRobots(bmodel.listRobots());
		rmodel.runSimulation();
		cmodel.runSimulation();
		bmodel.runSimulation();
		assertTrue(rmodel.score() > bmodel.score() && cmodel.score() > bmodel.score());
	}
	
	@Test
	void testBetterOnInput6() throws Exception{
		String inputFile = "input/06.in";
		Model rmodel = new Model(inputFile, random);
		Model cmodel = new Model(inputFile, closest);
		Model bmodel = new Model(inputFile, better);
		random.registerRobots(rmodel.listRobots());
		closest.registerRobots(cmodel.listRobots());
		better.registerRobots(bmodel.listRobots());
		rmodel.runSimulation();
		cmodel.runSimulation();
		bmodel.runSimulation();
		assertTrue(rmodel.score() > bmodel.score() && cmodel.score() > bmodel.score());
	}
}