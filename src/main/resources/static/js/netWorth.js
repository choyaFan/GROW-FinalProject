function getTotalCash() {
    var value;
    $.ajax({
        url: "http://localhost:8080/getCashTotalValue",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (result) {
            value = result;
            // document.getElementById("totalCash").innerText = "$" + value;
            $("#totalCash").innerText = "$" + value;
        },
        error: function () {
            alert("getCashTotalValue request failed")
        }
    });
}



// function getAllCash() {
//     var temp = '';
//     $.ajax({
//         url: "http://localhost:8080/getAllCash",
//         type: "GET",
//         dataType: "json",
//         async: false,
//         success: function (result) {
//             var cash = result;
//             for (var $i = 0; $i < cash.length; $i++) {
//                 temp +=
//                     '<ul class="table-row">' +
//                     '<li class="table-cell"><span class="left">' + cash[$i].name + '</span></li>' +
//                     '<li class="table-cell"><span class="right">' + cash[$i].value.toFixed(2) + "</span></li>" +
//                     '</ul>' +
//                     '<ul class="table-row">' +
//                     '<li class="table-cell1"><span class="left">' + cash[$i].type + '</span></li>' +
//                     '<li class="table-cell1"><span class="right">' + cash[$i].created.substring(0, 10) + '</span></li>' +
//                     '</ul>'
//             }
//             $('#cashTable').append(temp);
//         },
//         error: function () {
//             alert("getAllCash request failed")
//         }
//     });
// }