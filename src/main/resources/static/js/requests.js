function getChannelToken() {
    $.get({
        url: '/generate_token',
        success: function (data) {
            lineBar.setText(data);
        }
    })
}

function registerNewChannel() {
    $.post({
        url: '/create_channel',
        data: $(".progressbar__label").text(),
        success: function (data) {
            console.log("ok")
        },
        error: function (data, error, msg) {
            console.log("bad")
        }
    })
}

function changeDisplayName(channelID, newDisplayName) {
    $.post({
        url: '/change_displayName',
        data: {channelID: channelID, newDisplayName: newDisplayName},
        success: function (data) {
            toastr["success"]('Дисплейное название канала успешно изменено на ' + newDisplayName);
        },
        error: function (data, error, msg) {
            if (data.status === 400) {
                toastr["error"](data.responseText);
            } else if (data.status === 409) {
                toastr["error"](data.responseText);
            } else {
                toastr["error"]("Ошибка! Не удалось изменить дисплейное имя!");
            }
        }
    })
}