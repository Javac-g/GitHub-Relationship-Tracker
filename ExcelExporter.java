package com.max.tracker;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class ExcelExporter {
    public static void export(Set<String> followers, Set<String> notSubscribed, Set<String> unsubscribed) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            writeSheet(workbook, "Followers", followers);
            writeSheet(workbook, "Not Subscribed", notSubscribed);
            writeSheet(workbook, "Unsubscribed", unsubscribed);

            try (FileOutputStream fos = new FileOutputStream("github_followers_report.xlsx")) {
                workbook.write(fos);
            }
        }
    }

    private static void writeSheet(Workbook wb, String name, Set<String> data) {
        Sheet sheet = wb.createSheet(name);
        int row = 0;
        for (String user : data) {
            sheet.createRow(row++).createCell(0).setCellValue(user);
        }
    }
}
