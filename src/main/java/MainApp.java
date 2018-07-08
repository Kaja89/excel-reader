import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kaja D.
 */
public class MainApp {
    public static void main(String[] args) throws IOException, InvalidFormatException, DatatypeConfigurationException {
        System.out.println("HELLO POI!");

        ExcelReader excelReader = new ExcelReader();
        excelReader.readExcel();

//        String number = ";222.2";//try-catch NumberFormatException
//        double value = Double.parseDouble(number);
//        System.out.println(value);
    }
}
