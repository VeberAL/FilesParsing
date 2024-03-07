import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class ZipTest {
    private static final ClassLoader classLoader = ZipTest.class.getClassLoader();
    private static final String zipFileName = "Files.zip";
    private static final String csvFileName = "Jedi.csv";
    private static final String pdfFileName = "JavaCoffee.pdf";
    private static final String xlsxFileName = "Fullbody.xlsx";
    private static final String pdfFileValue = "Кофе – мой друг,";
    private static final String xlsxFileValue = "УТРОМ";
    private static final String csvFileValue = "{";


    private InputStream getInputStreamFileInZipFile(String fileName) throws IOException {
        ZipInputStream is = new ZipInputStream(Objects.requireNonNull(classLoader.getResourceAsStream(zipFileName)));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            if (entry.getName().equals(fileName)) {
                return is;
            }
        }
        throw new IOException("Файл " + fileName + " не найден в ZIP-архиве");
    }

    @Test
    @DisplayName("Поиск значения в CSV в ZIP-архиве")
    void csvFileInZipSearchTest() throws Exception {
        try (InputStream inputStream = getInputStreamFileInZipFile(csvFileName);
             CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String valueCsv = reader.readNext()[0];
            assertThat(valueCsv).contains(csvFileValue);
        }
    }

    @Test
    @DisplayName("Поиск значения в PDF в ZIP-архиве")
    void pdfFileInZipSearchTest() throws IOException {
        try (InputStream inputStream = getInputStreamFileInZipFile(pdfFileName)) {
            PDF pdf = new PDF(inputStream);
            assertThat(pdf).containsExactText(pdfFileValue);
        }
    }

    @Test
    @DisplayName("Поиск значения в XLSX в ZIP-архиве")
    void xlsxFileInZipSearchTest() throws IOException {
        try (InputStream inputStream = getInputStreamFileInZipFile(xlsxFileName)) {
            XLS xlsx = new XLS(inputStream);
            String cellValueXlsx = xlsx.excel.getSheetAt(0).getRow(0)
                    .getCell(1).getStringCellValue();
            assertThat(cellValueXlsx).contains(xlsxFileValue);
        }
    }


}
