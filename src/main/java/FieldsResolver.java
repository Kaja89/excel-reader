import model.Contract;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Kaja D.
 */
public class FieldsResolver  {
    //Contract contract = new Contract();
    private String contractNumber;//id plus year?
    private LocalDate dateFrom;
    private LocalDate dateUntil;
    private double totalCost;
    private String accountType;
    private String accountingPeriod;
    private boolean active;
    private String systemInfo;

    int systemNum;
    int orderNum;
    int fromDateNum;
    int toDateNum;
    int amountNum;
    int amountTypeNum;
    int amountPeriodNum;
    int activeNum;

    public void resolveFields(Row row) throws IOException, InvalidFormatException, DatatypeConfigurationException {
        for(Cell cell:row){
            String cellContent = cell.getRichStringCellValue().getString();
            switch (cellContent){
                case "system":
                    systemNum = cell.getColumnIndex();
                    break;
                case "order_number":
                    orderNum = cell.getColumnIndex();
                    break;
                case "from_date":
                    fromDateNum = cell.getColumnIndex();
                    break;
                case "to_date":
                    toDateNum = cell.getColumnIndex();
                    break;

                case "amount":
                    amountNum = cell.getColumnIndex();
                    break;

                case "amount_type":
                    amountTypeNum = cell.getColumnIndex();
                    break;

                case "amount_period":
                    amountPeriodNum = cell.getColumnIndex();
                    break;

                case "active":
                    activeNum = cell.getColumnIndex();
                    break;

                default:
                    System.out.println("Nothing special");
            }

        }
        System.out.println("FROM FIELDS RESOLVER: " + systemNum + " " + orderNum + " " + fromDateNum + " " + toDateNum);
    }

    public Contract contractCreator(Row row) throws DatatypeConfigurationException{
        for(Cell cell: row){
            int cellNum = cell.getColumnIndex();
//            switch (cellNum){
//                case systemNum:
//
//            }
            if(cellNum == systemNum){
                systemInfo = cell.getStringCellValue();
            }
            if(cellNum == orderNum){
                contractNumber = cell.getStringCellValue();
            }
            if(cellNum == fromDateNum){
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                Date dateToReturn = cell.getDateCellValue();
//
//                XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateFormat.format(dateToReturn));

                if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
                    dateFrom = convertToLocalDate(cell);
                } else{
                    System.out.println("not numeric data");
                }



                        //= cal.toGregorianCalendar().toZonedDateTime().toLocalDate();
            }

            if(cellNum == toDateNum){
                if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
                    dateUntil  = convertToLocalDate(cell);
                } else{
                    System.out.println("not numeric data");
                }

            }

            if(cellNum == amountNum){
                if(cell.getCellTypeEnum().equals(CellType.STRING)){
                    //try-catch NumberFormatException
                    try{
                        totalCost = Double.parseDouble(cell.getStringCellValue());
                    } catch(NumberFormatException e){
                        System.out.println(e);
                    }
                } else {
                    totalCost = cell.getNumericCellValue();
                }
            }

            if(cellNum == amountTypeNum){
                accountType = cell.getStringCellValue();
            }

            if(cellNum == amountPeriodNum){
                accountingPeriod = cell.getStringCellValue();
            }

            if(cellNum == activeNum){
                if(cell.getCellTypeEnum().equals(CellType.BOOLEAN)){
                    active = cell.getBooleanCellValue();
                } else if(cell.getCellTypeEnum().equals(CellType.STRING)){
                    String booleanValue = cell.getStringCellValue();
                    if(booleanValue.equals("true")){
                        active = true;
                    } else if(booleanValue.equals("false")){
                        active = false;
                    }
                }
            }
        }
        Contract contract = new Contract(contractNumber, dateFrom, dateUntil, totalCost, accountType, accountingPeriod, active, systemInfo);
        return contract;
    }

    public LocalDate convertToLocalDate(Cell cell) throws DatatypeConfigurationException{

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateToConvert = cell.getDateCellValue();

        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateFormat.format(dateToConvert));

        LocalDate dateToReturn = cal.toGregorianCalendar().toZonedDateTime().toLocalDate();
        return dateToReturn;
    }



}
