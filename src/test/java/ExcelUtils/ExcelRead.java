package ExcelUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Abuthahir on 9/9/2016.
 */
public class ExcelRead {

    public static HashMap<String, String> storeValues = new HashMap();

    public static List<HashMap<String,String>> data(File filepath, String sheetName)
    {
        List<HashMap<String,String>> mydata = new ArrayList<HashMap<String, String>>();
        try
        {
            FileInputStream fs = new FileInputStream(filepath);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Row HeaderRow = sheet.getRow(0);
            for(int i=1;i<sheet.getPhysicalNumberOfRows();i++)
            {
                Row currentRow = sheet.getRow(i);
                HashMap<String,String> currentHash = new HashMap<String,String>();
                for(int j=1;j<currentRow.getLastCellNum();j++)
                {
                    Cell currentCell = currentRow.getCell(j);
                    if(currentCell !=null) {
                        String cellvalue = readcell(currentCell);
                        currentHash.put(HeaderRow.getCell(j).getStringCellValue(), cellvalue);
                    }else {
                        currentHash.put(HeaderRow.getCell(j).getStringCellValue(), "");
                    }
                }
                mydata.add(currentHash);
            }
            fs.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mydata;

    }

    public static String readcell(Cell currentcell){
        Object cellvalue = null;
        switch (currentcell.getCellType()){

            case Cell.CELL_TYPE_STRING:
                cellvalue = currentcell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellvalue = NumberToTextConverter.toText(currentcell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
        }
        return String.valueOf(cellvalue);
    }
}
