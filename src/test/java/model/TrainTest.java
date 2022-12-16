package model;

import static org.junit.jupiter.api.Assertions.*;

import exception.TrainException;
import java.math.BigDecimal;
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
		BigDecimal emptyWeight = train.getEmptyWeight();
		assertEquals(BigDecimal.valueOf(120 + 140 + 130 + 150), emptyWeight);
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
		BigDecimal maxWeightForGoods = train.getMaxWeightForGoods();
		assertEquals(BigDecimal.valueOf(200 + 220 + 210 + 230), maxWeightForGoods);
	}

	@Test
	void shouldCalculateMaxPayloadProperly() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		BigDecimal maxPayload = train.getMaxPayload();
		assertEquals(BigDecimal.valueOf((60 + 80 + 70 + 90) * 75 + 200 + 220 + 210 + 230), maxPayload);
		//passenger number * passenger weight + max weight for goods
	}

	@Test
	void shouldCalculateMaxTotalWeightProperly() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		BigDecimal maxTotalWeight = train.getMaxTotalWeight();
		assertEquals(BigDecimal.valueOf((120 + 140 + 130 + 150) + (60 + 80 + 70 + 90) * 75 + 200 + 220 + 210 + 230),
				maxTotalWeight);
		//empty weights + passenger number * passenger weight + max weight for goods
	}

	@Test
	void shouldCalculateMaxConductorNumberProperly() {
		List<Locomotive> locomotiveList1 = new ArrayList<>();
		Locomotive locomotive1 =
				new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 0, BigDecimal.ONE, "");
		locomotiveList1.add(locomotive1);

		Train trainWithoutPassenger = new Train(locomotiveList1);
		int maxConductorNumber1 = trainWithoutPassenger.getMaxConductorNumber();
		assertEquals(0, maxConductorNumber1);

		List<Locomotive> locomotiveList2 = new ArrayList<>();
		Locomotive locomotive2 =
				new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 1, BigDecimal.ONE, "");
		locomotiveList2.add(locomotive2);

		Train trainWithOnePassenger = new Train(locomotiveList2);
		int maxConductorNumber2 = trainWithOnePassenger.getMaxConductorNumber();
		assertEquals(1, maxConductorNumber2);

		List<Locomotive> locomotiveList3 = new ArrayList<>();
		Locomotive locomotive3 =
				new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 60, BigDecimal.ONE, "");
		locomotiveList3.add(locomotive3);

		Train trainWith60Passenger = new Train(locomotiveList3);
		int maxConductorNumber3 = trainWith60Passenger.getMaxConductorNumber();
		assertEquals(2, maxConductorNumber3);
	}

	@Test
	void shouldAddOnlyUniqueLocomotives() {
		List<Locomotive> locomotiveList = new ArrayList<>();
		Locomotive locomotive1 =
				new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 10, BigDecimal.ONE, "unique1");
		locomotiveList.add(locomotive1);
		Train train = new Train(locomotiveList);

		Exception exception = assertThrows(TrainException.class, () -> train.addLocomotive(locomotive1));

		String expectedMessage = "This locomotive already exists in the train";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

		Locomotive locomotive2 =
				new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 10, BigDecimal.ONE, "unique2");
		train.addLocomotive(locomotive2);
		assertEquals(2, train.getLocomotives().size());
	}

	@Test
	void shouldAddOnlyUniqueWagons() {
		List<Locomotive> locomotiveList = new ArrayList<>();
		Locomotive locomotive1 =
				new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 10, BigDecimal.ONE, "unique1");
		locomotiveList.add(locomotive1);

		List<Wagon> wagonList = new ArrayList<>();
		Wagon wagon1 = new Wagon(BigDecimal.ONE, BigDecimal.ONE, 10, BigDecimal.ONE, "uniqueWagon1");
		wagonList.add(wagon1);

		Train train = new Train(locomotiveList, wagonList);

		Exception exception = assertThrows(TrainException.class, () -> train.addWagon(wagon1));

		String expectedMessage = "This wagon already exists in the train";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

		Wagon wagon2 = new Wagon(BigDecimal.ZERO, BigDecimal.ZERO, 0, BigDecimal.ZERO, "uniqueWagon2");
		train.addWagon(wagon2);
		assertEquals(2, train.getWagons().size());
	}

	@Test
	void shouldCheckIfTrainIsDrivable() {
		List<Locomotive> locomotiveList = makeDummyLocomotiveList();
		List<Wagon> wagonList = makeDummyWagonList();
		Train train = new Train(locomotiveList, wagonList);
		assertFalse(train.isDrivable()); // 1000 < 23900

		locomotiveList.get(0).changeEffort(BigDecimal.valueOf(15000));
		locomotiveList.get(1).changeEffort(BigDecimal.valueOf(15000));
		assertTrue(train.isDrivable()); // 3000 > 23900
	}

	private List<Locomotive> makeDummyLocomotiveList() {
		List<Locomotive> locomotiveList = new ArrayList<>();

		Locomotive locomotive1 =
				new Locomotive(BigDecimal.valueOf(120), BigDecimal.valueOf(2), BigDecimal.valueOf(500), 60,
						BigDecimal.valueOf(200), "");
		locomotiveList.add(locomotive1);

		Locomotive locomotive2 =
				new Locomotive(BigDecimal.valueOf(140), BigDecimal.valueOf(2), BigDecimal.valueOf(500), 80,
						BigDecimal.valueOf(220), "");
		locomotiveList.add(locomotive2);

		return locomotiveList;
	}

	private List<Wagon> makeDummyWagonList() {
		List<Wagon> wagonList = new ArrayList<>();

		Wagon wagon1 = new Wagon(BigDecimal.valueOf(130), BigDecimal.valueOf(2), 70, BigDecimal.valueOf(210), "");
		wagonList.add(wagon1);

		Wagon wagon2 = new Wagon(BigDecimal.valueOf(150), BigDecimal.valueOf(2), 90, BigDecimal.valueOf(230), "");
		wagonList.add(wagon2);

		return wagonList;
	}
}