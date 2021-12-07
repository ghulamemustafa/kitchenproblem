/**
 * 
 */
package ee.cleankitchen;

import static ee.cleankitchen.TestConstants.MONDAY_1230_3ORDER_CHECK_4ORDER_1830_AVAILABLE_SLOTS_1030_1230;
import static ee.cleankitchen.TestConstants.MONDAY_NO_ORDER_CHECK_ALL_AVAILABLE_SLOTS;
import static ee.cleankitchen.TestConstants.TUESDAY_1030_2ORDER_CHECK_AVAILABLE_SLOTS_1230_1830;
import static ee.cleankitchen.TestConstants.TUESDAY_NO_ORDER_CHECK_ALL_AVAILABLE_SLOTS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import ee.cleankitchen.orderservice.OrderServiceClient;
import ee.cleankitchen.orderservice.PublicOrderService;

/**
 * @author Ghulam Mustafa
 *
 */
public final class PublicOrderServiceFactory {

	
	
	private final Map<String, List<Map<String, Object>>> dataMap ;
	
	public PublicOrderServiceFactory() {
		dataMap = Map.of(
				MONDAY_1230_3ORDER_CHECK_4ORDER_1830_AVAILABLE_SLOTS_1030_1230, prepareMonday1230_3OrderCheck_4Order1830_availableSlots1030_1230(),
				TUESDAY_1030_2ORDER_CHECK_AVAILABLE_SLOTS_1230_1830, prepareTuesday1030_2OrderCheck_availableSlots1230_1830(),
				MONDAY_NO_ORDER_CHECK_ALL_AVAILABLE_SLOTS, prepareMondayNoOrderCheck_allAvailableSlots(),
				TUESDAY_NO_ORDER_CHECK_ALL_AVAILABLE_SLOTS, prepareTuesdayNoOrderCheck_allAvailableSlots()
				); 
	}
	
	
	private List<Map<String, Object>> prepareMonday1230_3OrderCheck_4Order1830_availableSlots1030_1230(){
		return List.of(
				Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(10, 30), "orderId","123", "customerId", "a-111"),
				Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(10, 30), "orderId","223", "customerId", "a-211"),
				Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(10, 30), "orderId","222", "customerId", "a-211"),
			    Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(18, 30), "orderId", "413", "customerId", "a-411"),
			    Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(18, 30), "orderId", "423", "customerId", "a-421"),
			    Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(18, 30), "orderId", "433", "customerId", "a-431"),
			    Map.of("date", LocalDate.of(2021, 11, 29), "time", LocalTime.of(18, 30), "orderId", "443", "customerId", "a-441"));
	}
	
	private List<Map<String, Object>> prepareTuesday1030_2OrderCheck_availableSlots1230_1830() {
		return List.of(
				Map.of("date", LocalDate.of(2021, 11, 30), "time", LocalTime.of(10, 30), "orderId","123", "customerId", "a-111"),
				Map.of("date", LocalDate.of(2021, 11, 30), "time", LocalTime.of(10, 30), "orderId","223", "customerId", "a-211"));
	}
	
	private List<Map<String, Object>> prepareMondayNoOrderCheck_allAvailableSlots() {
		return List.of();
	}
	
	private List<Map<String, Object>> prepareTuesdayNoOrderCheck_allAvailableSlots() {
		return List.of();
	}
	
	
	public PublicOrderService getInstance(String key) {

		return new PublicOrderService(new OrderServiceClient() {

			@Override
			public List<Map<String, Object>> getBy(LocalDate date) {
				return dataMap.get(key);
			}
		});
	}
}
