function fillTable(items) {
    const table = document.getElementById("table").getElementsByTagName("tbody")[0];
    $("#table tr>td").remove();
    items.forEach((item, index) => {
        let row = table.insertRow();
        // Добавление индекса
        let tableIndex = row.insertCell(0);
        tableIndex.innerHTML = index + 1;
        // Добавление юзернейма
        let username = row.insertCell(1);
        username.innerHTML = item.userName;
        // Добавление времени
        let date = row.insertCell(2);
        date.innerHTML = item.dateTime.replace("T", " ");
        // Добавление кнопки удаления
        let button = document.createElement('button');
        button.type = 'button';
        button.innerHTML = 'Удалить';
        button.className = 'btn btn-danger';
        button.onclick = function () {
            deleteUserFromChannelBlacklist(row.cells[1].innerHTML);
        }
        let deleteButton = row.insertCell(3);
        deleteButton.appendChild(button);
    });
}