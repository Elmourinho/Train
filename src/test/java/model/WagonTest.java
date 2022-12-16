package model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.TrainException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class WagonTest {

	@Test
	void shouldThrowException_whenCreateWagonWith0Weight() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Wagon(BigDecimal.ZERO, BigDecimal.ONE, 0, BigDecimal.ONE, ""));

		String expectedMessage = "Weight can not be 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowException_whenCreateWagonWith0Length() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Wagon(BigDecimal.ONE, BigDecimal.ZERO, 0, BigDecimal.ONE, ""));

		String expectedMessage = "Length can not be 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowException_whenCreateWagonWithNegativePassengerCount() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Wagon(BigDecimal.ONE, BigDecimal.ONE, -1, BigDecimal.ONE, ""));

		String expectedMessage = "Max passenger number should be greater than 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowException_whenCreateWagonWith0GoodWeight() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Wagon(BigDecimal.ONE, BigDecimal.ONE, 0, BigDecimal.ZERO, ""));

		String expectedMessage = "Weight for goods can not be 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
}
