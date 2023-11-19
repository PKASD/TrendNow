var genderChart;
$(document).ready(function() {
  updateDayOptions();

    var genderCtx = document.getElementById('genderChart').getContext('2d');
    genderChart = new Chart(genderCtx, {
      type: 'doughnut',
      data: {
        labels: ['남자','여자'],
        datasets: [{
          data: [],
          backgroundColor: ['#36A2EB', '#FFCE56'] //도넛의 각 배경색
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

    console.log("KeyGender req Data ={} ", jsonData);

    $.ajax({
      type: 'POST',
      url: '/shopping/keyword/gender',
      contentType: 'application/json',
      data: JSON.stringify(jsonData),
      success: function(response) {
        console.log("KeyGender res Data ={} ", response);
        drawGenderChart(response);
      },
      error: function(error) {
        console.error(error);
      }
    });
  });
});

function drawGenderChart(response) {
  var female = 0;
  var male = 0;
  for(a = 0; a < response.results[0].data.length;a++){
      if(response.results[0].data[a].group == 'f'){
        female += response.results[0].data[a].ratio;
     }
     else{
     male += response.results[0].data[a].ratio;
     }
  }
  var femaleRatio = female/(female + male)*100;
  var maleRatio = 100 - femaleRatio;

  genderChart.data.datasets[0].data = [maleRatio,femaleRatio];

  genderChart.update();
}
