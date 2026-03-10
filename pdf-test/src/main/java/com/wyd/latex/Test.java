package com.wyd.latex;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.TeXFormula;

import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

public class Test {

    public static void main(String[] args) {
        String latex = "{f}^{\\prime }(1) = 0,\\mathop{\\lim }\\limits_{{x \\rightarrow 1}}\\frac{{f}^{\\prime \\prime }(x)}{{\\left( x - 1\\right) }^{2}} = \\frac{2}{3}";

        try {
            BufferedImage image = latexToImage(latex, 1, "BLACK");
            saveImageCompressed(image, "d:/latex_formula_wechat2.jpg", 0.7f);
            System.out.println("LaTeX公式图片生成成功！");
            System.out.println("图片尺寸: " + image.getWidth() + "x" + image.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage latexToImage(String latex, int fontSize, String color) {
        TeXFormula formula = new TeXFormula(latex);

        int style = TeXConstants.STYLE_DISPLAY;
        int size = fontSize * 20;

        TeXIcon icon = formula.new TeXIconBuilder()
                .setStyle(style)
                .setSize(size)
                .build();

        icon.setInsets(new Insets(2, 2, 2, 2));

        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        BufferedImage image = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, iconWidth, iconHeight);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        icon.paintIcon(new JLabel(), g2d, 0, 0);
        g2d.dispose();

        return image;
    }

    public static BufferedImage latexToImageNoBackground(String latex, int fontSize, String color) {
        TeXFormula formula = new TeXFormula(latex);

        int style = TeXConstants.STYLE_DISPLAY;
        int size = fontSize * 20;

        TeXIcon icon = formula.new TeXIconBuilder()
                .setStyle(style)
                .setSize(size)
                .build();

        icon.setInsets(new Insets(2, 2, 2, 2));

        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        BufferedImage image = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, iconWidth, iconHeight);
        g2d.setComposite(AlphaComposite.SrcOver);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        icon.paintIcon(new JLabel(), g2d, 0, 0);
        g2d.dispose();

        return image;
    }

    public static void saveImageCompressed(BufferedImage image, String path, float quality) throws Exception {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        String format = path.toLowerCase().endsWith(".jpg") || path.toLowerCase().endsWith(".jpeg") ? "jpg" : "png";

        if ("jpg".equals(format)) {
            Iterator<ImageWriter> writers = javax.imageio.ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();
            ImageOutputStream ios = javax.imageio.ImageIO.createImageOutputStream(file);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
            ios.close();
            writer.dispose();
        } else {
            javax.imageio.ImageIO.write(image, "PNG", file);
        }
    }

}
