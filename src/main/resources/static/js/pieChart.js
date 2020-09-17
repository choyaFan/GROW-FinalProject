Date.prototype.Format = function (fmt) {
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    S: this.getMilliseconds(), //毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(
      RegExp.$1,
      (this.getFullYear() + "").substr(4 - RegExp.$1.length)
    );
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)
      );
  return fmt;
};

//var end = new Date(new Date().getTime() + 1 * 24 * 60 * 60 * 1000).Format(
  //"MM/dd/yyyy"
//); //just for test
var end = new Date().Format("MM/dd/yyyy"); //today
var timeW = new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000).Format(
  "MM/dd/yyyy"
); //7 days ago
var timeM = new Date(new Date().getTime() - 30 * 24 * 60 * 60 * 1000).Format(
  "MM/dd/yyyy"
); //30 days ago
var timeQ = new Date(new Date().getTime() - 90 * 24 * 60 * 60 * 1000).Format(
  "MM/dd/yyyy"
); //90 days ago
var timeY = new Date(new Date().getTime() - 365 * 24 * 60 * 60 * 1000).Format(
  "MM/dd/yyyy"
); //365 days ago

var param;

function getIncomePiePositive(n) {
  var incomeData = [];
  var incomeName = [];
  if (n == 0) {
    param = "http://localhost:8080/getIncome?start=" + timeW + "&end=" + end;
  } else if (n == 1) {
    param = "http://localhost:8080/getIncome?start=" + timeM + "&end=" + end;
  } else if (n == 2) {
    param = "http://localhost:8080/getIncome?start=" + timeQ + "&end=" + end;
  } else if (n == 3) {
    param = "http://localhost:8080/getIncome?start=" + timeY + "&end=" + end;
  }

  $.ajax({
    url: param,
    type: "GET",
    dataType: "json",
    async: false,
    success: function (result) {
      var list = result.positiveList;
      var totalIncome = result.totalValue;
      for (var i = 0; i < list.length; i++) {
        var map = {};
        map["name"] = list[i].name;
        map["value"] = list[i].value;
        incomeData.push(map);
        incomeName[i] = list[i].name;
      }
      document.getElementById("totalIncome").innerText =
        "Total Income: " + totalIncome.toFixed(2);
      //$("#totalIncome").append(totalIncome);
    },
    error: function () {
      alert("getIncome request failed");
    },
  });

  var dom = document.getElementById("incomePiePositive");
  var myChart = echarts.init(dom, "light");

  var app = {};
  option = null;
  option = {
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}: {c} ({d}%)",
    },
    legend: {
      orient: "vertical",
      left: 10,
      data: incomeName,
    },
    series: [
      {
        name: "performance",
        type: "pie",
        radius: ["50%", "70%"],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: "center",
        },
        emphasis: {
          label: {
            show: true,
            fontSize: "30",
            fontWeight: "bold",
          },
        },
        labelLine: {
          show: false,
        },
        data: incomeData,
      },
    ],
  };
  if (option && typeof option === "object") {
    myChart.setOption(option, true);
  }
}

function getIncomePieNegetive(n) {
  var incomeData = [];
  var incomeName = [];
  if (n == 0) {
    param = "http://localhost:8080/getIncome?start=" + timeW + "&end=" + end;
  } else if (n == 1) {
    param = "http://localhost:8080/getIncome?start=" + timeM + "&end=" + end;
  } else if (n == 2) {
    param = "http://localhost:8080/getIncome?start=" + timeQ + "&end=" + end;
  } else if (n == 3) {
    param = "http://localhost:8080/getIncome?start=" + timeY + "&end=" + end;
  }
  $.ajax({
    url: param,
    type: "GET",
    dataType: "json",
    async: false,
    success: function (result) {
      var list = result.negativeList;
      for (var i = 0; i < list.length; i++) {
        var map = {};
        map["name"] = list[i].name;
        map["value"] = list[i].value;
        incomeData.push(map);
        incomeName[i] = list[i].name;
      }
    },
    error: function () {
      alert("getIncome request failed");
    },
  });

  var dom = document.getElementById("incomePieNegative");
  var myChart = echarts.init(dom, "light");

  var app = {};
  option = null;
  option = {
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}: {c} ({d}%)",
    },
    legend: {
      orient: "vertical",
      left: 10,
      data: incomeName,
    },
    series: [
      {
        name: "performance",
        type: "pie",
        radius: ["50%", "70%"],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: "center",
        },
        emphasis: {
          label: {
            show: true,
            fontSize: "30",
            fontWeight: "bold",
          },
        },
        labelLine: {
          show: false,
        },
        data: incomeData,
      },
    ],
  };
  if (option && typeof option === "object") {
    myChart.setOption(option, true);
  }
}

function getSpendingPie(n) {
  var spendingData = [];
  var spendingName = [];
  if (n == 0) {
    param = "http://localhost:8080/getSpending?start=" + timeW + "&end=" + end;
  } else if (n == 1) {
    param = "http://localhost:8080/getSpending?start=" + timeM + "&end=" + end;
  } else if (n == 2) {
    param = "http://localhost:8080/getSpending?start=" + timeQ + "&end=" + end;
  } else if (n == 3) {
    param = "http://localhost:8080/getSpending?start=" + timeY + "&end=" + end;
  }
  $.ajax({
    url: param,
    type: "GET",
    dataType: "json",
    async: false,
    success: function (result) {
      var list = result.positiveList;
      var totalSpending = result.totalValue;
      for (var i = 0; i < list.length; i++) {
        var map = {};
        map["name"] = list[i].name;
        map["value"] = list[i].value;
        spendingData.push(map);
        spendingName[i] = list[i].name;
      }
      document.getElementById("totalSpending").innerText = "Total Spending: " + totalSpending;
      //$("#totalSpending").append(totalSpending);
    },
    error: function () {
      alert("getSpending request failed");
    },
  });

  var dom = document.getElementById("spendingPie");
  var myChart = echarts.init(dom, "light");
  var app = {};
  option = null;
  option = {
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}: {c} ({d}%)",
    },
    legend: {
      orient: "vertical",
      left: 10,
      data: spendingName,
    },
    series: [
      {
        name: "spending",
        type: "pie",
        radius: ["50%", "70%"],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: "center",
        },
        emphasis: {
          label: {
            show: true,
            fontSize: "30",
            fontWeight: "bold",
          },
        },
        labelLine: {
          show: false,
        },
        data: spendingData,
      },
    ],
  };
  if (option && typeof option === "object") {
    myChart.setOption(option, true);
  }
}
