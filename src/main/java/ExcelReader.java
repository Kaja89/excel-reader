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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kaja D.
 */
public class ExcelReader {
    public String excel_file_path = "C:\\Users\\kajas\\Desktop\\Bluesoft\\umowy_2016.xlsx";

    FieldsResolver fieldsResolver = new FieldsResolver();
    //przypisz liczbę do odpowiedniej kolumny
    //jeśli numer komórki = nr kolumnny - zapisz ją do zmiennej, która będzie przypisana(switch?)

    public void readExcel() throws IOException, InvalidFormatException, DatatypeConfigurationException{
        Workbook workbook = WorkbookFactory.create(new File(excel_file_path));
        System.out.println("Workbook has: " + workbook.getNumberOfSheets() + " sheets.");

        RequiredValuesCreator requiredValuesCreator = new RequiredValuesCreator();
        List<String> requiredValues = requiredValuesCreator.createListOfrequiredValues();
        List<Integer> cellIndexListToIgnore = new ArrayList<>();

        DataFormatter dataFormatter = new DataFormatter();

        for(Sheet sheet: workbook){
            Row row = sheet.getRow(0);
            fieldsResolver.resolveFields(row);
            for(Cell cell: row){
                if(cell.getCellTypeEnum().equals(CellType.STRING)){
                    String cellName = cell.getRichStringCellValue().getString();
                    //System.out.println(cell.getRichStringCellValue().getString());
                    checkNotRequired(requiredValues, cellIndexListToIgnore, cell, cellName);
                }
            }

            for(Row row1: sheet){
                if(row1.getRowNum()!=0){
                    Contract contract = fieldsResolver.contractCreator(row1);
                    System.out.println("NEW CONTRACT CREATED: " + contract.getDateUntil() + " " + contract.getDateFrom() + " " + contract.getSystemInfo() + contract.getAccountingPeriod() + " " +  contract.getTotalCost() + " " + contract.getAccountType() + contract.isActive());
                    System.out.println("ROW NUMBER: " + row1.getRowNum());
                    for(Cell cell: row1){
                        System.out.println("Checking each cell in row...");
                        int columnIndex = cell.getColumnIndex();
                        if(cellIndexListToIgnore.contains(columnIndex)){
                            System.out.println("Found ignored cell");
                            continue;
                        } else{
                            System.out.println("Cell type: " + cell.getCellTypeEnum());
                            if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
                                //Date date = cell.getDateCellValue();
                                //System.out.println("Ekhem date : " + date);
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateToReturn = cell.getDateCellValue();

                                XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateFormat.format(dateToReturn));

                                LocalDate finalDate = cal.toGregorianCalendar().toZonedDateTime().toLocalDate();
                                //LocalDate date = cal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                System.out.println(finalDate);

                            }
                            String cellContent = dataFormatter.formatCellValue(cell);
                            System.out.println(cellContent);
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    private void checkNotRequired(List<String> requiredValues, List<Integer> cellIndexListToIgnore, Cell cell, String cellName) {
        if(!requiredValues.contains(cellName)){
            System.out.println(cellName);
            int cellIndex = cell.getColumnIndex();//<---to ignore
            cellIndexListToIgnore.add(cellIndex);
            System.out.println("Column index: " + cellIndex);
        }
    }
}
