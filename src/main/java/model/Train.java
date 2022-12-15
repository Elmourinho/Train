package model;

import exception.TrainException;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Train {

	private List<Locomotive> locomotives;
	private List<Wagon> wagons;

	private static final int PASSENGER_WEIGHT = 75;
	private static final int PASSENGER_COUNT_PER_CONDUCTOR = 50;

	public Train(List<Locomotive> locomotives) {
		this(locomotives, new LinkedList<>());
	}

	public Train(List<Locomotive> locomotives, List<Wagon> wagons) {
		if (locomotives == null || locomotives.isEmpty()) {
			throw new TrainException("At least one locomotive should exist");
		}
		this.locomotives = locomotives;
		this.wagons = wagons;
	}

	public double getEmptyWeight() {
		double locomotiveEmptyWeightSum = locomotives.stream().mapToDouble(Locomotive::getWeight).sum();
		double wagonEmptyWeightSum = wagons.stream().mapToDouble(Wagon::getWeight).sum();
		return locomotiveEmptyWeightSum + wagonEmptyWeightSum;
	}

	public Integer getMaxPassengerNumber() {
		int locomotiveMaxPassengerSum = locomotives.stream().mapToInt(Locomotive::getMaxPassengerNumber).sum();
		int wagonMaxPassengerSum = wagons.stream().mapToInt(Wagon::getMaxPassengerNumber).sum();
		return locomotiveMaxPassengerSum + wagonMaxPassengerSum;
	}

	public double getMaxWeightForGoods() {
		double locomotiveMaxWeightOfGoods = locomotives.stream().mapToDouble(Locomotive::getMaxGoodWeight).sum();
		double wagonMaxWeightOfGoods = wagons.stream().mapToDouble(Wagon::getMaxGoodWeight).sum();
		return locomotiveMaxWeightOfGoods + wagonMaxWeightOfGoods;
	}

	public double getMaxPayload() {
		return getMaxPassengerNumber() * PASSENGER_WEIGHT + getMaxWeightForGoods();
	}

	public double getMaxTotalWeight() {
		return getEmptyWeight() + getMaxPayload();
	}

	public int getMaxConductorNumber() {
		return (getMaxPassengerNumber() + PASSENGER_COUNT_PER_CONDUCTOR - 1) / PASSENGER_COUNT_PER_CONDUCTOR;
	}

	public void addLocomotive(Locomotive locomotive) {
		boolean isPresent = locomotives
				.stream()
				.anyMatch(locomotive1 -> locomotive1.getSerialNumber().equals(locomotive.getSerialNumber()));
		if (isPresent) {
			throw new TrainException("This locomotive already exists in the train");
		}
		locomotives.add(locomotive);
	}

	public void addWagon(Wagon wagon) {
		boolean isPresent = wagons
				.stream()
				.anyMatch(wagon1 -> wagon1.getSerialNumber().equals(wagon.getSerialNumber()));
		if (isPresent) {
			throw new TrainException("This wagon already exists in the train");
		}
		wagons.add(wagon);
	}

	public boolean isDrivable() {
		double locomotiveMaxEffortSum = locomotives.stream().mapToDouble(Locomotive::getEffort).sum();
		return locomotiveMaxEffortSum > getMaxTotalWeight();
	}
}
