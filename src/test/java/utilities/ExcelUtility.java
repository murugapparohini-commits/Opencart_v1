package utilities;
	import java.io.File;
import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import org.apache.poi.ss.usermodel.CellStyle;
	import org.apache.poi.ss.usermodel.DataFormatter;
	import org.apache.poi.ss.usermodel.FillPatternType;
	import org.apache.poi.ss.usermodel.IndexedColors;
	import org.apache.poi.xssf.usermodel.XSSFCell;
	import org.apache.poi.xssf.usermodel.XSSFRow;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;

	public class ExcelUtility {
		
		public static FileInputStream fi;
		public static FileOutputStream fo;
		public static XSSFWorkbook wb;
		public static XSSFSheet ws;
		public static XSSFRow row;
		public static XSSFCell cell;
		public static CellStyle style;
	    String path;
		
		public ExcelUtility(String path) {
			this.path=path;
		}
		
		public  int getRowCount(String sheetName) throws IOException {
			fi=new FileInputStream(path);
			wb=new XSSFWorkbook(fi);
			ws=wb.getSheet(sheetName);
			int rowcount=ws.getLastRowNum();
			wb.close();
			fi.close();
			return rowcount;
		}
		
		public  int getCellCount(String sheetName,int rownum) throws IOException {
			fi=new FileInputStream(path);
			wb=new XSSFWorkbook(fi);
			ws=wb.getSheet(sheetName);
			row=ws.getRow(rownum);
			int cellcount=row.getLastCellNum();
			wb.close();
			fi.close();
			return cellcount;
		}
		
		public  String getCellData(String sheetName,int rownum,int colnum) throws IOException {
			fi=new FileInputStream(path);
			wb=new XSSFWorkbook(fi);
			ws=wb.getSheet(sheetName);
			row=ws.getRow(rownum);
			cell=row.getCell(colnum);
			
			String data;
			try {
				//data=cell.toString();
				DataFormatter formatter = new DataFormatter();
				data=formatter.formatCellValue(cell);
				}
			catch(Exception e) {
				data="";
			}
			
			wb.close();
			fi.close();
			return data;
			
		}
		
		public void setCellData(String sheetName, int rownum, int column, String data)
		        throws IOException {

		    File xlfile = new File(path);

		    // If file does not exist then create new file
		    if (!xlfile.exists()) {
		        wb = new XSSFWorkbook();
		        fo = new FileOutputStream(path);
		        wb.write(fo);
		    }

		    fi = new FileInputStream(path);
		    wb = new XSSFWorkbook(fi);

		    // If sheet does not exist then create new Sheet
		    if (wb.getSheetIndex(sheetName) == -1)
		        wb.createSheet(sheetName);

		    ws = wb.getSheet(sheetName);

		    // If row does not exist then create new Row
		    if (ws.getRow(rownum) == null)
		        ws.createRow(rownum);

		    row = ws.getRow(rownum);

		    cell = row.createCell(column);
		    cell.setCellValue(data);

		    fo = new FileOutputStream(path);
		    wb.write(fo);

		    wb.close();
		    fi.close();
		    fo.close();
		}
		
		public  void fillGreenColor(String sheetName,int rownum,int colnum) throws IOException {
			fi=new FileInputStream(path);
			wb=new XSSFWorkbook(fi);
			ws=wb.getSheet(sheetName);
			row=ws.getRow(rownum);
			cell=row.getCell(colnum);


			style=wb.createCellStyle();
			
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			cell.setCellStyle(style);
			fo=new FileOutputStream(path);
			wb.write(fo);
			wb.close();
			fi.close();
			fo.close();
		
		}
		
		public  void fillRedColor(String sheetName,int rownum,int colnum) throws IOException {
			fi=new FileInputStream(path);
			wb=new XSSFWorkbook(fi);
			ws=wb.getSheet(sheetName);
			row=ws.getRow(rownum);
			cell=row.getCell(colnum);


			style=wb.createCellStyle();
			
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			cell.setCellStyle(style);
			fo=new FileOutputStream(path);
			wb.write(fo);
			wb.close();
			fi.close();
			fo.close();
		
		}


	}



