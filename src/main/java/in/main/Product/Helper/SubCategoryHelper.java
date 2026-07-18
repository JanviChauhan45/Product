package in.main.Product.Helper;

import in.main.Product.SubCategory.SubCategory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class SubCategoryHelper {
    public static String[] HEADERS = {
            "ID",
            "SubCategory Name",
            "Description",
            "Category Name",
            "Active",
            "Created At",
            "Updated At",
            "Created By",
            "Updated By",
    };
    public static String SHEET_NAME = "SubCategories";

    public static ByteArrayInputStream subcategoryToExcel(
            List<SubCategory> subCategories
    ){
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
            for(SubCategory subCategory : subCategories){
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(subCategory.getId());
                row.createCell(1).setCellValue(subCategory.getName());
                row.createCell(2).setCellValue(subCategory.getDescription());
                row.createCell(3).setCellValue(subCategory.getCategory().getName());
                row.createCell(4).setCellValue(subCategory.getActive() == 1
                ? "Yes" : "No");
                row.createCell(5).setCellValue(subCategory.getCreatedAt().toString());
                row.createCell(6).setCellValue(subCategory.getUpdatedAt() != null ? subCategory.getUpdatedAt().toString() : "Not Updated");
                row.createCell(7).setCellValue(subCategory.getCreatedBy().getName());
                row.createCell(8).setCellValue(subCategory.getUpdatedBy().getName());
            }
            workbook.write(out);

            return new ByteArrayInputStream(
                    out.toByteArray()
            );

        }catch(Exception e){
            throw new RuntimeException("Failed to create Excel Sheet",e);
        }finally {
            try{
                workbook.close();
                out.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

}
