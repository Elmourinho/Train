package model;

import java.time.Year;
import lombok.Data;

@Data
public class Wagon {
	private double weight;
	private double length;
	private WagonType type;
	private int maxPassengerNumber;
	private double maxGoodWeight;
	private String designationType;
	private String manufacturer;
	private Year constructionYear;
	private String serialNumber;
}

enum WagonType {
	PASSENGER_CAR,
	SLEEPER_CAR,
	DINING_CAR,
	FREIGHT_WAGON
}
