package com.wyd.latex.util;

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

public class LatexUtils {

    public static String latexToBase64Str(String latex, int fontSize) {
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(optimizedImage, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException("latex 代码转图片失败！");
        }
        byte[] imageBytes = baos.toByteArray();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

    private static BufferedImage toIndexColorImage(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();

        BufferedImage indexed = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        indexed.getGraphics().drawImage(source, 0, 0, null);

        return indexed;
    }


}
