function getHolding() {
	$.ajax({
        url: "http://localhost:8080/getHolding",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (result) {
            //var aaaa = result;
            document.getElementById("holding").innerText = "Your Holdings: " + result + "%";
        },
        error: function () {
            alert("getNetWorth_preWeek request failed")
        }
    });
}