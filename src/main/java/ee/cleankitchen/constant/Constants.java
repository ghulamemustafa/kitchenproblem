package ee.cleankitchen.constant;

import java.time.LocalTime;
import java.util.List;

/**
 * @author Ghulam Mustafa
 * using interface for constants makes sense since the fields are static final by default !
 */
public interface Constants {
	
	// daily limits
	int LIMIT_FOR_MONDAY = 4;
	int LIMIT_FOR_OTHER_DAYS = 2;
	
	// key to fetch time from order object
	String KEY_ORDER_TIME = "time";
	
	// immutable list of daily delivery times
	List<LocalTime> DAILY_DELIVERY_TIMES = List.of(LocalTime.of(10, 30), LocalTime.of(12, 30), LocalTime.of(18, 30));
}
