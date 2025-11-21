package com.wyd.pdf;

/**
 * @program: pdf-test
 * @author: Stone
 * @create: 2024-05-05 14:51
 */

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.File;
import java.io.IOException;

/**
 * pdf 加密
 * */
public class PdfProtectionTest {

    public static void main(String[] args) throws IOException {

        PDDocument document = PDDocument.load(new File("C:\\Users\\Stone\\Desktop\\我的简历 (1).pdf"));

        // Replace with your desired user password
        String userPassword = "123456";

        // Replace with your desired owner password
        String ownerPassword = "123456";

        AccessPermission accessPermission = new AccessPermission();

        // Set to true if you want to allow printing
        accessPermission.setCanPrint(false);

        StandardProtectionPolicy protectionPolicy = new StandardProtectionPolicy(ownerPassword, userPassword, accessPermission);
        try{
            document.protect(protectionPolicy);
            document.save("outprotect.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}