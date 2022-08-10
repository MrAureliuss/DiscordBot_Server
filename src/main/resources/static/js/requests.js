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
            if ((data.status === 400) || (data.status === 422)) {
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
            if ((data.status === 400) || (data.status === 422)) {
                toastr["error"](data.responseText);
            } else {
                toastr["error"]("Ошибка! Не удалось удалить канал!");
            }
        }
    })
}

function speechSynthesis() {
    $.post({
        url: 'http://localhost:8000/speech_synthesis',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "display_name": $("#selectServerControl option:selected").val(),
            "speech_text": $("#textArea").val(),
            "user_id": user_id
        }),
        success: function (data) {
            toastr["success"](data["success_message"]);
        },
        error: function (data, error, msg) {
            if ((data.status === 400) || (data.status === 403))  {
                toastr["error"](JSON.parse(data.responseText)['detail']);
            } else {
                toastr["error"]("Ошибка! Не удалось синтезировать текст!");
            }
        }
    })
}

function sendMessage() {
    $.post({
        url: 'http://localhost:8000/send_speech',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "display_name": $("#selectServerControl option:selected").val(),
            "speech_text": $("#textArea").val(),
            "channel_owner_id": user_id
        }),
        success: function (data) {
            toastr["success"](data["success_message"]);
        },
        error: function (data, error, msg) {
            if ((data.status === 400) || (data.status === 403))  {
                toastr["error"](JSON.parse(data.responseText)['detail']);
            } else {
                toastr["error"]("Ошибка! Не удалось отправить текст!");
            }
        }
    })
}

function getChannelBlacklist() {
    $.post({
        url: '/getBlacklistedUsers',
        data: $("#selectServerControl option:selected").val(),
        success: function (data) {
            console.log(data);
            fillTable(data);
        },
        error: function (data, error, msg) {
            console.log(data);
            if ((data.status === 400) || (data.status === 422))  {
                toastr["error"](data.responseText);
            } else {
                toastr["error"]("Ошибка! Не удалось получить список заблокированных пользователей!");
            }
        }
    })
}


function addUserToChannelBlacklist() {
    $.post({
        url: '/addUserToBlacklist',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "display_name": $("#selectServerControl option:selected").val(),
            "user_to_exist": $("#userToBlacklistText").val(),
            "channel_owner_id": user_id
        }),
        success: function (data) {
            toastr["success"](data);
            getChannelBlacklist();
        },
        error: function (data, error, msg) {
            console.log(data.responseText)
            if ((data.status === 400) || (data.status === 403))  {
                toastr["error"](data.responseText);
            } else if (data.status === 422) {
                toastr["error"](data.responseText);
            } else {
                toastr["error"]("Ошибка! Не удалось добавить пользователя в черный список канала!");
            }
        }
    })
}

function deleteUserFromChannelBlacklist(userToDelete) {
    $.post({
        url: '/deleteUserFromBlacklist',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "display_name": $("#selectServerControl option:selected").val(),
            "user_to_delete": userToDelete,
            "channel_owner_id": user_id
        }),
        success: function (data) {
            toastr["success"](data);
            getChannelBlacklist();
        },
        error: function (data, error, msg) {
            console.log(data)
            if ((data.status === 400) || (data.status === 403))  {
                toastr["error"](data.responseText);
            } else if (data.status === 422) {
                toastr["error"](data.responseText);
            } else {
                toastr["error"]("Ошибка! Не удалось удалить пользователя из черного списка канала!");
            }
        }
    })
}
