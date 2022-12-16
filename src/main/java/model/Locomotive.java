package model;

import exception.TrainException;
import java.math.BigDecimal;
import java.time.Year;
import lombok.Getter;
import model.enums.DriveType;

@Getter
public class Locomotive {
	private BigDecimal weight;
	private BigDecimal length;
	private BigDecimal effort;
	private int maxPassengerNumber;
	private BigDecimal maxGoodWeight;
	private String designationType;
	private String manufacturer;
	private Year constructionYear;
	private String serialNumber;
	private DriveType driveType;

	public Locomotive(BigDecimal weight, BigDecimal length, BigDecimal effort, int maxPassengerNumber,
			BigDecimal maxGoodWeight, String serialNumber) {
		if (weight.compareTo(BigDecimal.ZERO) == 0) {
			throw new TrainException("Weight can not be 0");
		}
		if (length.compareTo(BigDecimal.ZERO) == 0) {
			throw new TrainException("Length can not be 0");
		}
		if (effort.compareTo(BigDecimal.ZERO) == 0) {
			throw new TrainException("Effort can not be 0");
		}
		if (maxPassengerNumber < 0) {
			throw new TrainException("Max passenger number should be greater than 0");
		}
		if (maxGoodWeight.compareTo(BigDecimal.ZERO) == 0) {
			throw new TrainException("Weight for goods can not be 0");
		}
		this.weight = weight;
		this.length = length;
		this.effort = effort;
		this.maxPassengerNumber = maxPassengerNumber;
		this.maxGoodWeight = maxGoodWeight;
		this.serialNumber = serialNumber;
	}

	public void changeEffort(BigDecimal effort) {
		this.effort = effort;
	}
}