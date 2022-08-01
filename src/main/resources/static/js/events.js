$('.form-select').on("click", function (e) {
    if (document.getElementById("selectServer").length === 0) {
        toastr.error("Вы еще не добавили бот ни на один сервер!<br>Используйте зеленую кнопку сверху для добавления бота на свой сервер",
            "",
            {timeOut: 4000, allowHtml: true, progressBar: true}
        );
    }
});

$('#addServer').on("click", function (e) {
    window.open("https://discord.com/api/oauth2/authorize?client_id=333151552472612866&permissions=8&scope=bot", '_blank').focus();

    stopLineBar = true;
    lineBar.stop();
    registerNewChannel();
});

$('#editDisplayName').on("click", function (e) {
    alertify.prompt("Введите новое название.", $("#selectServer option:selected").text(),
        function(evt, value) {
            changeDisplayName($("#selectOptions").val(), value)
        },
        function(){}
    ).set({title:"Изменение дисплейного имени канала"}).set({labels:{ok:'Изменить', cancel: 'Отменить'}});
});

$('#deleteServer').on("click", function (e) {
    alertify.confirm('Подтверждение удаления канала', 'Вы точно хотите удалить этот канал из списка ваших каналов?',
        function() {
            deleteChannel($("#selectOptions").val());
        },
        function() {}
    ).set({labels:{ok:'Удалить', cancel: 'Отмена'}});
});

$("#speechSynthesis").on("click", function () {
    speechSynthesis();
});

$("#sendMessage").on("click", function () {
    sendMessage();
});