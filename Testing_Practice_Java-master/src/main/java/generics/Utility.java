package main.java.generics;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.Reporter;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Utility {

    private Utility() {

    }

    //----------------------------------------------------------------------------------------------------------------//

    /**
     * This method is used to get data from the property file
     *
     * @param filePath path of property file located
     * @param key      the reference of the element we are searching
     * @return value.
     */
    static String getPropertyValue(String filePath, String key) {
        String value = "";
        Properties ppt = new Properties();
        try {
            //Added

            ClassLoader loader = Utility.class.getClassLoader();
            String propPath=loader.getResource(filePath).getPath();
            //System.out.println("My file Path: "+propPath);
            //System.out.println("Existing path: "+filePath);

            ppt.load(loader.getResourceAsStream(filePath));
            value = ppt.getProperty(key);
        } catch (Exception e) {
            Reporter.log(e.getLocalizedMessage(), true);
            Assert.fail();
        }
        return value;
    }
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * This method is used to get data from the objectRepository File
     *
     * @param sheet is the name of the Sheet from which the data has to be fetched;
     * @param key   is the name of the Element based on which the element is selected;
     * @return Object;
     */
    public static ObjectRepSheet getCellData_OBR(String sheet, String key) {
        ObjectRepSheet data = new ObjectRepSheet();
        Map<String, ObjectRepSheet> repSheet = new HashMap<>();
        String rowData;

        //Added
        ClassLoader loader = Utility.class.getClassLoader();


        try (InputStream fis = loader.getResourceAsStream(BaseTest.objectRepository);
             Workbook wb = new HSSFWorkbook(fis)) {
            Sheet objSheet = wb.getSheet(sheet);
            if (objSheet == null) {
                throw new NullPointerException(sheet + " : is not matched with the expected Sheet name in the Object repository File");
            }
            // Getting the Column Headings
            int element = 0;
            int locatorType = 0;
            int locatorValue = 0;
            Boolean locatorTypeMatch = false;
            Boolean locatorValueMatch = false;
            int firstRowNum = objSheet.getFirstRowNum();
            int columnCount = objSheet.getRow(firstRowNum).getLastCellNum();
            for (int i = 0; i < columnCount; i++) {
                rowData = objSheet.getRow(firstRowNum).getCell(i).toString();
                if (rowData.trim().equalsIgnoreCase("Element")) {
                    element = i;
                } else if (rowData.trim().equalsIgnoreCase("Locator Type")) {
                    locatorType = i;
                    locatorTypeMatch = true;
                } else if (rowData.trim().equalsIgnoreCase("Locator Value")) {
                    locatorValue = i;
                    locatorValueMatch = true;
                } else {
                    if (!locatorTypeMatch) {
                        throw new NullPointerException(rowData + " : is not matched with the expected column" + " 'Locator Type' " + " Please verify the Object Repository sheet header ");
                    } else if (!locatorValueMatch) {
                        throw new NullPointerException(rowData + " : is not matched with the expected column" + " 'Locator Value' " + " Please verify the Object Repository sheet header ");
                    }
                }
            }

            // Iterating to the particular key and getting the locator type and locator value
            for (Row row : objSheet) {
                ObjectRepSheet repoSheet = new ObjectRepSheet();
                String rowName = null;
                for (int i = 0; i < columnCount; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    DataFormatter df = new DataFormatter();
                    String formattedCell = df.formatCellValue(cell);
                    formattedCell.replaceAll("\u00A0", "");

                    if (i == element) {
                        repoSheet.setElement(formattedCell);
                        if (key.equalsIgnoreCase(formattedCell.trim())) {
                            rowName = formattedCell;
                        }
                    } else if (i == locatorType) {
                        repoSheet.setLocatorType(formattedCell);

                    } else if (i == locatorValue) {
                        repoSheet.setLocatorValue(formattedCell);
                        if (rowName != null)
                            repSheet.put(rowName, repoSheet);
                    } else {
                        break;
                    }
                }
                if (repSheet.size() > 0) {
                    data = repSheet.get(key);
                    break;
                }
            }
        } catch (Exception e) {
            //printing the error message
            Reporter.log(e.getLocalizedMessage(), true);
            Assert.fail();
        }
        return data;
    }
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * This method is used to get data from the TestData File
     *
     * @param sheet is the name of the Sheet from which the data has to be fetched;
     * @param key   is the name of the Element based on which the element is selected;
     * @return Data;
     */
    public static String getCellData_TestData(String sheet, String key) {
        String cellData = "";
        int rowNum = 1;

        ClassLoader loader = Utility.class.getClassLoader();
        //FileInputStream fis = new FileInputStream(new File(BaseTest.testData)

        try (InputStream fis = loader.getResourceAsStream(BaseTest.testData); Workbook wb = new HSSFWorkbook(fis)) {
            Sheet sheet1 = wb.getSheet(sheet);

            int columnCount = sheet1.getRow(0).getLastCellNum();
            for (int cell = 0; cell <= columnCount; cell++) {
                String rowData = sheet1.getRow(0).getCell(cell).toString();
                if (rowData.trim().equalsIgnoreCase(key)) {
                    cellData = sheet1.getRow(rowNum).getCell(cell).toString();
                    break;
                }
            }

        } catch (Exception e) {
            Reporter.log(e.getLocalizedMessage(), true);
            Assert.fail();
        }
        return cellData;
    }
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * This method is used to get data from the Platform Config File
     *
     * @param sheet is the name of the Sheet from which the data has to be fetched;
     * @param key   is the name of the Element based on which the element is selected;
     * @return value;
     */
    static String getCellData_PC(String sheet, String key) {
        String cellData = "";
        int columnNum = 1;

        ClassLoader loader = Utility.class.getClassLoader();
        try (InputStream fis = loader.getResourceAsStream(AutomationConstants.PLATFORM_CONFIG); Workbook wb = new HSSFWorkbook(fis)) {
            Sheet sheet1 = wb.getSheet(sheet);

            int rowCount = sheet1.getLastRowNum();
            for (int row = 1; row <= rowCount + 1; row++) {
                String rowData = sheet1.getRow(row).getCell(0).toString();
                if (rowData.trim().equalsIgnoreCase(key)) {
                    cellData = sheet1.getRow(row).getCell(columnNum).toString();
                    break;
                }
            }

        } catch (Exception e) {
            Reporter.log(e.getLocalizedMessage(), true);
            Assert.fail();
        }

        return cellData;
    }
    //----------------------------------------------------------------------------------------------------------------//

    public JSONObject  readJson(String file) {

        JSONObject  value = null;
        try {
            JSONObject jsonObject;
            try (FileReader reader = new FileReader(file)) {
                JSONParser jsonParser = new JSONParser();
                jsonObject = (JSONObject) jsonParser.parse(reader);
                value=jsonObject;
            }
        } catch (Exception e) {
            Reporter.log(e.getMessage(),true);
        }
        return value;
    }
    //----------------------------------------------------------------------------------------------------------------//
}