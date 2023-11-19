var clicksChart;

$(document).ready(function() {
  updateDayOptions();

  var clicksCtx = document.getElementById('clicksChart').getContext('2d');
  clicksChart = new Chart(clicksCtx, {
    type: 'line',
    data: {
      labels: [],
      datasets: [{
        label: '쇼핑인사이트 검색어통계',
        data: [],
        borderColor: '#FF6384',
        fill: false
      }]
    },
    options: { maintainAspectRatio: false }
  });

  $('#search-button').click(function(event) {
    event.preventDefault();

    var startDate = $('#startDate-year option:selected').val() + "-" + $('#startDate-month option:selected').val() + "-" + $('#startDate-day option:selected').val();
    var endDate = $('#endDate-year option:selected').val() + "-" + $('#endDate-month option:selected').val() + "-" + $('#endDate-day option:selected').val();
    var timeUnit = $('#timeUnit').val();
    var selectedOption = $('#category option:selected');
    var category = selectedOption.data('cid');
    var key_param =  $('#keyword').val();
    var key_name = selectedOption.val() + '/' + key_param;
    var gender = $('#gender').val();
    var ages = $('input[name="age"]:checked').map(function() {
      return $(this).val();
    }).get();

    var jsonData = {
      "startDate": startDate,
      "endDate": endDate,
      "timeUnit": timeUnit,
      "category": category,
      "keyword": [{"name": key_name, "param": [key_param]}],
      "device": "",
      "gender": gender,
      "ages": ages
    };

    console.log("Keyword req Data ={} ", jsonData);

    $.ajax({
      type: 'POST',
      url: '/shopping/keyword',
      contentType: 'application/json',
      data: JSON.stringify(jsonData),
      success: function(response) {
        console.log("Keyword res Data ={} ", response);
        drawChart(response);
      },
      error: function(error) {
        console.error(error);
      }
    });
  });
});

function drawChart(response) {
  // 쇼핑인사이트 검색어통계 클릭량 라인차트 생성
  var ratio = [];
  var period = [];

  for (a = 0; a < response.results[0].data.length; a++) {
    ratio.push(response.results[0].data[a].ratio);
    period.push(response.results[0].data[a].period);
  }

  clicksChart.data.labels = period;
  clicksChart.data.datasets[0].data = ratio;
  clicksChart.update();

}