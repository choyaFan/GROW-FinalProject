var indexPercent = "";
var indexValue = "";
var indexName = "";

var gainerPercent = "";
var gainerValue = "";

var loserValue = "";
var loserPercent = "";

$.ajax({
  url: "http://localhost:8080/getIndex",
  type: "GET",
  dataType: "json",
  async: false,
  success: function (result) {
    var indices = result;
    for (var i = 0; i < 4; i++) {
      indexPercent +=
        '<div class="col-md-3"><a class="btn btn-outline-primary"><span class="text">' +
        indices[i].percent +
        "</span></a></div>";
      indexValue +=
        '<div class="col-md-3"><a class="btn btn-outline-primary"><span class="text">' +
        indices[i].value +
        "</span></a></div>";
      indexName +=
        '<div class="col-md-3"><span class="text" style="font-size: 4px;alignment: center">' +
        indices[i].name +
        "</span></div>";
    }
  },
  error: function () {
    alert("getIndex request failed");
  },
});

$.ajax({
  url: "http://localhost:8080/getGainer",
  type: "GET",
  dataType: "json",
  async: false,
  success: function (result) {
    var gainer = result;
    for (var i = 0; i < gainer.length; i++) {
      gainerPercent +=
        "<tr>" +
        "<td>" +
        gainer[i].name +
        "</td>" +
        '<td style="color: green">' +
        gainer[i].percent +
        "%" +
        "</td>" +
        "</tr>";
      gainerValue +=
        "<tr>" +
        "<td>" +
        gainer[i].name +
        "</td>" +
        '<td style="color: green">' +
        gainer[i].value +
        "</td>" +
        "</tr>";
    }
  },
  error: function () {
    alert("getGainer request failed");
  },
});

$.ajax({
  url: "http://localhost:8080/getLoser",
  type: "GET",
  dataType: "json",
  async: false,
  success: function (result) {
    var loser = result;
    for (var i = 0; i < loser.length; i++) {
      loserPercent +=
        "<tr>" +
        "<td>" +
        loser[i].name +
        "</td>" +
        '<td style="color: red">' +
        loser[i].percent +
        "%" +
        "</td>" +
        "</tr>";
      loserValue +=
        "<tr>" +
        "<td>" +
        loser[i].name +
        "</td>" +
        '<td style="color: red">' 
        +"-" +
        loser[i].value +
        "</td>" +
        "</tr>";
    }
  },
  error: function () {
    alert("getLoser request failed");
  },
});

function changeToValue() {
  getIndexValue();
  getGainerValue();
  getLoserValue();
}

function changeToPercent() {
  getIndexPercent();
  getGainerPercent();
  getLoserPercent();
}

function getIndexPercent() {
  //   var indexPercent = "";
  //   var indexName = "";
  //   $.ajax({
  //     url: "http://localhost:8080/getIndex",
  //     type: "GET",
  //     dataType: "json",
  //     async: false,
  //     success: function (result) {
  //       var indices = result;
  //       for (var i = 0; i < indices.length; i++) {
  //         indexPercent +=
  //           '<div class="col-md-3"><a class="btn btn-outline-primary"><span class="text">' +
  //           indices[i].percent +
  //           "</span></a></div>";
  //         indexName +=
  //           '<div class="col-md-3"><span class="text" style="font-size: 4px;alignment: center">' +
  //           indices[i].name +
  //           "</span></div>";
  //       }
  document.getElementById("indices1").innerHTML = indexPercent;
  document.getElementById("indices2").innerHTML = indexName;
  //$("#gainerTable").append(temp);
  //     },
  //     error: function () {
  //       alert("getIndex request failed");
  //     },
  //   });
}

