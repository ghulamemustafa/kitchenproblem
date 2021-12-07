package ee.cleankitchen;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ee.cleankitchen.constant.Constants;
import ee.cleankitchen.orderservice.OrderServiceClient;
import ee.cleankitchen.orderservice.PublicOrderService;


public class ApplicationTest {

	@DisplayName("Given: System has 2 orders on Tuesday at 10:30, When: User checks delivery times for Tuesday, Then: System will only return 12:30 and 18:30 delivery times")
	@Test
	public void testScenerio1() {
		PublicOrderService publicOrderService = new PublicOrderService(new OrderServiceClient() {

			@Override
			public List<Map<String, Object>> getBy(LocalDate date) {
				return List.of(
						Map.of("date", LocalDate.of(2021, 11, 30), "time", LocalTime.of(10, 30), "orderId","123", "customerId", "a-111"),
						Map.of("date", LocalDate.of(2021, 11, 30), "time", LocalTime.of(10, 30), "orderId","223", "customerId", "a-211"));
			}
		});
		List<LocalTime> availableDeliveryTimes = publicOrderService.getAvailableDeliveryTimes(LocalDate.of(2021, 11, 30));
		assertEquals(2, availableDeliveryTimes.size());
		assertTrue(availableDeliveryTimes.contains(LocalTime.of(12, 30)));
		assertTrue(availableDeliveryTimes.contains(LocalTime.of(18, 30)));

	}
	
	@DisplayName("Given: System has 3 orders on Monday at 12:30 and 4 orders at 18:30, When:User checks delivery times for Monday, Then:System will return 10:30 and 12:30 delivery times")
	@Test
	public void testScenerio2() {
		PublicOrderService publicOrderService = new PublicOrderService(new OrderServiceClient() {

			@Override
			public List<Map<String, Object>> getBy(LocalDate date) {
				return List.of(
						Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(10, 30), "orderId","123", "customerId", "a-111"),
						Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(10, 30), "orderId","223", "customerId", "a-211"),
						Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(10, 30), "orderId","222", "customerId", "a-211"),
					    Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(18, 30), "orderId", "413", "customerId", "a-411"),
					    Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(18, 30), "orderId", "423", "customerId", "a-421"),
					    Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(18, 30), "orderId", "433", "customerId", "a-431"),
					    Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(18, 30), "orderId", "443", "customerId", "a-441"));
			}
		});
		List<LocalTime> availableDeliveryTimes = publicOrderService.getAvailableDeliveryTimes(LocalDate.of(2021, 11, 29));
		assertEquals(2, availableDeliveryTimes.size());
		assertTrue(availableDeliveryTimes.contains(LocalTime.of(10, 30)));
		assertTrue(availableDeliveryTimes.contains(LocalTime.of(12, 30)));
	}
	
	
	@DisplayName("Given: System has no orders for a day (Monday), When: User checks delivery times for a day, Then: System will return 10:30, 12:30 and 18:30 as available times")
	@Test
	public void testScenerio4() {
		PublicOrderService publicOrderService = new PublicOrderService(new OrderServiceClient() {

			@Override
			public List<Map<String, Object>> getBy(LocalDate date) {
				return List.of();
			}
		});
		List<LocalTime> availableDeliveryTimes = publicOrderService.getAvailableDeliveryTimes(LocalDate.of(2021, 11, 29));
		assertEquals(Constants.DAILY_DELIVERY_TIMES.size(), availableDeliveryTimes.size());
		assertTrue(availableDeliveryTimes.containsAll(Constants.DAILY_DELIVERY_TIMES));
	}
	
	@DisplayName("Given: System has no orders for a day (Tuesday), When: User checks delivery times for a day, Then: System will return 10:30, 12:30 and 18:30 as available times")
	@Test
	public void testScenerio5() {
		PublicOrderService publicOrderService = new PublicOrderService(new OrderServiceClient() {

			@Override
			public List<Map<String, Object>> getBy(LocalDate date) {
				return List.of();
			}
		});
		List<LocalTime> availableDeliveryTimes = publicOrderService.getAvailableDeliveryTimes(LocalDate.of(2021, 11, 30));
		assertEquals(Constants.DAILY_DELIVERY_TIMES.size(), availableDeliveryTimes.size());
		assertTrue(availableDeliveryTimes.containsAll(Constants.DAILY_DELIVERY_TIMES));
	}

}
