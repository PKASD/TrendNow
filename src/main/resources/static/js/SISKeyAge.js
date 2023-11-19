var ageChart;
var groupedAgeData = {};

$(document).ready(function() {
  updateDayOptions();

  ageCtx = document.getElementById('ageChart').getContext('2d');
  ageChart = new Chart(ageCtx, {
    type: 'bar',
    data: {
      labels: ['20'],
      datasets: [{
        label: '연령별',
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
      "keyword": key_param,
      "device": "",
      "gender": gender,
      "ages": ages
    };

    console.log("KeyAge req Data ={} ", jsonData);

    $.ajax({
      type: 'POST',
      url: '/shopping/keyword/age',
      contentType: 'application/json',
      data: JSON.stringify(jsonData),
      success: function(response) {
        console.log("KeyAge res Data ={} ", response);
        drawAgeChart(response);
      },
      error: function(error) {
        console.error(error);
      }
    });
  });
});

function drawAgeChart(response) {
  var ageGroup = [];
  var ratio = [];
    for (var a = 0; a < response.results[0].data.length; a++) {
        //찾으려는 값이 있으면 -1 리턴
        var groupIndex = ageGroup.indexOf(response.results[0].data[a].group);
        if (groupIndex !== -1) {
          ratio[groupIndex] += response.results[0].data[a].ratio;
        } else {
          ageGroup.push(response.results[0].data[a].group);
          ratio.push(response.results[0].data[a].ratio);
        }
    }
    //오름차순 정렬
    ageGroup.sort((a, b) => parseInt(a) - parseInt(b));

    ageChart.data.labels = ageGroup;
    ageChart.data.datasets[0].data = ratio;
    ageChart.update();
}