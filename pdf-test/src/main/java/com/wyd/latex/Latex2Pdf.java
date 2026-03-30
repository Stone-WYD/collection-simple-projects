package com.wyd.latex;

import com.wyd.latex.util.LatexUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Latex2Pdf {

    public static void main(String[] args) throws IOException {
        List<String> question = new ArrayList<>();
        question.add("若函数$y = \\frac{{kx} + 7}{k{x}^{2} + {4kx} + 3}$的定义域为 $R$ ,则 $k$ 的取值范围是_____");
        question.add("已知 $f\\left( {x + \\frac{1}{x}}\\right)  = \\ln \\left| x\\right|  - \\frac{1}{2}\\ln \\left( {{x}^{4} + 1}\\right)$ ,求 $f\\left( x\\right)$ .");
        String output = "C:\\Users\\11748\\Documents\\latexTest37.pdf";
        generatePdf(question, output);
    }

    public static void generatePdf(List<String> questions, String outputPath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        float yPosition = page.getMediaBox().getHeight() - 50;
        Pattern pattern = Pattern.compile("\\$([^$]+)\\$");
        PDType0Font font = PDType0Font.load(document, new File("C:\\Windows\\Fonts\\simhei.ttf"));

        for (String question : questions) {
            String[] lines = question.split("\n");
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                float xPosition = 50;
                int lastEnd = 0;

                contentStream.beginText();
                contentStream.setFont(font, 14);
                contentStream.newLineAtOffset(xPosition, yPosition);

                while (matcher.find()) {
                    if (matcher.start() > lastEnd) {
                        contentStream.showText(line.substring(lastEnd, matcher.start()));
                    }
                    contentStream.endText();

                    String latex = matcher.group(1);
                    String base64 = LatexUtils.latexToBase64Str(latex, 10);
                    byte[] imageBytes = Base64.getDecoder().decode(base64.split(",")[1]);

                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", baos);

                    PDImageXObject image = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "latex");
                    float imgHeight = image.getHeight() * 0.5f;
                    float imgWidth = image.getWidth() * 0.5f;
                    contentStream.drawImage(image, xPosition, yPosition - 14, imgWidth, imgHeight);

                    xPosition += imgWidth;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    lastEnd = matcher.end();
                }

                if (lastEnd < line.length()) {
                    contentStream.showText(line.substring(lastEnd));
                }
                contentStream.endText();

                yPosition -= 30;
                if (yPosition < 50) {
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = page.getMediaBox().getHeight() - 50;
                }
            }
            yPosition -= 20;
            if (yPosition < 50) {
                contentStream.close();
                page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                yPosition = page.getMediaBox().getHeight() - 50;
            }
        }

        contentStream.close();
        document.save(outputPath);
        document.close();
    }
}
