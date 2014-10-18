<#--宏-->
<#macro main title="wj" css=[] js=[]> <#--定义宏，带默认参数-->
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="WangJun">
    <title>
    ${title}
    </title>
<#--公用css-->
    <link href="/css/bootstrap3.css" rel="stylesheet">
    <link href="/css/docs.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">
<#--新添加css-->
    <#list css as attr>
        <link href="${attr}" rel="stylesheet">
    </#list>


</head>
<body>
    <#nested >
<#--新添加js-->
    <#list css as attr>
    <link href="${attr}" rel="stylesheet">
    </#list>
</body>
</html>
</#macro>
