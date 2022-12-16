package model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.TrainException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class LocomotiveTest {

	@Test
	void shouldThrowException_whenCreateLocomotiveWith0Weight() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Locomotive(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, 0, BigDecimal.ONE, ""));

		String expectedMessage = "Weight can not be 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowException_whenCreateLocomotiveWith0Length() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Locomotive(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, 0, BigDecimal.ONE, ""));

		String expectedMessage = "Length can not be 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowException_whenCreateLocomotiveWith0Effort() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, 0, BigDecimal.ONE, ""));

		String expectedMessage = "Effort can not be 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowException_whenCreateLocomotiveWithNegativePassengerCount() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, -1, BigDecimal.ONE, ""));

		String expectedMessage = "Max passenger number should be greater than 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowException_whenCreateLocomotiveWith0GoodWeight() {
		Exception exception = assertThrows(TrainException.class,
				() -> new Locomotive(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 0, BigDecimal.ZERO, ""));

		String expectedMessage = "Weight for goods can not be 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
}
