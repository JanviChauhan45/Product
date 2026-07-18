package in.main.Product.Helper;

import in.main.Product.Products.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;



public class ProductHelper {
    public static String[] HEADERS = {
            "ID",
            "ImageURL",
            "Name",
            "Description",
            "Price",
            "Category",
            "SubCategory",
            "Discount",
            "Active",
            "Created At",
            "Updated At",
            "Created By",
            "Updated By"
    };
    public static String SHEET_NAME = "Products";
    public static ByteArrayInputStream productToExcel(
            List<Product> products
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
            for(Product product : products){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getImageURL());
                row.createCell(2).setCellValue(product.getName());
                row.createCell(3).setCellValue(product.getDescription());
                row.createCell(4).setCellValue(product.getPrice());
                row.createCell(5).setCellValue(product.getSubCategory().getCategory().getName());
                row.createCell(6).setCellValue(product.getSubCategory().getName());
                row.createCell(7).setCellValue(product.getDiscount());
                row.createCell(8).setCellValue(product.getActive() == 1
                        ? "Yes" : "No");
                row.createCell(9).setCellValue(product.getCreatedAt().toString());
                row.createCell(10).setCellValue(
                       product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : "Not Updated"
                );
                row.createCell(11).setCellValue(product.getCreatedBy().getName());
                row.createCell(12).setCellValue(product.getUpdatedBy().getName());

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }catch(Exception e){
            throw new RuntimeException("Failed to create Excel Sheet " ,e);
        }finally{
            try{
                workbook.close();
                out.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
