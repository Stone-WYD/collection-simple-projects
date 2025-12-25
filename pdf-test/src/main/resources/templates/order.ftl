<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"></meta>
    <title>订单模板</title>
    <style>
        body { font-family: "SimSun", serif; margin: 20px; }
        .title { text-align: center; font-size: 24px; font-weight: bold; margin-bottom: 30px; }
        .info { font-size: 18px; line-height: 2.5; }
        .label { display: inline-block; width: 100px; font-weight: bold; }
    </style>
</head>
<body>
<div class="title">${title}</div>
<div class="info">
    <div><span class="label">订单号：</span>${orderNo}</div>
    <div><span class="label">客户姓名：</span>${customerName}</div>
    <div><span class="label">订单金额：</span>¥${amount}</div>
    <div><span class="label">创建时间：</span>${createTime}</div>
</div>
</body>
</html>