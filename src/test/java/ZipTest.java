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
    private static final ClassLoader CLASS_LOADER = ZipTest.class.getClassLoader();
    private static final String ZIP_FILE_NAME = "Files.zip";
    private static final String CSV_FILE_NAME = "Jedi.csv";
    private static final String PDF_FILE_NAME = "JavaCoffee.pdf";
    private static final String XLSX_FILE_NAME = "Fullbody.xlsx";
    private static final String PDF_FILE_VALUE = "Кофе – мой друг,";
    private static final String XLSX_FILE_VALUE = "УТРОМ";
    private static final String CSV_FILE_VALUE = "{";


    private InputStream getInputStreamFileInZipFile(String fileName) throws IOException {
        ZipInputStream is = new ZipInputStream(Objects.requireNonNull(CLASS_LOADER.getResourceAsStream(ZIP_FILE_NAME)));
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
        try (InputStream inputStream = getInputStreamFileInZipFile(CSV_FILE_NAME);
             CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String valueCsv = reader.readNext()[0];
            assertThat(valueCsv).contains(CSV_FILE_VALUE);
        }
    }

    @Test
    @DisplayName("Поиск значения в PDF в ZIP-архиве")
    void pdfFileInZipSearchTest() throws IOException {
        try (InputStream inputStream = getInputStreamFileInZipFile(PDF_FILE_NAME)) {
            PDF pdf = new PDF(inputStream);
            assertThat(pdf).containsExactText(PDF_FILE_VALUE);
        }
    }

    @Test
    @DisplayName("Поиск значения в XLSX в ZIP-архиве")
    void xlsxFileInZipSearchTest() throws IOException {
        try (InputStream inputStream = getInputStreamFileInZipFile(XLSX_FILE_NAME)) {
            XLS xlsx = new XLS(inputStream);
            String cellValueXlsx = xlsx.excel.getSheetAt(0).getRow(0)
                    .getCell(1).getStringCellValue();
            assertThat(cellValueXlsx).contains(XLSX_FILE_VALUE);
        }
    }


}
