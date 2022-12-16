package model;

import exception.TrainException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

@Getter
public class Train {

	private static final int PASSENGER_WEIGHT = 75;
	private static final int PASSENGER_COUNT_PER_CONDUCTOR = 50;

	private final List<Locomotive> locomotives;
	private final List<Wagon> wagons;

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

	public BigDecimal getEmptyWeight() {
		BigDecimal locomotiveEmptyWeightSum = locomotives.stream()
				.map(Locomotive::getWeight)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal wagonEmptyWeightSum = wagons.stream()
				.map(Wagon::getWeight)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return locomotiveEmptyWeightSum.add(wagonEmptyWeightSum);
	}

	public Integer getMaxPassengerNumber() {
		int locomotiveMaxPassengerSum = locomotives.stream().mapToInt(Locomotive::getMaxPassengerNumber).sum();
		int wagonMaxPassengerSum = wagons.stream().mapToInt(Wagon::getMaxPassengerNumber).sum();
		return locomotiveMaxPassengerSum + wagonMaxPassengerSum;
	}

	public BigDecimal getMaxWeightForGoods() {
		BigDecimal locomotiveMaxWeightOfGoods = locomotives.stream()
				.map(Locomotive::getMaxGoodWeight)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal wagonMaxWeightOfGoods = wagons.stream()
				.map(Wagon::getMaxGoodWeight)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return locomotiveMaxWeightOfGoods.add(wagonMaxWeightOfGoods);
	}

	public BigDecimal getMaxPayload() {
		return getMaxWeightForGoods().add(BigDecimal.valueOf((long) getMaxPassengerNumber() * PASSENGER_WEIGHT));
	}

	public BigDecimal getMaxTotalWeight() {
		return getEmptyWeight().add(getMaxPayload());
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
		BigDecimal locomotiveMaxEffortSum = locomotives.stream()
				.map(Locomotive::getEffort)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return locomotiveMaxEffortSum.compareTo(getMaxTotalWeight()) > 0;
	}
}
