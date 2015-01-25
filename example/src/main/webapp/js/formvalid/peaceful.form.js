/**
 *
 * 校验必填表单:  调用方式: $("form").validForm()
 * @returns {boolean}
 */
$.fn.validForm = function () {

    var inputs = $(this).find("input");
    for (var i = 0; i < inputs.length; i++) {
        if ($(inputs[i]).attr("required") == "required" && $(inputs[i]).attr("type") != "checkbox") {
            if ($(inputs[i]).val() == null || $(inputs[i]).val() == "") {
                $(inputs[i]).focus();
                $(inputs[i]).attr("placeholder", "必填");
                return false;
            }
        }
    }

    var textareas = $(this).find("textarea");
    for (var i = 0; i < textareas.length; i++) {
        if ($(textareas[i]).attr("required") == "required") {
            if ($(textareas[i]).val() == null || $(textareas[i]).val() == "") {
                $(textareas[i]).focus();
                return false;
            }
        }
    }

    var checkboxs = $(this).find("input:checkbox");
    for (var i = 0; i < checkboxs.length; i++) {
        if ($(checkboxs[i]).attr("required") == "required") {
            var cName = $(checkboxs[i]).attr("name");
            if ($("input:checkbox:checked[name='" + cName + "']").length == 0) {
                alert("还有必选复选框未选择");
                $(checkboxs[i]).focus();
                return false;
            }
        }
    }
    return true;
}

if (undefined != window.app_data && window.app_data != null) {
    app_form = null;
    highlight_function = null;
    if (app_data.form != null) {
        var filter = "form[id='" + app_data.form + "']"
        app_form = $(filter);
    } else {
        app_form = $('form');
    }
    if (app_data.highlight != null) {
        highlight_function = app_data.highlight;
    } else {
        highlight_function = function (element) {
            $(element).closest('.form-group').addClass('has-error');
        };
    }
    if (app_data.errorPlacement != null) {
        errorPlacement_function = app_data.errorPlacement;
    } else {
        errorPlacement_function = function (error, element) {
            element.parent('div').append(error);
        };
    }
    var MyValidator = function () {
        var handleSubmit = function () {
            app_form.validate({
                errorElement: 'span',
                errorClass: 'help-block',
                focusInvalid: false,
                rules: app_data.rules,
                messages: app_data.messages,
                highlight: highlight_function,

                success: function (label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: errorPlacement_function,

                submitHandler: function () {
                    var flag = true;
                    if (app_data.postFront != null || app_data.postFront != undefined) {
                        flag = app_data.postFront();
                    }
                    if (flag) {
                        var data = app_form.serialize();
                        $.ajax({
                            type: "POST",
                            url: app_data.url,
                            data: data,
                            dataType: "json",
                            success: function (data) {
                                if (data.code == 1) {
                                    alert(data.result);
                                    document.location.href = app_data.nextUrl;
                                } else if (data.code == 2) {
                                    document.location.href = app_data.nextUrl;
                                }
                                else {
                                    alert(data.result);
                                }
                            }

                        });
                    }
                }
            });


            $('form input').keypress(function (e) {
                if (e.which == 13) {
                    if (app_form.validate().form()) {
                        return true;
                    }
                    return false;
                }
            });
        }
        return {
            init: function () {
                handleSubmit();
            }
        };

    }
    ();
    MyValidator.init();

}

