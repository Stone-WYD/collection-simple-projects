package com.wyd.pdf.convert.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * FreeMarker模板工具类
 */
public class FreeMarkerUtil {
    // 模板配置（单例）
    private static final Configuration CONFIG;

    static {
        CONFIG = new Configuration(Configuration.VERSION_2_3_32);
        try {
            // 设置模板目录（resources/templates下）
            CONFIG.setClassForTemplateLoading(FreeMarkerUtil.class, "/templates");
            // 设置编码
            CONFIG.setDefaultEncoding("UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("FreeMarker初始化失败", e);
        }
    }

    /**
     * 渲染模板生成HTML字符串
     * @param templateName 模板文件名（如template.ftl）
     * @param data 模板数据
     * @return 渲染后的HTML字符串
     */
    public static String renderTemplate(String templateName, Map<String, Object> data) {
        try (Writer writer = new StringWriter()) {
            Template template = CONFIG.getTemplate(templateName);
            template.process(data, writer);
            return writer.toString();
        } catch (TemplateException e) {
            throw new RuntimeException("模板渲染失败", e);
        } catch (Exception e) {
            throw new RuntimeException("IO异常", e);
        }
    }
}
