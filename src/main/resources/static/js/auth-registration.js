$(function () {
    $('#submitButtonReg').on("click", function (e) {
        e.preventDefault();

        $.post({
            url: '/sign_up',
            data: $('#registrationForm').serialize(),
            success: function (data) {
                if (data.validated) {
                    toastr.options.onHidden = function() { window.location.href = '/' };
                    toastr["success"](data.message);
                }
            },
            error: function (data, error, msg) {
                if (!data.responseJSON.validated) {
                    toastr["error"](data.responseJSON.message.replaceAll("\n", "<br>"));
                }
            }
        })
    });
});

$(function () {
    $('#submitButtonAuth').on("click", function (e) {
        e.preventDefault();

        $.post({
            url: '/sign_in',
            data: $('#authForm').serialize(),
            success: function (data) {
                toastr.options.onHidden = function() { window.location.href = '/' };
                toastr["success"]("Вы успешно авторизовались!");
            },
            error: function (data, error, msg) {
                if (data.status === 401) {
                    toastr["error"](data.responseJSON.message);
                }
            }
        })
    });
});