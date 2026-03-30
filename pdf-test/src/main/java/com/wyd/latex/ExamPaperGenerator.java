package com.wyd.latex;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
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
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExamPaperGenerator {

    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth();
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight();
    private static final float MARGIN_LEFT = 60;
    private static final float MARGIN_RIGHT = 60;
    private static final float MARGIN_TOP = 80;
    private static final float MARGIN_BOTTOM = 60;
    private static final int LATEX_FONT_SIZE = 10;
    private static final Pattern LATEX_PATTERN = Pattern.compile("\\$([^$]+)\\$");
    private static final Pattern URL_PATTERN = Pattern.compile("(https?://\\S+)$");

    /** 封装页面状态，支持绘制方法内部触发分页 */
    private static class PageContext {
        PDDocument document;
        PDPageContentStream cs;
        PDType0Font font;
        float y;

        PageContext(PDDocument document, PDType0Font font) throws IOException {
            this.document = document;
            this.font = font;
            newPage();
        }

        void newPage() throws IOException {
            if (cs != null) {
                cs.close();
            }
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            cs = new PDPageContentStream(document, page);
            y = PAGE_HEIGHT - MARGIN_TOP;
        }

        void ensureSpace(float needed) throws IOException {
            if (y - needed < MARGIN_BOTTOM) {
                newPage();
            }
        }

        void close() throws IOException {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> questions = new ArrayList<>();
        questions.add("若函数$y = \\frac{{kx} + 7}{k{x}^{2} + {4kx} + 3}$的定义域为 $R$ ,则 $k$ 的取值范围是_____https://fd-edu.oss-cn-beijing.aliyuncs.com/fd-edu/20260308/900-1-21_1772940643727.png,https://fd-edu.oss-cn-beijing.aliyuncs.com/fd-edu/20260308/900-1-21_1772940643727.png");
        questions.add("已知 $f\\left( {x + \\frac{1}{x}}\\right)  = \\ln \\left| x\\right|  - \\frac{1}{2}\\ln \\left( {{x}^{4} + 1}\\right)$ ,求 $f\\left( x\\right)$ .");
        questions.add("设 $a > 0$ 且 $a \\neq 1$ ,函数 $f(x) = a^{x}$ 在 $[1,2]$ 上的最大值与最小值之差为 $\\frac{a}{2}$ ,求 $a$ 的值.");

        List<String> answers = new ArrayList<>();
        answers.add("这是答案这是答案这是答案这是答案这是答案这是答案若函数$y = \\frac{{kx} + 7}{k{x}^{2} + {4kx} + 3}$的定义域为 $R$ ,则 $k$ 的取值范围是_____");
        answers.add("这是答案这是答案这是答案这是答案这是答案这是答案这是答案已知 $f\\left( {x + \\frac{1}{x}}\\right)  = \\ln \\left| x\\right|  - \\frac{1}{2}\\ln \\left( {{x}^{4} + 1}\\right)$ ,求 $f\\left( x\\right)$ .");
        answers.add("这是答案这是答案这是答案这是答案这是答案这是答案这是答案设 $a > 0$ 且 $a \\neq 1$ ,函数 $f(x) = a^{x}$ 在 $[1,2]$ 上的最大值与最小值之差为 $\\frac{a}{2}$ ,求 $a$ 的值.https://fd-edu.oss-cn-beijing.aliyuncs.com/fd-edu/20260308/900-1-21_1772940643727.png,https://fd-edu.oss-cn-beijing.aliyuncs.com/fd-edu/20260308/900-1-21_1772940643727.png");

        String output = "C:\\Users\\11748\\Documents\\" + DateUtil.calendar().getTimeInMillis() + ".pdf";
        generate(questions, answers, output);
        System.out.println("试卷已生成: " + output);
    }

    public static void generate(List<String> questions, List<String> answers, String outputPath) throws IOException {
        PDDocument document = new PDDocument();
        // 确认字体
        PDType0Font font = loadChineseFont(document);
        PageContext ctx = new PageContext(document, font);

        // 绘制题目
        drawTitle(ctx, "数学试卷", 22);
        ctx.y -= 20;

        for (int i = 0; i < questions.size(); i++) {
            String text = (i + 1) + ". " + questions.get(i);
            // 如果空间不够，就再起一页
            ctx.ensureSpace(40);
            drawSegment(ctx, text, 14, 14);
            ctx.y -= 20;
        }

        // 答案解析（新起一页）
        ctx.newPage();
        drawTitle(ctx, "答案解析", 20);
        ctx.y -= 15;

        for (int i = 0; i < answers.size(); i++) {
            String text = (i + 1) + ". " + answers.get(i);
            ctx.ensureSpace(30);
            ctx.cs.setNonStrokingColor(0.35f, 0.35f, 0.35f);
            drawSegment(ctx, text, 14, 14);
            ctx.y -= 12;
        }

        ctx.close();
        document.save(outputPath);
        document.close();
    }

    private static void drawTitle(PageContext ctx, String title, float size) throws IOException {
        float w = ctx.font.getStringWidth(title) / 1000 * size;
        ctx.cs.setNonStrokingColor(0f, 0f, 0f);
        ctx.cs.beginText();
        ctx.cs.setFont(ctx.font, size);
        ctx.cs.newLineAtOffset((PAGE_WIDTH - w) / 2, ctx.y);
        ctx.cs.showText(title);
        ctx.cs.endText();
        ctx.y -= size + 10;
    }

    /** 预计算后的渲染元素 */
    private static class RenderItem {
        String text;           // 文字段（二选一）
        PDImageXObject image;  // 图片段（二选一）
        float width;
        float height;          // 图片高度，文字为fontSize
    }

    /** 绘制部分内容 */
    private static void drawSegment(PageContext ctx, String text, float fontSize, float lineHeight) throws IOException {
        // === 提取末尾的图片URL ===
        List<String> imageUrls = new ArrayList<>();
        Matcher urlMatcher = URL_PATTERN.matcher(text);
        if (urlMatcher.find()) {
            String urlPart = urlMatcher.group(1);
            text = text.substring(0, urlMatcher.start());
            for (String url : urlPart.split(",")) {
                url = url.trim();
                if (!url.isEmpty()) imageUrls.add(url);
            }
        }

        // === 第一遍：解析所有段，预计算尺寸 ===
        Matcher matcher = LATEX_PATTERN.matcher(text);
        List<RenderItem> items = new ArrayList<>();
        int last = 0;
        while (matcher.find()) {
            if (matcher.start() > last) {
                RenderItem item = new RenderItem();
                item.text = text.substring(last, matcher.start());
                // 计算文字段在 pdf 中的实际宽度
                item.width = ctx.font.getStringWidth(item.text) / 1000 * fontSize;
                item.height = fontSize;
                items.add(item);
            }
            String latex = matcher.group(1);
            String b64 = LatexUtils.latexToBase64Str(latex, LATEX_FONT_SIZE);
            byte[] bytes = Base64.getDecoder().decode(b64.split(",")[1]);
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytes));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            PDImageXObject img = PDImageXObject.createFromByteArray(ctx.document, baos.toByteArray(), "latex");

            // 根据图片实际高度判断：简单公式（如单字母、简单表达式）图片本身就不高
            float ph = img.getHeight() * 0.08f;
            float th;
            if (ph <= fontSize * 1.2f && latex.length() < 10) {
                // 图片本身不高，固定与文字等高
                if (latex.length() == 1) {
                    th = fontSize * 0.8f;
                } else if (latex.length() < 5) {
                    th = fontSize * 0.9f;
                } else {
                    th = fontSize;
                }
            } else {
                // 复杂公式（分数、积分等），按比例放大，上限 fontSize*2.5
                th = Math.min(ph, fontSize * 2.2f);
            }
            float sc = th / img.getHeight();

            RenderItem item = new RenderItem();
            item.image = img;
            item.width = img.getWidth() * sc;
            item.height = th;
            items.add(item);
            last = matcher.end();
        }
        if (last < text.length()) {
            RenderItem item = new RenderItem();
            item.text = text.substring(last);
            item.width = ctx.font.getStringWidth(item.text) / 1000 * fontSize;
            item.height = fontSize;
            items.add(item);
        }

        // === 第二遍：按宽度分行，找出每行最高元素 ===
        float maxWidth = PAGE_WIDTH - MARGIN_LEFT - MARGIN_RIGHT;
        List<List<RenderItem>> lines = new ArrayList<>();
        List<RenderItem> currentLine = new ArrayList<>();
        float currentWidth = 0;

        for (RenderItem item : items) {
            if (currentWidth + item.width > maxWidth && !currentLine.isEmpty()) {
                lines.add(currentLine);
                currentLine = new ArrayList<>();
                currentWidth = 0;
            }
            currentLine.add(item);
            currentWidth += item.width;
        }
        if (!currentLine.isEmpty()) lines.add(currentLine);

        // === 第三遍：逐行绘制，每行统一下移一次 ===
        for (List<RenderItem> line : lines) {
            float maxH = lineHeight;
            for (RenderItem item : line) {
                if (item.height > maxH) maxH = item.height;
            }

            // 整行统一下移：为最高元素腾出空间
            if (maxH > lineHeight) {
                ctx.y -= (maxH - lineHeight);
            }
            ctx.ensureSpace(maxH);

            float x = MARGIN_LEFT;
            for (RenderItem item : line) {
                if (item.text != null) {
                    ctx.cs.beginText();
                    ctx.cs.setFont(ctx.font, fontSize);
                    ctx.cs.newLineAtOffset(x, ctx.y);
                    ctx.cs.showText(item.text);
                    ctx.cs.endText();
                } else {
                    // 图片垂直居中于行高
                    float imgY = ctx.y - (item.height - fontSize) / 2;
                    ctx.cs.drawImage(item.image, x, imgY, item.width, item.height);
                }
                x += item.width;
            }
            ctx.y -= lineHeight;
        }

        // === 绘制末尾的网络图片 ===
        if (!imageUrls.isEmpty()) {
            float imgFixedHeight = lineHeight * 3;
            float x = MARGIN_LEFT;
            float contentWidth = PAGE_WIDTH - MARGIN_LEFT - MARGIN_RIGHT;

            for (String urlStr : imageUrls) {
                try {
                    PDImageXObject netImg = PDImageXObject.createFromByteArray(
                            ctx.document, downloadImage(urlStr), urlStr);
                    float scale = imgFixedHeight / netImg.getHeight();
                    float imgW = netImg.getWidth() * scale;

                    // 超出右边距则换行
                    if (x + imgW > PAGE_WIDTH - MARGIN_RIGHT && x > MARGIN_LEFT) {
                        ctx.y -= imgFixedHeight + 4;
                        x = MARGIN_LEFT;
                    }
                    // 图片太宽则缩放到页面宽度
                    if (imgW > contentWidth) {
                        imgW = contentWidth;
                        imgFixedHeight = netImg.getHeight() * (imgW / netImg.getWidth());
                    }

                    ctx.ensureSpace(imgFixedHeight);
                    ctx.cs.drawImage(netImg, x, ctx.y - imgFixedHeight, imgW, imgFixedHeight);
                    x += imgW + 8; // 图片间留8pt间距
                } catch (IOException e) {
                    // 图片下载失败，跳过
                }
            }
            ctx.y -= imgFixedHeight + 4;
        }
    }

    private static byte[] downloadImage(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        try (InputStream in = url.openStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        }
    }

    private static PDType0Font loadChineseFont(PDDocument doc) throws IOException {
        InputStream is = ExamPaperGenerator.class.getResourceAsStream("/fonts/simhei.ttf");
        if (is != null) return PDType0Font.load(doc, is);
        String[] paths = {"C:\\Windows\\Fonts\\msyh.ttc", "C:\\Windows\\Fonts\\simhei.ttf",
                "C:\\Windows\\Fonts\\simsun.ttc", "/System/Library/Fonts/PingFang.ttc"};
        for (String p : paths) {
            File f = new File(p);
            if (f.exists()) return PDType0Font.load(doc, f);
        }
        throw new IOException("未找到可用的中文字体，请将字体文件放到 src/main/resources/fonts/ 目录下");
    }
}