package ee.cleankitchen.orderservice;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ee.cleankitchen.constant.Constants;

/**
 * @author Ghulam Mustafa
 * 
 * 
 */
public final class PublicOrderService {

	// instance is managed via dependency inversion
	private final OrderServiceClient orderServiceClient;

	public PublicOrderService(OrderServiceClient orderServiceClient) {
		this.orderServiceClient = orderServiceClient;
	}

	public List<LocalTime> getAvailableDeliveryTimes(LocalDate date) {
		
		// check if its Monday ?
		boolean isMonday = date.getDayOfWeek().equals(DayOfWeek.MONDAY);
		
		// initialize daily delivery time counts map with zeros (key=LocalTime, value=0)
		Map<LocalTime, Integer> deliveryTimeCounts = Constants.DAILY_DELIVERY_TIMES.stream().collect(Collectors.toMap(Function.identity(), v->Integer.valueOf(0)));
		
		// get existing orders for date
		List<Map<String, Object>> existingOrders = orderServiceClient.getBy(date);
		
		// iterate over existing orders
		for (Map<String, Object> existingOrder : existingOrders) {
			
			// get order delivery time 
			LocalTime existingOrdertime = (LocalTime) existingOrder.get(Constants.KEY_ORDER_TIME);
			
			// count number of orders per time slot
			Integer timeCount = deliveryTimeCounts.get(existingOrdertime);
			deliveryTimeCounts.put(existingOrdertime, timeCount + 1);
		}
		
		//iterate over delivery counts
		return deliveryTimeCounts.entrySet().stream().filter(entrySet -> {
			// decide the daily limit
			int dayLimit = isMonday ? Constants.LIMIT_FOR_MONDAY : Constants.LIMIT_FOR_OTHER_DAYS;
			// filter only those time slots whose count is less than daily limit
			return entrySet.getValue() < dayLimit;
		}).map(entrySet -> entrySet.getKey()).collect(Collectors.toList());
	}
}
