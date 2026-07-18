package in.main.Product.Helper;

import in.main.Product.Category.Category;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;



public class CategoryHelper {
    public static String[] HEADERS = {
            "ID",
            "Category Name",
            "Description",
            "Active",
            "Created At",
            "Updated At",
            "Created By",
            "Updated By",
    };
    public static String SHEET_NAME = "Categories";

    public static ByteArrayInputStream categoryToExcel(
            List<Category> categories){

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row headerRow = sheet.createRow(0);
            for(int i = 0; i < HEADERS.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }
            int rowIndex = 1;
            for(Category category : categories){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(category.getId());
                row.createCell(1).setCellValue(category.getName()!= null ? category.getName().toString() : "Not Created");
                row.createCell(2).setCellValue(category.getDescription());
                row.createCell(3).setCellValue(category.getActive() == 1 ? "Yes" : "No");
                row.createCell(4).setCellValue(category.getCreatedAt()!= null ? category.getCreatedAt().toString() : "Not Created");
                row.createCell(5).setCellValue(category.getUpdatedAt() != null ? category.getUpdatedAt().toString() : "Not Updated");
                row.createCell(6).setCellValue(category.getCreatedBy()!= null ? category.getCreatedBy().getName().toString() : "Not Created");
                row.createCell(7).setCellValue(category.getUpdatedBy() != null ? category.getUpdatedBy().getName() : "Not Updated");

            }

            workbook.write(out);
            return new ByteArrayInputStream(
                    out.toByteArray()
            );
        }catch (Exception e){
            throw new RuntimeException("Failed to generate excel",e);
        }finally{
            try{
                workbook.close();
                out.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