function getIndexValue() {
  //   var indexValue = "";
  //   var indexName = "";
  //   $.ajax({
  //     url: "http://localhost:8080/getIndex",
  //     type: "GET",
  //     dataType: "json",
  //     async: false,
  //     success: function (result) {
  //       var indices = result;
  //       for (var i = 0; i < indices.length; i++) {
  //         indexValue +=
  //           '<div class="col-md-3"><a class="btn btn-outline-primary"><span class="text">' +
  //           indices[i].value +
  //           "</span></a></div>";
  //         indexName +=
  //           '<div class="col-md-3"><span class="text" style="font-size: 4px;alignment: center">' +
  //           indices[i].name +
  //           "</span></div>";
  //       }
  document.getElementById("indices1").innerHTML = indexValue;
  document.getElementById("indices2").innerHTML = indexName;
  //$("#gainerTable").append(temp);
  //     },
  //     error: function () {
  //       alert("getIndex request failed");
  //     },
  //   });
}

function getGainerPercent() {
  //   var temp = "";
  //   $.ajax({
  //     url: "http://localhost:8080/getGainer",
  //     type: "GET",
  //     dataType: "json",
  //     async: false,
  //     success: function (result) {
  //       var gainerValue = result;
  //       for (var i = 0; i < gainerValue.length; i++) {
  //         temp +=
  //           "<tr>" +
  //           "<td>" +
  //           gainerValue[i].name +
  //           "</td>" +
  //           '<td style="color: green">' +
  //           gainerValue[i].percent +
  //           "%" +
  //           "</td>" +
  //           "</tr>";
  //       }
  //document.getElementById("gainerTable").innerHTML = temp;
  document.getElementById("gainerTable").innerHTML = gainerPercent;
  //$("#gainerTable").append(temp);
  //     },
  //     error: function () {
  //       alert("getGainer request failed");
  //     },
  //   });
}

function getGainerValue() {
  //   var temp = "";
  //   $.ajax({
  //     url: "http://localhost:8080/getGainer",
  //     type: "GET",
  //     dataType: "json",
  //     async: false,
  //     success: function (result) {
  //       var gainerValue = result;
  //       for (var i = 0; i < gainerValue.length; i++) {
  //         temp +=
  //           "<tr>" +
  //           "<td>" +
  //           gainerValue[i].name +
  //           "</td>" +
  //           '<td style="color: green">' +
  //           gainerValue[i].value +
  //           "</td>" +
  //           "</tr>";
  //       }
  // document.getElementById("gainerTable").innerHTML = temp;
  document.getElementById("gainerTable").innerHTML = gainerValue;
  //       //$("#gainerTable").append(temp);
  //     },
  //     error: function () {
  //       alert("getGainer request failed");
  //     },
  //   });
}

function getLoserPercent() {
  //   var temp = "";
  //   $.ajax({
  //     url: "http://localhost:8080/getLoser",
  //     type: "GET",
  //     dataType: "json",
  //     async: false,
  //     success: function (result) {
  //       var loserValue = result;
  //       for (var i = 0; i < loserValue.length; i++) {
  //         temp +=
  //           "<tr>" +
  //           "<td>" +
  //           loserValue[i].name +
  //           "</td>" +
  //           '<td style="color: red">' +
  //           loserValue[i].percent +
  //           "%" +
  //           "</td>" +
  //           "</tr>";
  //       }
  //document.getElementById("loserTable").innerHTML = temp;
  document.getElementById("loserTable").innerHTML = loserPercent;
  //$("#loserTable").append(temp);
  //     },
  //     error: function () {
  //       alert("getLoser request failed");
  //     },
  //   });
}

function getLoserValue() {
  //   var temp = "";
  //   $.ajax({
  //     url: "http://localhost:8080/getLoser",
  //     type: "GET",
  //     dataType: "json",
  //     async: false,
  //     success: function (result) {
  //       var loserValue = result;
  //       for (var i = 0; i < loserValue.length; i++) {
  //         temp +=
  //           "<tr>" +
  //           "<td>" +
  //           loserValue[i].name +
  //           "</td>" +
  //           '<td style="color: red">' +
  //           loserValue[i].value +
  //           "</td>" +
  //           "</tr>";
  //       }
  //document.getElementById("loserTable").innerHTML = temp;
  document.getElementById("loserTable").innerHTML = loserValue;
  //$("#loserTable").append(temp);
  //     },
  //     error: function () {
  //       alert("getLoser request failed");
  //     },
  //   });
}
