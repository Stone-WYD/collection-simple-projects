<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>工艺流程卡</title>
    <style type="text/css">
        /* 全局：防溢出+高清渲染 */
        * {
            text-rendering: optimizeLegibility;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
            image-rendering: crisp-edges;
            box-sizing: border-box;
        }

        /* 核心：body自适应宽度，取消溢出裁切 */
        body {
            font-family: "SimSun", serif;
            font-size: 0.75rem; /* 12px 高清 */
            margin: 10px;
            position: relative;
            padding-top: 120px;
            /* 关键：让body宽度自适应内容，且不溢出 */
            width: fit-content; /* 宽度适配内容，避免裁切 */
            min-width: 100%; /* 兜底：至少占满渲染器宽度 */
            height: 100%;
            line-height: 1.2;
            overflow-x: visible; /* 取消横向溢出隐藏 */
        }

        /* 标题区域：位置精准，不超宽 */
        .title-container {
            text-align: center;
            margin-bottom: 10px;
            position: absolute;
            top: 10px;
            left: 0;
            right: 0;
            z-index: 1;
            width: 100%; /* 标题宽度适配body，不超边 */
        }
        .company-name {
            font-size: 1rem;
            font-weight: bold;
        }
        .card-title {
            font-size: 1.125rem;
            font-weight: bold;
            margin-top: 2px;
        }
        .page-no {
            text-align: right;
            margin-right: 20px;
            margin-top: 5px;
            font-size: 0.75rem;
        }

        /* 二维码区域：限制最大宽度，避免超边 */
        .qr-container {
            position: absolute;
            top: 10px;
            right: 10px; /* 右间距从20px减到10px，减少超边风险 */
            z-index: 2;
            max-width: 100px; /* 限制二维码容器宽度，不超边 */
        }
        .qr-img {
            width: 100px;
            height: 100px;
            display: block;
        }
        .qr-serial {
            font-size: 0.625rem;
            margin-top: 2px;
            color: #333;
            text-align: center; /* 序列号居中，避免超宽 */
        }

        /* 信息表格：取消fixed布局，列宽自适应+最大宽度，避免超边 */
        .info-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 10px;
            margin-top: 20px;
            /* 关键：移除table-layout: fixed，避免强制固定列宽导致溢出 */
            table-layout: auto;
        }
        .info-table td {
            border: 1px solid #000;
            padding: 3px 5px;
            height: 22px;
            white-space: normal; /* 允许文字换行，避免列宽撑爆 */
            word-break: break-all; /* 超长文字换行，不超列宽 */
        }
        .info-label {
            font-weight: bold;
            text-align: center;
            width: 80px;
            min-width: 80px; /* 最小宽度，不挤压 */
            max-width: 100px; /* 最大宽度，不超边 */
        }
        .product-name-value {
            min-width: 200px;
            max-width: 250px; /* 限制最大宽度，避免超边 */
        }
        .production-qty-value {
            width: 60px;
            text-align: center;
        }
        .date-value {
            width: 120px;
            text-align: center;
        }

        /* 工序表格：自适应布局，列宽均分且换行 */
        .process-table {
            width: 100%;
            border-collapse: collapse;
            font-family: "SimSun", serif;
            table-layout: auto; /* 取消fixed布局 */
        }
        .process-table th, .process-table td {
            border: 1px solid #000;
            padding: 3px 5px;
            text-align: center;
            height: 22px;
            white-space: normal; /* 允许换行 */
            word-break: break-all;
        }
        .process-table th {
            font-weight: bold;
            /* 列宽均分，不强制固定 */
            width: 12.5%;
        }
    </style>
</head>
<body>
<div class="qr-container">
    <img class="qr-img" src="${qrCodeBase64!""}" alt="工艺流程卡二维码" />
</div>

<div class="title-container">
    <div class="company-name">${companyName!""}</div>
    <div class="card-title">工艺流程卡</div>
    <div class="page-no">${pageNo!""}</div>
</div>

<table class="info-table">
    <tr>
        <td class="info-label">客户名称</td>
        <td class="info-value">${customerName!""}</td>
        <td class="info-label">成品名称</td>
        <td class="info-value product-name-value">${productName!""}</td>
        <td class="info-label">成品图号</td>
        <td class="info-value">${productDrawingNo!""}</td>
        <td class="info-label">生产数量</td>
        <td class="info-value production-qty-value">${needNum!""}</td>
    </tr>
    <tr>
        <td class="info-label">BOM创建人</td>
        <td class="info-value">${""}</td>
        <td class="info-label">零件名称</td>
        <td class="info-value">${currentName!""}</td>
        <td class="info-label">零件图号</td>
        <td class="info-value">${currentDrawingNo!""}</td>
        <td class="info-label">交货日期</td>
        <td class="info-value date-value">${orderDeliveryDate!""}</td>
    </tr>
    <tr>
        <td class="info-label">料厚</td>
        <td class="info-value">${thickness!""}</td>
        <td class="info-label">材质</td>
        <td class="info-value">${material!""}</td>
        <td class="info-label">订单号</td>
        <td class="info-value">${workNo!""}</td>
        <td class="info-label">计划日期</td>
        <td class="info-value date-value">${scheduleEndDate!""}</td>
    </tr>
</table>

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
    <#list produceProcessList as process>
        <tr>
            <td>${process.processName!""}</td>
            <td>${process.processDesc!""}</td>
            <td>
                <#if process.firstCheck?? && process.firstCheck == 1>是
                <#elseif process.firstCheck?? && process.firstCheck == 0>否
                <#else>${""}</#if>
            </td>
            <td>${""}</td>
            <td>${""}</td>
            <td>${""}</td>
            <td>${process.exceptEndDate!""}</td>
            <td>${""}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>