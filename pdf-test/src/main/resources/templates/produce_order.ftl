<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
    <title>工艺流程卡</title>
    <style type="text/css">
        /* 全局样式：匹配原图字体+布局 */
        body {
            font-family: "SimSun", serif;
            font-size: 12px;
            margin: 10px;
            position: relative; /* 为二维码绝对定位提供参考 */
            /* 核心调整：增大顶部内边距，预留足够二维码空间（从100px增至150px） */
            padding-top: 150px;
        }
        /* 标题样式 */
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
        }

        /* 二维码区域：固定右上角，脱离文档流 */
        .qr-container {
            position: absolute;
            top: 10px;    /* 距离顶部10px */
            right: 20px;  /* 距离右侧20px */
            text-align: center;
            z-index: 10;  /* 确保在最上层，不被遮挡 */
        }
        .qr-img {
            width: 80px;
            height: 80px;
            border: 1px solid #eee; /* 可选：加边框更清晰 */
        }
        .qr-serial {
            font-size: 10px;
            margin-top: 2px;
            color: #333;
        }

        /* 信息区域表格：匹配原图的字段布局 + 顶部外边距兜底 */
        .info-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 10px;
            /* 额外增加表格顶部外边距，确保彻底避开二维码 */
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
            width: 80px;
        }
        .info-value {
            text-align: left;
        }

        /* 工序表格：匹配原图的列布局 */
        .process-table {
            width: 100%;
            border-collapse: collapse;
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
<!-- 二维码区域：右上角固定定位 -->
<div class="qr-container">
    <img class="qr-img" src="${qrCodeBase64!""}" alt="工艺流程卡二维码" />
    <div class="qr-serial">${qrCodeSerialNo!""}</div>
</div>

<!-- 标题区域 -->
<div class="title-container">
    <div class="company-name">${companyName!""}</div>
    <div class="card-title">工艺流程卡</div>
    <div class="page-no">${pageNo!""}</div> <!-- 页码移到标题右侧 -->
</div>

<!-- 信息区域表格（匹配原图的字段） -->
<table class="info-table">
    <tr>
        <td class="info-label">客户名称</td>
        <td class="info-value" colspan="2">${customerName!""}</td>
        <td class="info-label">成品名称</td>
        <td class="info-value" colspan="2">${productName!""}</td>
        <td class="info-label">成品图号</td>
        <td class="info-value" colspan="2">${productDrawingNo!""}</td>
        <td class="info-label">生产数量</td>
        <td class="info-value">${productionQty!""}</td>
    </tr>
    <tr>
        <td class="info-label">零件图号</td>
        <td class="info-value" colspan="4">${partDrawingNo!""}</td>
        <td class="info-label">BOM创建人</td>
        <td class="info-value" colspan="2">${bomCreator!""}</td>
        <td class="info-label">交货日期</td>
        <td class="info-value" colspan="2">${deliveryDate!""}</td>
    </tr>
    <tr>
        <td class="info-label">零件名称</td>
        <td class="info-value" colspan="4">${partName!""}</td>
        <td class="info-label">料厚</td>
        <td class="info-value">${materialThickness!""}</td>
        <td class="info-label">材质</td>
        <td class="info-value">${material!""}</td>
        <td class="info-label">任务单号</td>
        <td class="info-value">${taskNo!""}</td>
    </tr>
</table>

<!-- 工序表格（循环渲染工序列表） -->
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
        <th colspan="2">备注</th>
    </tr>
    </thead>
    <tbody>
    <!-- 循环渲染工序数据 -->
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
            <td colspan="2">${process.remark!""}</td>
        </tr>
    </#list>

    <!-- 空数据兜底 -->
    <#if orderProcesses?size == 0>
        <tr>
            <td colspan="11">暂无工序信息</td>
        </tr>
    </#if>
    </tbody>
</table>
</body>
</html>