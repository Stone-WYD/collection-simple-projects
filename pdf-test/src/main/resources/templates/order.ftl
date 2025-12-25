<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
    <title>订单详情（含表格+二维码）</title>
    <style type="text/css">
        /* 全局样式：适配Flying Saucer，使用兼容的CSS 2.1属性 */
        body { font-family: "SimSun", "Microsoft YaHei", serif; margin: 20px; color: #333; }
        .title { text-align: center; font-size: 24px; font-weight: bold; margin-bottom: 30px; }

        /* 基础信息样式 */
        .base-info { font-size: 16px; line-height: 2; margin-bottom: 20px; }
        .info-label { display: inline-block; width: 100px; font-weight: bold; }

        /* 表格样式：边框、对齐、列宽 */
        .detail-table { width: 100%; border-collapse: collapse; margin-bottom: 30px; font-size: 14px; }
        .detail-table th, .detail-table td {
            border: 1px solid #000;
            padding: 8px 10px;
            text-align: center;
        }
        .detail-table th { background-color: #f5f5f5; font-weight: bold; }
        .col-1 { width: 10%; } /* 序号列 */
        .col-2 { width: 30%; } /* 商品名称列 */
        .col-3 { width: 20%; } /* 单价列 */
        .col-4 { width: 15%; } /* 数量列 */
        .col-5 { width: 25%; } /* 小计列 */

        /* 二维码容器：右对齐，避免遮挡内容 */
        .qr-container { text-align: right; margin-top: 20px; }
        .qr-img { width: 120px; height: 120px; }
    </style>
</head>
<body>
<!-- 标题 -->
<div class="title">${title}</div>

<!-- 基础信息 -->
<div class="base-info">
    <div><span class="info-label">订单号：</span>${orderNo}</div>
    <div><span class="info-label">客户姓名：</span>${customerName}</div>
    <div><span class="info-label">下单时间：</span>${createTime}</div>
    <div><span class="info-label">订单总金额：</span>¥${totalAmount}</div>
</div>

<!-- 订单明细表格 -->
<table class="detail-table">
    <thead>
    <tr>
        <th class="col-1">序号</th>
        <th class="col-2">商品名称</th>
        <th class="col-3">单价（元）</th>
        <th class="col-4">数量</th>
        <th class="col-5">小计（元）</th>
    </tr>
    </thead>
    <tbody>
    <#list orderItems as item>
        <tr>
            <td>${item_index + 1}</td>
            <td>${item.productName}</td>
            <td>¥${item.price}</td>
            <td>${item.quantity}</td>
            <td>¥${item.subtotal}</td>
        </tr>
    </#list>
    <!-- 空数据兜底 -->
    <#if orderItems?size == 0>
        <tr>
            <td colspan="5">暂无订单明细</td>
        </tr>
    </#if>
    </tbody>
</table>

<!-- 二维码：Base64格式直接嵌入 -->
<div class="qr-container">
    <div style="font-size: 12px; margin-bottom: 5px;">订单核验码</div>
    <img class="qr-img" src="${qrCodeBase64}" alt="订单二维码" />
</div>
</body>
</html>