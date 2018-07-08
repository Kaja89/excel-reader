import java.util.ArrayList;
import java.util.List;

/**
 * @author Kaja D.
 */
public class RequiredValuesCreator {
    public List<String> createListOfrequiredValues(){
        List<String> requiedValues = new ArrayList<>();
        requiedValues.add("system");
        requiedValues.add("order_number");
        requiedValues.add("from_date");
        requiedValues.add("to_date");
        requiedValues.add("amount");
        requiedValues.add("amount_type");
        requiedValues.add("amount_period");
        requiedValues.add("active");

        return requiedValues;
    }
}
