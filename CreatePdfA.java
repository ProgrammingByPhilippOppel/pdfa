import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
/*
STEP0_Prereq:
    - include itextpdf (latest version)
    - include itext-pdfa (latest version)
    - use Java8 as JDK
*/
public class CreatePdfA {

    public static void main(String[] args) {

        /*
        STEP1_Setup:
            - initialize the document and the PdfAWriter
            - declare embeddable Font and ICCProfile (ColorProfile)
        */
        Document document = new Document();
        try {
            PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream("test.pdf"), PdfAConformanceLevel.PDF_A_3B);
            File colorProfile = new File("/home/user1/IdeaProjects/helloyt/src/main/java/sRGB_CS_profile.icm");
            ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream(colorProfile));
            String FONT = "/home/user1/IdeaProjects/helloyt/src/main/java/OpenSans-Regular.ttf";
            Font font = new Font(BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);

            /*
            STEP2_Prepare:
                - open Document to edit
                - add ColorProfile for conformance-requirements
            */
            document.open();
            writer.createXmpMetadata();
            writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);

            /*
            STEP3_Write:
                - create Element (always define Font with embedded one)
                - add Element to document
            */
            PdfPTable table = new PdfPTable(5);
            for (int i = 0; i < 5; i++) {
                PdfPCell cell = new PdfPCell(new Phrase("Header " + i, font));
                cell.setBackgroundColor(new BaseColor(0,255,255));
                table.addCell(cell);
            }
            table.setHeaderRows(1);

            for (int i = 0; i < 22500; i++) {
                table.addCell(new Phrase("Test " + i, font));
            }

            document.add(table);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*
            STEP4_Close:
                - close inside finally, in order to not create a zombie
            */
            document.close();
        }

    }
}
