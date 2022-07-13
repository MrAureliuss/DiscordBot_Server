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
            $("#selectServer").find("option:selected").text(newDisplayName);
            toastr["success"]('Дисплейное название канала успешно изменено на ' + newDisplayName);
        },
        error: function (data, error, msg) {
            if (data.status === 400) {
                toastr["error"](data.responseText);
            } else if (data.status === 422) {
                toastr["error"](data.responseText);
            } else {
                toastr["error"]("Ошибка! Не удалось изменить дисплейное имя!");
            }
        }
    })
}

function deleteChannel(channelID) {
    $.post({
        url: '/channel_delete',
        data: channelID,
        success: function (data) {
            $('#selectServer').find('[value="' + channelID + '"]').remove();
            toastr["success"]('Канал успешно удален из вашего списка каналов!');
        },
        error: function (data, error, msg) {
            if (data.status === 400) {
                console.log(data.responseText);
                toastr["error"](data.responseText);
            } else if (data.status === 422) {
                toastr["error"](data.responseText);
            } else {
                toastr["error"]("Ошибка! Не удалось удалить канал!");
            }
        }
    })
}