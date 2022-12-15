package model;

import java.time.Year;
import lombok.Data;

@Data
public class Locomotive {
	private double weight;
	private double length;
	private double effort;
	private int maxPassengerNumber;
	private double maxGoodWeight;
	private String designationType;
	private String manufacturer;
	private Year constructionYear;
	private String serialNumber;
	private DriveType driveType;
}

enum DriveType {
	DIESEL,
	STEAM,
	ELECTRIC
}


