function getChannelToken() {
    $.get({
        url: '/generate_token',
        success: function (data) {
            lineBar.setText(data);
        }
    })
}

function registerNewChannel() {
    console.log($(".progressbar__label").text())
    $.post({
        url: '/create_channel',
        data: $(".progressbar__label").text(),
        success: function (data) {
            console.log("ok")
        },
        error: function (data, error, msg) {
            console.log("bad")
            // if (!data.responseJSON.validated) {
            //     toastr["error"](data.responseJSON.message.replaceAll("\n", "<br>"));
            // }
        }
    })
}