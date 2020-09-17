var date = new Array();
var netWorthValue = new Array();
var i = 0;
var param;

function getValuePerWeek() {
  if (i == 0) {
    param = "http://localhost:8080/getNetWorth_preWeek";
  } else if (i == 1) {
    param = "http://localhost:8080/getCash_preWeek";
  } else {
    param = "http://localhost:8080/getInvestment_preWeek";
  }
  $.ajax({
    url: param,
    type: "GET",
    dataType: "json",
    async: false,
    success: function (result) {
      var cash = result;
      for (var $i = 0; $i < cash.length; $i++) {
        date[$i] = cash[$i].created;
        netWorthValue[$i] = cash[$i].value;
        drawLineChart();
      }
    },
    error: function () {
      alert("getNetWorth_preWeek request failed");
    },
  });
}

function getValuePerMonth() {
    if (i == 0) {
        param = "http://localhost:8080/getNetWorth_preMonth";
      } else if (i == 1) {
        param = "http://localhost:8080/getCash_preMonth";
      } else {
        param = "http://localhost:8080/getInvestment_preMonth";
      }
  $.ajax({
    url: param,
    type: "GET",
    dataType: "json",
    async: false,
    success: function (result) {
      var cash = result;
      for (var $i = 0; $i < cash.length; $i++) {
        date[$i] = cash[$i].created;
        netWorthValue[$i] = cash[$i].value;
        drawLineChart();
      }
    },
    error: function () {
      alert("getNetWorth_preMonth request failed");
    },
  });
}

function getValuePerQuarter() {
    if (i == 0) {
        param = "http://localhost:8080/getNetWorth_preQuarter";
      } else if (i == 1) {
        param = "http://localhost:8080/getCash_preQuarter";
      } else {
        param = "http://localhost:8080/getInvestment_preQuarter";
      }
  $.ajax({
    url: param,
    type: "GET",
    dataType: "json",
    async: false,
    success: function (result) {
      var cash = result;
      for (var $i = 0; $i < cash.length; $i++) {
        date[$i] = cash[$i].created;
        netWorthValue[$i] = cash[$i].value;
        drawLineChart();
      }
    },
    error: function () {
      alert("getNetWorth_preQuarter request failed");
    },
  });
}

function getValuePerYear() {
    if (i == 0) {
        param = "http://localhost:8080/getNetWorth_preYear";
      } else if (i == 1) {
        param = "http://localhost:8080/getCash_preYear";
      } else {
        param = "http://localhost:8080/getInvestment_preYear";
      }
  $.ajax({
    url: param,
    type: "GET",
    dataType: "json",
    async: false,
    success: function (result) {
      var cash = result;
      for (var $i = 0; $i < cash.length; $i++) {
        date[$i] = cash[$i].created;
        netWorthValue[$i] = cash[$i].value;
        drawLineChart();
      }
    },
    error: function () {
      alert("getNetWorth_preYear request failed");
    },
  });
}

function changeToNetWorth() {
  document.getElementById("title").innerText = "Net Worth";
  getValuePerWeek("http://localhost:8080/getNetWorth_preWeek");
  i = 0;
}

function changeToCash() {
  document.getElementById("title").innerText = "Cash";
  getValuePerWeek("http://localhost:8080/getCash_preWeek");
  i = 1;
}

function changeToInvestment() {
  document.getElementById("title").innerText = "Investment";
  getValuePerWeek("http://localhost:8080/getInvestment_preWeek");
  i = 2;
}

function drawLineChart() {
  var dom = document.getElementById("lineChart");
  var myChart = echarts.init(dom, "light");
  var app = {};
  option = null;
  option = {
    xAxis: {
      type: "category",
      data: date,
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: netWorthValue,
        type: "line",
        smooth: true,
      },
    ],
  };
  if (option && typeof option === "object") {
    myChart.setOption(option, true);
  }
}
