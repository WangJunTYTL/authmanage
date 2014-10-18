function mysubmit() {
    $('#myModal').modal('toggle');
}


$("#mysubmit").bind("click", function () {
    if (!checkRequiredInput()) {
        return;
    }
    var url = $("form").attr("action");
    var method = $("form").attr("method");
    if (method == null || method == "") {
        method = "GET";
    }
    $.ajax({
        type: method,
        url: url,
        data: $("form").serialize(),
        success: function (msg) {
            var response = eval(msg);
            var modalTitle = $("#detail");
            var modalLog = $("#log");
            modalLog.html("");
            modalTitle.html("");
            var title = response.detailDesc
            modalTitle.html(title);
            mysubmit();
        }

    });

});

$("#normalSubmit").bind("click", function () {
    if (!checkRequiredInput()) {
        return;
    }
    $("form").submit();

});


function checkRequiredInput() {
    var flag = true;
    $("input[required='true']").each(function () {
        if ($(this).val() == "") {
            $(this).attr("placeholder", $(this).attr("message"));
            $(this).focus();
            flag = false;
            return false;
        }
    });
    return flag;
}

