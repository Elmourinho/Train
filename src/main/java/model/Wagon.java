package model;

import exception.TrainException;
import java.math.BigDecimal;
import java.time.Year;
import lombok.Getter;
import model.enums.WagonType;

@Getter
public class Wagon {
	private BigDecimal weight;
	private BigDecimal length;
	private WagonType type;
	private int maxPassengerNumber;
	private BigDecimal maxGoodWeight;
	private String designationType;
	private String manufacturer;
	private Year constructionYear;
	private String serialNumber;

	public Wagon(BigDecimal weight, BigDecimal length, int maxPassengerNumber, BigDecimal maxGoodWeight,
			String serialNumber) {
		if (weight.compareTo(BigDecimal.ZERO) == 0) {
			throw new TrainException("Weight can not be 0");
		}
		if (length.compareTo(BigDecimal.ZERO) == 0) {
			throw new TrainException("Length can not be 0");
		}
		if (maxPassengerNumber < 0) {
			throw new TrainException("Max passenger number should be greater than 0");
		}
		if (maxGoodWeight.compareTo(BigDecimal.ZERO) == 0) {
			throw new TrainException("Weight for goods can not be 0");
		}
		this.weight = weight;
		this.length = length;
		this.maxPassengerNumber = maxPassengerNumber;
		this.maxGoodWeight = maxGoodWeight;
		this.serialNumber = serialNumber;
	}
}