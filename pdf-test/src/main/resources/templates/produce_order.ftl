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
            /*font-family: "SimHei", sans-serif;  公司名称-黑体 */
        }
        .card-title {
            font-size: 18px;
            font-weight: bold;
            margin-top: 2px;
            /* font-family: "SimHei", sans-serif;  卡片标题-黑体 */
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
            /*font-family: "SimHei", sans-serif; 工序表头-黑体 */
        }
    </style>
</head>
<body>
<!-- 二维码区域 -->
<div class="qr-container">
    <img class="qr-img" src="${qrCodeBase64!""}" alt="工艺流程卡二维码" />
    <div class="qr-serial">${qrCodeSerialNo!""}</div>
</div>

<!-- 标题区域 -->
<div class="title-container">
    <div class="company-name">${companyName!""}</div>
    <div class="card-title">工艺流程卡</div>
    <div class="page-no">${pageNo!""}</div>
</div>

<!-- 信息区域表格：调整成品名称/生产数量的colspan和类名 -->
<table class="info-table">
    <tr>
        <td class="info-label">客户名称</td>
        <td class="info-value" >${customerName!""}</td> <!-- 缩减colspan，给成品名称让空间 -->
        <td class="info-label">成品名称</td>
        <td class="info-value product-name-value" >${productName!""}</td> <!-- 增加colspan+专属类名加宽 -->
        <td class="info-label">成品图号</td>
        <td class="info-value" >${productDrawingNo!""}</td>
        <td class="info-label">生产数量</td>
        <td class="info-value production-qty-value">${productionQty!""}</td> <!-- 专属类名缩窄 -->
    </tr>
    <tr>
        <td class="info-label">BOM创建人</td>
        <td class="info-value" >${bomCreator!""}</td>
        <td class="info-label">零件名称</td>
        <td class="info-value" >${partName!""}</td>
        <td class="info-label">零件图号</td>
        <td class="info-value">${partDrawingNo!""}</td>
        <td class="info-label">交货日期</td>
        <td class="info-value">${deliveryDate!""}</td>
    </tr>
    <tr>
        <td class="info-label">料厚</td>
        <td class="info-value">${materialThickness!""}</td>
        <td class="info-label">材质</td>
        <td class="info-value">${material!""}</td>
        <td class="info-label">订单号</td>
        <td class="info-value">${taskNo!""}</td>
        <td class="info-label">计划日期</td>
        <td class="info-value">${deliveryDate!""}</td>
    </tr>
</table>

<!-- 工序表格 -->
<table class="process-table">
    <thead>
    <tr>
        <th>序号</th>
        <th>工序</th>
        <th>工序说明</th>
        <th>首件确认</th>
        <th>良品数量</th>
        <th>不良品数量</th>
        <th>操作者</th>
        <th>加工日期</th>
        <th>终检</th>
        <th>备注信息</th>
    </tr>
    </thead>
    <tbody>
    <#list orderProcesses as process>
        <tr>
            <td>${process.sequence!""}</td>
            <td>${process.processName!""}</td>
            <td>${process.processDesc!""}</td>
            <td>${process.firstPieceConfirm!""}</td>
            <td>${process.goodQty!""}</td>
            <td>${process.badQty!""}</td>
            <td>${process.operator!""}</td>
            <td>${process.processDate!""}</td>
            <td>${process.finalInspection!""}</td>
            <td>${process.remark!""}</td>
        </tr>
    </#list>
    <#if orderProcesses?size == 0>
        <tr>
            <td colspan="11">暂无工序信息</td>
        </tr>
    </#if>
    </tbody>
</table>
</body>
</html>