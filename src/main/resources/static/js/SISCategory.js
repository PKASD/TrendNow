var searchChart;

$(document).ready(function() {
  updateDayOptions();

  // 초기 차트 생성
  var searchCtx = document.getElementById('searchChart').getContext('2d');
  searchChart = new Chart(searchCtx, {
    type: 'line',
    data: {
      labels: [],
      datasets: [{
        label: '쇼핑인사이트 분야통계',
        data: [],
        borderColor: '#FF6384',
        fill: false
      }]
    },
    options: { maintainAspectRatio: false }
  });

  $('#search-button').click(function(event) {
    event.preventDefault();

    var startDate = $('#startDate-year option:selected').val()+"-"+$('#startDate-month option:selected').val()+"-"+$('#startDate-day option:selected').val();
    var endDate = $('#endDate-year option:selected').val()+"-"+$('#endDate-month option:selected').val()+"-"+$('#endDate-day option:selected').val();
    var timeUnit = $('#timeUnit').val();
    var selectedOption = $('#category option:selected');
    var category = selectedOption.val();
    var categoryId = selectedOption.data('cid');
    var gender = $('#gender').val();
    var ages = $('input[name="age"]:checked').map(function() {
      return $(this).val();
    }).get();

    var jsonData = {
      "startDate": startDate,
      "endDate": endDate,
      "timeUnit": timeUnit,
      "category": [{"name": category, "param": [categoryId]}],
      "device": "",
      "gender": gender,
      "ages": ages
    };

    console.log("Category req Data", jsonData);

    $.ajax({
      type: 'POST',
      url: '/shopping/category',
      contentType: 'application/json',
      data: JSON.stringify(jsonData),
      success: function(response) {
         console.log("Category res Data", response);
         drawCategoryChart(response);
      },
      error: function(error) {
        console.error(error);
      }
    });
  });
});

function drawCategoryChart(response) {
  var ratio = [];
  var period = [];
   console.log(response.results[0].data);
  for (var a = 0; a < response.results[0].data.length; a++) {
    ratio.push(response.results[0].data[a].ratio);
    period.push(response.results[0].data[a].period);
  }
  searchChart.data.labels = period;
  searchChart.data.datasets[0].data = ratio;
  searchChart.update();
}