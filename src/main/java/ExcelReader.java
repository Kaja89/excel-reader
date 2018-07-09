import model.Contract;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Contract> readedContracts = new ArrayList<>();

        //DataFormatter dataFormatter = new DataFormatter();

        for(Sheet sheet: workbook){
            Row row = sheet.getRow(0);
            fieldsResolver.resolveFields(row);
            for(Cell cell: row){
                if(cell.getCellTypeEnum().equals(CellType.STRING)){
                    String cellName = cell.getRichStringCellValue().getString();
                    checkNotRequired(requiredValues, cellIndexListToIgnore, cell, cellName);
                }
            }

            for(Row row1: sheet){
                if(row1.getRowNum()!=0){
                    Contract contract = fieldsResolver.contractCreator(row1);
                    Optional<Contract> contractOptional= Optional.ofNullable(contract);
                    if(contractOptional.isPresent()){
                        readedContracts.add(contract);
                        System.out.println("NEW CONTRACT CREATED: " + contract.toString());
                    }
                    System.out.println("ROW NUMBER: " + row1.getRowNum());
                    System.out.println();
                }
            }
            System.out.println("Readed " + readedContracts.size() + " contract/s");
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
