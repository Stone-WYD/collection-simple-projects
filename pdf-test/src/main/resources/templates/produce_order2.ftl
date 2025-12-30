<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
    <title>工艺流程卡</title>
    <style type="text/css">
        /* 全局样式：表格内容用宋体 + 基础布局 */
        body {
            font-family: "SimSun", serif; /* 表格内容统一用宋体 */
            font-size: 12px;
            margin: 10px;
            position: relative;
            padding-top: 120px;
        }
        /* 标题样式：改用黑体（匹配原图标题字体） */
        .title-container {
            text-align: center;
            margin-bottom: 10px;
            position: absolute;
            top: 10px;
            left: 0;
            right: 0;
        }
        .company-name {
            font-size: 16px;
            font-weight: bold;
        }
        .card-title {
            font-size: 18px;
            font-weight: bold;
            margin-top: 2px;
        }
        .page-no {
            text-align: right;
            margin-right: 20px;
            margin-top: 5px;
            font-size: 12px;
            font-family: "SimSun", serif; /* 页码保持宋体 */
        }

        /* 二维码区域样式 */
        .qr-container {
            position: absolute;
            top: 10px;
            right: 20px;
            text-align: center;
            z-index: 10;
        }
        .qr-img {
            width: 80px;
            height: 80px;
            border: 1px solid #eee;
        }
        .qr-serial {
            font-size: 10px;
            margin-top: 2px;
            color: #333;
            font-family: "SimSun", serif; /* 二维码序列号-宋体 */
        }

        /* 信息区域表格：核心调整列宽 */
        .info-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 10px;
            margin-top: 20px;
        }
        .info-table td {
            border: 1px solid #000;
            padding: 3px 5px;
            height: 22px;
        }
        .info-label {
            font-weight: bold;
            text-align: center;
            width: 80px; /* 标签列固定宽度 */
        }
        .info-value {
            text-align: left;
        }
        /* 新增：成品名称列加宽 */
        .product-name-value {
            width: 200px; /* 成品名称值列固定宽200px */
        }
        /* 新增：生产数量列缩窄 */
        .production-qty-value {
            width: 60px; /* 生产数量值列固定宽60px */
            text-align: center; /* 数量居中更美观 */
        }

        /* 工序表格样式 */
        .process-table {
            width: 100%;
            border-collapse: collapse;
            font-family: "SimSun", serif; /* 工序表格-宋体 */
        }
        .process-table th, .process-table td {
            border: 1px solid #000;
            padding: 3px 5px;
            text-align: center;
            height: 22px;
        }
        .process-table th {
            font-weight: bold;
        }
    </style>
</head>
<body>
<!-- 二维码区域：无对应属性，保留占位符并兜底空值 -->
<div class="qr-container">
    <img class="qr-img" src="${qrCodeBase64!""}" alt="工艺流程卡二维码" />
</div>

<!-- 标题区域：公司名/页码无对应属性，保留占位符 -->
<div class="title-container">
    <div class="company-name">${companyName!""}</div>
    <div class="card-title">工艺流程卡</div>
    <div class="page-no">${pageNo!""}</div>
</div>

<!-- 信息区域表格：核心字段映射 + 日期格式化 -->
<table class="info-table">
    <tr>
        <td class="info-label">客户名称</td>
        <td class="info-value" >${customerName!""}</td> <!-- 映射：客户名 -->
        <td class="info-label">成品名称</td>
        <td class="info-value product-name-value" >${productName!""}</td> <!-- 映射：产成品名 -->
        <td class="info-label">成品图号</td>
        <td class="info-value" >${productDrawingNo!""}</td> <!-- 映射：产成品图号 -->
        <td class="info-label">生产数量</td>
        <td class="info-value production-qty-value">${needNum!""}</td> <!-- 映射：生产件数（needNum） -->
    </tr>
    <tr>
        <td class="info-label">BOM创建人</td>
        <td class="info-value" >${""}</td> <!-- 无对应属性，直接留空 -->
        <td class="info-label">零件名称</td>
        <td class="info-value" >${currentName!""}</td> <!-- 映射：当前工单零部件名 -->
        <td class="info-label">零件图号</td>
        <td class="info-value">${currentDrawingNo!""}</td> <!-- 映射：当前工单零部件图号 -->
        <td class="info-label">交货日期</td>
        <td class="info-value">${orderDeliveryDate!""}</td> <!-- 映射：交期 + 日期格式化 -->
    </tr>
    <tr>
        <td class="info-label">料厚</td>
        <td class="info-value">${thickness!""}</td> <!-- 映射：料厚 -->
        <td class="info-label">材质</td>
        <td class="info-value">${material!""}</td> <!-- 映射：材质 -->
        <td class="info-label">订单号</td>
        <td class="info-value">${workNo!""}</td> <!-- 映射：工单号（优先），也可替换为customerOrderNo（客户订单号） -->
        <td class="info-label">计划日期</td>
        <td class="info-value">${scheduleEndDate!""}</td> <!-- 映射：计划结束时间 + 格式化 -->
    </tr>
</table>

<!-- 工序表格：映射produceProcessList + 首件确认值转换 + 日期格式化 -->
<table class="process-table">
    <thead>
    <tr>
        <th>工序</th>
        <th>工序说明</th>
        <th>首件确认</th>
        <th>良品数量</th>
        <th>不良品数量</th>
        <th>操作者</th>
        <th>截至日期</th>
        <th>备注信息</th>
    </tr>
    </thead>
    <tbody>
    <!-- 遍历工单的工序列表：produceProcessList（替换原orderProcesses） -->
    <#list produceProcessList as process>
        <tr>
            <td>${process.processName!""}</td> <!-- 映射：工序名 -->
            <td>${process.processDesc!""}</td> <!-- 工序说明：无对应属性，留空 -->
            <!-- 首件确认：1=是，0=否，兜底空值 -->
            <td><#if process.firstCheck?? && process.firstCheck == 1>
                    是
                <#elseif process.firstCheck?? && process.firstCheck == 0>
                    否
                <#else>
                    ${""}
                </#if></td>
            <td>${""}</td> <!-- 映射：合格数量 -->
            <td>${""}</td> <!-- 映射：不合格数量 -->
            <td>${""}</td> <!-- 操作者：无对应属性，留空 -->
            <!-- 加工日期：工序计划结束时间 + 格式化 -->
            <td>${process.exceptEndDate!""}</td>
            <td>${""}</td> <!-- 备注：无对应属性，留空 -->
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>