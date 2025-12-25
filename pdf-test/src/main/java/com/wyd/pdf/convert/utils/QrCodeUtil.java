package com.wyd.pdf.convert.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.util.Base64;

public class QrCodeUtil {

    /**
     * 生成二维码Base64字符串
     * @param content 二维码内容（如订单链接、订单号）
     * @param width 二维码宽度（像素）
     * @param height 二维码高度（像素）
     * @return Base64编码的二维码图片（data:image/png;base64,...）
     */
    public static String generateQrCodeBase64(String content, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            // 生成二维码矩阵
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    width,
                    height
            );
            // 配置二维码颜色（黑底白块）
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);

            // 矩阵转字节流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(
                    bitMatrix,
                    "PNG",
                    outputStream,
                    config
            );

            // 字节流转Base64
            byte[] imageBytes = outputStream.toByteArray();
            String base64 = Base64.getEncoder().encodeToString(imageBytes);
            return "data:image/png;base64," + base64; // HTML img可直接识别的格式
        } catch (Exception e) {
            throw new RuntimeException("二维码生成失败", e);
        }
    }

}
