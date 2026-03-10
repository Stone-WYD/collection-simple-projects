package com.wyd.latex;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Test2 {

    public static void main(String[] args) {
        String latex = "{f}^{\\prime }(1) = 0,\\mathop{\\lim }\\limits_{{x \\rightarrow 1}}\\frac{{f}^{\\prime \\prime }(x)}{{\\left( x - 1\\right) }^{2}} = \\frac{2}{3}";

        try {
            BufferedImage image = latexToImage(latex, 1, "BLACK");
            ImageIO.write(image, "PNG", new java.io.File("d:/latex_formula_test2.png"));

            System.out.println("LaTeX公式图片生成成功！");
            System.out.println("图片尺寸: " + image.getWidth() + "x" + image.getHeight());
            System.out.println("图片已保存到: d:/latex_formula_test2.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage latexToImage(String latex, int fontSize, String color) throws IOException {
        TeXFormula formula = new TeXFormula(latex);

        int style = TeXConstants.STYLE_DISPLAY;
        int size = fontSize * 20;

        TeXIcon icon = formula.new TeXIconBuilder()
                .setStyle(style)
                .setSize(size)
                .build();

        icon.setInsets(new Insets(0, 0, 0, 0));

        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        BufferedImage image = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, iconWidth, iconHeight);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        icon.paintIcon(new JLabel(), g2d, 0, 0);
        g2d.dispose();

        BufferedImage optimizedImage = toIndexColorImage(image);
        return optimizedImage;

    }

    private static BufferedImage toIndexColorImage(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();

        BufferedImage indexed = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        indexed.getGraphics().drawImage(source, 0, 0, null);

        return indexed;
    }
}
