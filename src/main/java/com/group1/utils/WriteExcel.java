package com.group1.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.group1.model.Bill;
import com.group1.model.Employee;
import com.group1.model.Job;
import com.group1.model.JobDetail;
import com.group1.model.Plan;
import com.vaadin.server.FileDownloader;

@Service
public class WriteExcel {

	private XSSFWorkbook workbook;
	
	private CellStyle cellStyle;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public String writePlan(Plan plan, String tempFilePath) throws Exception {
		Resource resource = resourceLoader.getResource("classpath:");
		
//		FileInputStream fis = new FileInputStream(tempFilePath + ".xlsx");
		FileInputStream fis = new FileInputStream(resource.getFile().getAbsolutePath()+"\\"+tempFilePath+ ".xlsx");
		
		java.util.Date vdtNow = new java.util.Date();
		String outFilePath = (new StringBuilder()).append(resource.getFile().getAbsolutePath()).append("\\"+tempFilePath).append(vdtNow.getHours())
				.append(vdtNow.getMinutes()).append(vdtNow.getSeconds()).append(vdtNow.getDate())
				.append(vdtNow.getMonth()).append(vdtNow.getYear()).append(".xlsx").toString();
		
//		resource = resourceLoader.getResource("classpath:"+outFilePath);
		FileOutputStream os = new FileOutputStream(outFilePath);
		
		workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);

		int rowCount = 2;
		Row row;
		Cell cell;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Tên kế hoạch");
		cell = row.getCell(4);
		cellStyle = cell.getCellStyle();
		cell.setCellValue(Objects.toString(plan.getName(), ""));
		rowCount += 2;

		Employee emp = plan.getEmp();
		row = sheet.getRow(rowCount);
//		cell = row.getCell(2);
//		cell.setCellValue("Họ tên");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(emp.getName(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(2);
//		cell.setCellValue("Email");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(emp.getEmail(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(2);
//		cell.setCellValue("Phone");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(emp.getPhone(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(2);
//		cell.setCellValue("Chuc vu");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(emp.getChucVu().getName(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(2);
//		cell.setCellValue("Phòng ban");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(emp.getGroup().getName(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Ngày đề xuất");
		cell = row.getCell(4);
		cell.setCellValue(Objects
				.toString(plan.getCreateDate() != null ? simpleDateFormat.format(plan.getCreateDate()) : null, ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Ngày duyệt cuối cùng");
		cell = row.getCell(4);
		cell.setCellValue(Objects
				.toString(plan.getCheckDate() != null ? simpleDateFormat.format(plan.getCheckDate()) : null, ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Trạng thái kế hoạch");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.getStatus().getName(), ""));
		rowCount += 2;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(2);
//		cell.setCellValue("Tên dự án");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.getProject().getName(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(2);
//		cell.setCellValue("Chi phí đã sử dụng");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.getProject().getUsageCost(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(2);
//		cell.setCellValue("Chi phí tối đa cho phép");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.getProject().getLimitCost(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Mục đich kế hoạch");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.getPurpose(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Địa điểm");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.getPlace(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Làm việc với");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.getPartner(), ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Kế hoạch phát sinh");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.isArised()? "Có":"Không", ""));
		rowCount++;

		row = sheet.getRow(rowCount);
//		cell = row.getCell(1);
//		cell.setCellValue("Ứng trước");
		cell = row.getCell(4);
		cell.setCellValue(Objects.toString(plan.isAdvance()? "Có":"Không", ""));
		rowCount+=2;

		// if (plan.getJobDetails()==null || plan.getJobDetails().size()==0)
		// return;
		Iterator<JobDetail> iterator = plan.getJobDetails().iterator();
		while (iterator.hasNext()) {
			rowCount = writeJob(iterator.next(), rowCount);
		}

		workbook.write(os);
		os.close();
		fis.close();

		return outFilePath;
	}

	private int writeJob(JobDetail job, int rowCount) {
		Sheet sheet = workbook.getSheetAt(0);
		Row row;
		Cell cell;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		row = sheet.createRow(rowCount);
		cell = row.createCell(2);
		cell.setCellValue("Tên");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(Objects.toString(job.getJob().getName(), ""));
		cell.setCellStyle(cellStyle);
		rowCount++;

		row = sheet.createRow(rowCount);
		cell = row.createCell(2);
		cell.setCellValue("Số lượng");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(Objects.toString(job.getQuantity(), ""));
		cell.setCellStyle(cellStyle);
		rowCount++;

		row = sheet.createRow(rowCount);
		cell = row.createCell(2);
		cell.setCellValue("Chi phí đề xuất");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(Objects.toString(job.getProposedCost(), ""));
		cell.setCellStyle(cellStyle);
		rowCount++;

		row = sheet.createRow(rowCount);
		cell = row.createCell(2);
		cell.setCellValue("Thuế");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(Objects.toString(job.getTax(), ""));
		cell.setCellStyle(cellStyle);
		rowCount++;

		row = sheet.createRow(rowCount);
		cell = row.createCell(2);
		cell.setCellValue("Chi phí không thuế");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(Objects.toString(job.getNoTaxCost(), ""));
		cell.setCellStyle(cellStyle);
		rowCount++;

		row = sheet.createRow(rowCount);
		cell = row.createCell(2);
		cell.setCellValue("Ngày bắt đầu");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(
				Objects.toString(job.getStartDate() != null ? simpleDateFormat.format(job.getStartDate()) : null, ""));
		cell.setCellStyle(cellStyle);
		rowCount++;

		row = sheet.createRow(rowCount);
		cell = row.createCell(2);
		cell.setCellValue("Ngày kết thúc");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(
				Objects.toString(job.getEndDate() != null ? simpleDateFormat.format(job.getEndDate()) : null, ""));
		cell.setCellStyle(cellStyle);
		rowCount += 2;

		Iterator<Bill> iterator = job.getBills().iterator();
		while (iterator.hasNext()) {
			rowCount = writeBill(iterator.next(), rowCount);
		}
		return rowCount;
	}

	private int writeBill(Bill bill, int rowCount) {
		Sheet sheet = workbook.getSheetAt(0);
		Row row;
		Cell cell;

		row = sheet.createRow(rowCount);
		cell = row.createCell(3);
		cell.setCellValue("Tên hóa đơn");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(Objects.toString(bill.getName(), ""));
		cell.setCellStyle(cellStyle);
		rowCount++;

		row = sheet.createRow(rowCount);
		cell = row.createCell(3);
		cell.setCellValue("Thuế");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(Objects.toString(bill.getTax(), ""));
		cell.setCellStyle(cellStyle);
		rowCount++;

		row = sheet.createRow(rowCount);
		cell = row.createCell(3);
		cell.setCellValue("Chi phí không thuế");
		cell.setCellStyle(cellStyle);
		cell = row.createCell(4);
		cell.setCellValue(Objects.toString(bill.getNoTaxCost(), ""));
		cell.setCellStyle(cellStyle);

		return ++rowCount;
	}
}
