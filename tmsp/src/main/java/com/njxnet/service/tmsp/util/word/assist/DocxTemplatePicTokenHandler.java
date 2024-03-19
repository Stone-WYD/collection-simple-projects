package com.njxnet.service.tmsp.util.word.assist;

import cn.hutool.core.util.StrUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class DocxTemplatePicTokenHandler {


    private final Map<String, String> map;

    public DocxTemplatePicTokenHandler(Map<String, String> map) {
        this.map = map;
    }

    public String handleToken(String key, CustomXWPFDocument doc, XWPFRun run) throws InvalidFormatException, IOException {
        String content = map.get(key);
        if (run != null && StrUtil.isNotEmpty(content)) {
            String[] arrs = content.split(",");
            if (arrs.length != 3) {
                throw new RuntimeException("文档中插入图片时，需要在 #{} 中插入三个参数(逗号隔开)，分别为：图片url、图片长度、图片宽度");
            }
            // 获取 url，长，宽
            String url = arrs[0];
            int width = Integer.parseInt(arrs[1].trim());
            int height = Integer.parseInt(arrs[2].trim());
            // 插入图片，分情况，看图片从何处获取
            String blipId = doc.addPictureData(Files.newInputStream(Paths.get(url)), CustomXWPFDocument.PICTURE_TYPE_PNG);
            doc.createPicture(blipId, doc.getNextPicNameNumber(CustomXWPFDocument.PICTURE_TYPE_PNG), width, height, run);
        }
        return "";
    }
}
