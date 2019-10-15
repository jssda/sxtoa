package pers.jssd.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/*
    	 	OutputStream out = response.getOutputStream();
      		//response.reset();
      		response.setContentType("application/vnd.ms-excel");
      		response.setHeader("Content-disposition", "attachment; fileName=" + new String(("duty.xls").getBytes(), "iso8859-1"));
           // OutputStream outputStream = new FileOutputStream("D:/students.xls");
            workbook.write(out);
            out.close();



*/
public class ExcelOperate {

    public static void main(String[] args) {
        // 创建Excel表格
        createExcel(getStudent());

        // 读取Excel表格
        // List<Student> list = readExcel();
        // System.out.println(list.toString());
    }

    /**
     * 初始化数据
     *
     * @return 数据
     */
    private static List<Student> getStudent() {
        List<Student> list = new ArrayList<Student>();
        Student student1 = new Student("小明", 8, "二年级");
        Student student2 = new Student("小光", 9, "三年级");
        Student student3 = new Student("小花", 10, "四年级");
        list.add(student1);
        list.add(student2);
        list.add(student3);
        return list;
    }

    /**
     * 创建Excel
     *
     * @param list 数据
     */
    private static void createExcel(List<Student> list) {
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("学生表一");

        CellRangeAddress region = new CellRangeAddress(0, // first row
                0, // last row
                0, // first column
                2 // last column
        );
        sheet.addMergedRegion(region);

        HSSFRow hssfRow = sheet.createRow(0);
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("学生列表");

        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        /*
       
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       // cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
       
        HSSFFont font = workbook.createFont();
        font.setFontName("楷体"); //字体
        font.setFontHeightInPoints((short)30); //字体大小
        font.setColor(HSSFColor.RED.index);//颜色
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        font.setItalic(true); //倾斜
        cellStyle.setFont(font);
        */
        headCell.setCellStyle(cellStyle);


        // 添加表头行
        hssfRow = sheet.createRow(1);
        // 添加表头内容
        headCell = hssfRow.createCell(0);
        headCell.setCellValue("姓名");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("年龄");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("年级");
        headCell.setCellStyle(cellStyle);

        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
            hssfRow = sheet.createRow((int) i + 2);
            Student student = list.get(i);

            // 创建单元格，并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(student.getName());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(student.getAge());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(2);
            cell.setCellValue(student.getGrade());
            cell.setCellStyle(cellStyle);
        }

        // 保存Excel文件
        try {
            OutputStream outputStream = new FileOutputStream("D:/students.xls");
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Excel
     *
     * @return 数据集合
     */
    private static List<Student> readExcel() {
        List<Student> list = new ArrayList<Student>();
        HSSFWorkbook workbook = null;

        try {
            // 读取Excel文件
            InputStream inputStream = new FileInputStream("D:/students.xls");
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 循环工作表
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行
            for (int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }

                // 将单元格中的内容存入集合
                Student student = new Student();

                HSSFCell cell = hssfRow.getCell(0);
                if (cell == null) {
                    continue;
                }
                student.setName(cell.getStringCellValue());

                cell = hssfRow.getCell(1);
                if (cell == null) {
                    continue;
                }
                student.setAge((int) cell.getNumericCellValue());

                cell = hssfRow.getCell(2);
                if (cell == null) {
                    continue;
                }
                student.setGrade(cell.getStringCellValue());

                list.add(student);
            }
        }
        return list;
    }
}


class Student {

    private String name;
    private int age;
    private String grade;

    public Student() {
    }

    public Student(String name, int age, String grade) {
        super();
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", grade=" + grade
                + "]";
    }
}