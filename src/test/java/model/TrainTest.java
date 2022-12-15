package model;

import static org.junit.jupiter.api.Assertions.*;

import exception.TrainException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TrainTest {

	@Test
	void shouldThrowException_whenCreateTrainWithNullLocomotives() {
		Exception exception = assertThrows(TrainException.class, () -> new Train(null));

		String expectedMessage = "At least one locomotive should exist";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowException_whenCreateTrainWithEmptyLocomotives() {
		Exception exception = assertThrows(TrainException.class, () -> new Train(new ArrayList<>()));
		String expectedMessage = "At least one locomotive should exist";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldCalculateEmptyWeightProperly() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		double emptyWeight = train.getEmptyWeight();
		assertEquals(120 + 140 + 130 + 150, emptyWeight);
	}

	@Test
	void shouldCalculateMaxPassengerNumberProperly() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		int maxPassengerNumber = train.getMaxPassengerNumber();
		assertEquals(60 + 80 + 70 + 90, maxPassengerNumber);
	}

	@Test
	void shouldCalculateMaxWeightForGoodsProperly() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		double maxWeightForGoods = train.getMaxWeightForGoods();
		assertEquals(200 + 220 + 210 + 230, maxWeightForGoods);
	}

	@Test
	void shouldCalculateMaxPayloadProperly() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		double maxPayload = train.getMaxPayload();
		assertEquals((60 + 80 + 70 + 90) * 75 + 200 + 220 + 210 + 230, maxPayload);
		//passenger number * passenger weight + max weight for goods
	}

	@Test
	void shouldCalculateMaxTotalWeightProperly() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		double maxTotalWeight = train.getMaxTotalWeight();
		assertEquals((120 + 140 + 130 + 150) + (60 + 80 + 70 + 90) * 75 + 200 + 220 + 210 + 230, maxTotalWeight);
		//empty weights + passenger number * passenger weight + max weight for goods
	}

	@Test
	void shouldCalculateMaxConductorNumberProperly() {
		List<Locomotive> locomotiveList1 = new ArrayList<>();
		Locomotive locomotive1 = new Locomotive();
		locomotive1.setMaxPassengerNumber(0);
		locomotiveList1.add(locomotive1);

		Train trainWithoutPassenger = new Train(locomotiveList1);
		int maxConductorNumber1 = trainWithoutPassenger.getMaxConductorNumber();
		assertEquals(0, maxConductorNumber1);

		List<Locomotive> locomotiveList2 = new ArrayList<>();
		Locomotive locomotive2 = new Locomotive();
		locomotive2.setMaxPassengerNumber(1);
		locomotiveList2.add(locomotive2);

		Train trainWithOnePassenger = new Train(locomotiveList2);
		int maxConductorNumber2 = trainWithOnePassenger.getMaxConductorNumber();
		assertEquals(1, maxConductorNumber2);

		List<Locomotive> locomotiveList3 = new ArrayList<>();
		Locomotive locomotive3 = new Locomotive();
		locomotive3.setMaxPassengerNumber(60);
		locomotiveList3.add(locomotive3);

		Train trainWith60Passenger = new Train(locomotiveList3);
		int maxConductorNumber3 = trainWith60Passenger.getMaxConductorNumber();
		assertEquals(2, maxConductorNumber3);
	}

	@Test
	void shouldAddOnlyUniqueLocomotives() {
		List<Locomotive> locomotiveList = new ArrayList<>();
		Locomotive locomotive1 = new Locomotive();
		locomotive1.setSerialNumber("unique1");
		locomotiveList.add(locomotive1);
		Train train = new Train(locomotiveList);

		Exception exception = assertThrows(TrainException.class, () -> train.addLocomotive(locomotive1));

		String expectedMessage = "This locomotive already exists in the train";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

		Locomotive locomotive2 = new Locomotive();
		locomotive1.setSerialNumber("unique2");
		train.addLocomotive(locomotive2);
		assertEquals(2, train.getLocomotives().size());
	}

	@Test
	void shouldAddOnlyUniqueWagons() {
		List<Locomotive> locomotiveList = new ArrayList<>();
		Locomotive locomotive1 = new Locomotive();
		locomotive1.setSerialNumber("unique1");
		locomotiveList.add(locomotive1);

		List<Wagon> wagonList = new ArrayList<>();
		Wagon wagon1 = new Wagon();
		wagon1.setSerialNumber("uniqueWagon1");
		wagonList.add(wagon1);

		Train train = new Train(locomotiveList, wagonList);

		Exception exception = assertThrows(TrainException.class, () -> train.addWagon(wagon1));

		String expectedMessage = "This wagon already exists in the train";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

		Wagon wagon2 = new Wagon();
		wagon2.setSerialNumber("uniqueWagon2");
		train.addWagon(wagon2);
		assertEquals(2, train.getWagons().size());
	}

	@Test
	void shouldCheckIfTrainIsDrivable() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		assertFalse(train.isDrivable()); // 1000 < 23900

		locomotiveList.get(0).setEffort(15000);
		locomotiveList.get(1).setEffort(15000);
		assertTrue(train.isDrivable()); // 3000 > 23900
	}

	private List<Locomotive> makeDummyLocomotiveList() {
		List<Locomotive> locomotiveList = new ArrayList<>();

		Locomotive locomotive1 = new Locomotive();
		locomotive1.setWeight(120);
		locomotive1.setMaxPassengerNumber(60);
		locomotive1.setMaxGoodWeight(200);
		locomotive1.setEffort(500);
		locomotiveList.add(locomotive1);

		Locomotive locomotive2 = new Locomotive();
		locomotive2.setWeight(140);
		locomotive2.setMaxPassengerNumber(80);
		locomotive2.setMaxGoodWeight(220);
		locomotive2.setEffort(500);
		locomotiveList.add(locomotive2);

		return locomotiveList;
	}

	private List<Wagon> makeDummyWagonList() {
		List<Wagon> wagonList = new ArrayList<>();

		Wagon wagon1 = new Wagon();
		wagon1.setWeight(130);
		wagon1.setMaxPassengerNumber(70);
		wagon1.setMaxGoodWeight(210);
		wagonList.add(wagon1);

		Wagon wagon2 = new Wagon();
		wagon2.setWeight(150);
		wagon2.setMaxPassengerNumber(90);
		wagon2.setMaxGoodWeight(230);
		wagonList.add(wagon2);

		return wagonList;
	}
}