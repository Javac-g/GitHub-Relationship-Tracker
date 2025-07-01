package com.max.tracker;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class ExcelExporter {

    public static void export(
            Set<String> followers,
            Set<String> following,
            Set<String> mutual,
            Set<String> notSubscribed,
            Set<String> unsubscribed,
            Set<String> notFollowing
    ) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            writeSheet(workbook, "Followers", followers);
            writeSheet(workbook, "Following", following);
            writeSheet(workbook, "Mutual", mutual);
            writeSheet(workbook, "Not Subscribed", notSubscribed);
            writeSheet(workbook, "Unsubscribed", unsubscribed);
            writeSheet(workbook, "Not Following", notFollowing);

            try (FileOutputStream fos = new FileOutputStream("github_followers_report.xlsx")) {
                workbook.write(fos);
            }
        }
    }

    private static void writeSheet(Workbook workbook, String name, Set<String> data) {
        Sheet sheet = workbook.createSheet(name);
        int row = 0;
        for (String user : data) {
            sheet.createRow(row++).createCell(0).setCellValue(user);
        }
    }
}
