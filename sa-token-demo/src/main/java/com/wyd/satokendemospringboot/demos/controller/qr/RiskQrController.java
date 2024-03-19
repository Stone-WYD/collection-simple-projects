package com.wyd.satokendemospringboot.demos.controller.qr;

import com.wyd.satokendemospringboot.demos.model.Report;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: RongHeCourtV1.0
 * @description: 二维码下载风险评估报告 controller
 * @author: Stone
 * @create: 2023-08-30 13:42
 **/
@Api(tags = "二维码下载风险评估报告")
@Controller
@CrossOrigin
@RequestMapping(value = "/Lra")
public class RiskQrController {


    @RequestMapping(value = "/create_code/down", method = RequestMethod.GET)
    @ApiOperation(value = "二维码扫描出的url", notes = "二维码扫描出的url")
    public String createCode(@RequestParam("reportId") String reportId, Model model) {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        reports.add(report);
        // 填充 report
        report.setName("test");
        List<String> contents = new ArrayList<>();
        contents.add(reportId);
        contents.add("334455");
        report.setContents(contents);
        // 添加一个图片
        model.addAttribute("durationUrl", "http://127.168.30.42:8081/file/docTemplate/dir/3d60d522-0d62-494e-b0f4-dfc5dd6dbdc5.png");
        // 加入参数到 model 中
        model.addAttribute("reports", reports);
        return "printPre";
    }

}
