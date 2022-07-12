$('#selectServer').on("click", function (e) {
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