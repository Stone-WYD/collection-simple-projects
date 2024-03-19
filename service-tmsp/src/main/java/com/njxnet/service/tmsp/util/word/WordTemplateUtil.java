package com.njxnet.service.tmsp.util.word;


import com.njxnet.service.tmsp.util.word.assist.CustomXWPFDocument;
import com.njxnet.service.tmsp.util.word.assist.DocxTemplatePicTokenHandler;
import com.njxnet.service.tmsp.util.word.assist.DocxTemplatePicTokenParser;
import com.njxnet.service.tmsp.util.word.assist.MapForDocxTokenHandler;
import org.apache.ibatis.parsing.GenericTokenParser;

import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * word 模型转换工具类
 */
public class WordTemplateUtil {

    private static final Logger logger = LoggerFactory.getLogger(WordTemplateUtil.class);

    public static void generateWordToOs(String fileTemplatePath, HashMap<String, String> params, OutputStream os) {
        try{
            FileInputStream fis = new FileInputStream(fileTemplatePath);
            CustomXWPFDocument document = new CustomXWPFDocument(fis);
            // 根据参数创建文字解析器
            GenericTokenParser textParser = new GenericTokenParser("${", "}",  new MapForDocxTokenHandler(params));
            // 根据参数创建图片解析器
            DocxTemplatePicTokenParser picParser = new DocxTemplatePicTokenParser("#{", "}",  new DocxTemplatePicTokenHandler(params));
            // 模板占位符内容替换
            replaceInPara(document, textParser, picParser);
            replaceInTable(document, textParser, picParser);
            // 文件流写入到输出流中
            document.write(os);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private static void replaceInPara(CustomXWPFDocument doc, GenericTokenParser textParser, DocxTemplatePicTokenParser picParser) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceContentInPara(para, textParser, picParser, doc);
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private static void replaceInTable(CustomXWPFDocument doc, GenericTokenParser textParser, DocxTemplatePicTokenParser picParser) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceContentInPara(para, textParser, picParser,doc);
                    }
                }
            }
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    private static void replaceContentInPara(XWPFParagraph para, GenericTokenParser textParser, DocxTemplatePicTokenParser picParser, CustomXWPFDocument doc) {
        String text = para.getText();
        if (text.contains("{") && text.contains("}")) {
            List<XWPFRun> runList = para.getRuns();
            if (text.contains("${")) {
                // 获取所有 runs 并修改带占位符的文字内容：具有同种格式的内容会放在一个 run 中
                for (XWPFRun run : runList) {
                    run.setText(textParser.parse(run.text()), 0);
                }
            }

            if (text.contains("#{")) {
                // 2024/1/17 处理段落中的图片替换内容
                for (XWPFRun run : runList) {
                    try {
                        run.setText(picParser.parse(run.text(), doc, run), 0);
                    } catch (Exception e) {
                        logger.error("word文档中图片处理失败：{}", e.getMessage());
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        HashMap<String, String> params = new HashMap<>();
        params.put("place", "王玉东测试");
        params.put("picture", "C:\\Users\\86132\\Pictures\\Saved Pictures\\壁纸3.png, 100, 50");
        params.put("picture2", "C:\\Users\\86132\\Pictures\\Saved Pictures\\壁纸5.png, 100, 50");
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\86132\\Desktop\\test.docx")) {
            generateWordToOs("src/main/resources/word_template/调解笔录.docx", params, fos);
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println("发生错误了。。。");
        }
    }
}



