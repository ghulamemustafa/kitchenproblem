package ee.cleankitchen;
import static ee.cleankitchen.TestConstants.MONDAY_1230_3ORDER_CHECK_4ORDER_1830_AVAILABLE_SLOTS_1030_1230;
import static ee.cleankitchen.TestConstants.MONDAY_NO_ORDER_CHECK_ALL_AVAILABLE_SLOTS;
import static ee.cleankitchen.TestConstants.TUESDAY_1030_2ORDER_CHECK_AVAILABLE_SLOTS_1230_1830;
import static ee.cleankitchen.TestConstants.TUESDAY_NO_ORDER_CHECK_ALL_AVAILABLE_SLOTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ee.cleankitchen.constant.Constants;
import ee.cleankitchen.orderservice.PublicOrderService;


public class ApplicationTest {
	
	PublicOrderServiceFactory publicOrderServiceFactory = new PublicOrderServiceFactory();

	@DisplayName("Given: System has 2 orders on Tuesday at 10:30, When: User checks delivery times for Tuesday, Then: System will only return 12:30 and 18:30 delivery times")
	@Test
	public void testTuesday1030_2OrderCheck_availableSlots1230_1830() {
		PublicOrderService publicOrderService = publicOrderServiceFactory.getInstance(TUESDAY_1030_2ORDER_CHECK_AVAILABLE_SLOTS_1230_1830);
		List<LocalTime> availableDeliveryTimes = publicOrderService.getAvailableDeliveryTimes(LocalDate.of(2021, 11, 30));
		assertEquals(2, availableDeliveryTimes.size());
		assertTrue(availableDeliveryTimes.contains(LocalTime.of(12, 30)));
		assertTrue(availableDeliveryTimes.contains(LocalTime.of(18, 30)));

	}
	
	@DisplayName("Given: System has 3 orders on Monday at 12:30 and 4 orders at 18:30, When:User checks delivery times for Monday, Then:System will return 10:30 and 12:30 delivery times")
	@Test
	public void testMonday1230_3OrderCheck_4Order1830_availableSlots1030_1230() {
		PublicOrderService publicOrderService = publicOrderServiceFactory.getInstance(MONDAY_1230_3ORDER_CHECK_4ORDER_1830_AVAILABLE_SLOTS_1030_1230);
		List<LocalTime> availableDeliveryTimes = publicOrderService.getAvailableDeliveryTimes(LocalDate.of(2021, 11, 29));
		assertEquals(2, availableDeliveryTimes.size());
		assertTrue(availableDeliveryTimes.contains(LocalTime.of(10, 30)));
		assertTrue(availableDeliveryTimes.contains(LocalTime.of(12, 30)));
	}
	
	
	@DisplayName("Given: System has no orders for a day (Monday), When: User checks delivery times for a day, Then: System will return 10:30, 12:30 and 18:30 as available times")
	@Test
	public void testMondayNoOrderCheck_allAvailableSlots() {
		PublicOrderService publicOrderService = publicOrderServiceFactory.getInstance(MONDAY_NO_ORDER_CHECK_ALL_AVAILABLE_SLOTS);
		List<LocalTime> availableDeliveryTimes = publicOrderService.getAvailableDeliveryTimes(LocalDate.of(2021, 11, 29));
		assertEquals(Constants.DAILY_DELIVERY_TIMES.size(), availableDeliveryTimes.size());
		assertTrue(availableDeliveryTimes.containsAll(Constants.DAILY_DELIVERY_TIMES));
	}
	
	@DisplayName("Given: System has no orders for a day (Tuesday), When: User checks delivery times for a day, Then: System will return 10:30, 12:30 and 18:30 as available times")
	@Test
	public void testTuesdayNoOrderCheck_allAvailableSlots() {
		PublicOrderService publicOrderService = publicOrderServiceFactory.getInstance(TUESDAY_NO_ORDER_CHECK_ALL_AVAILABLE_SLOTS);
		List<LocalTime> availableDeliveryTimes = publicOrderService.getAvailableDeliveryTimes(LocalDate.of(2021, 11, 30));
		assertEquals(Constants.DAILY_DELIVERY_TIMES.size(), availableDeliveryTimes.size());
		assertTrue(availableDeliveryTimes.containsAll(Constants.DAILY_DELIVERY_TIMES));
	}

}
