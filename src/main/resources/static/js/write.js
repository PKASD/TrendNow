$(document).ready(function() {
    $('#title').on('keyup', function() {
        $('#title_cnt').html("("+$(this).val().length+" / 50)");

        if($(this).val().length > 50) {
            $(this).val($(this).val().substring(0, 50));
            $('#title_cnt').html("50 / 50");
        }
    });
});

$(document).ready(function() {
    $('#context').on('keyup', function() {
        $('#context_cnt').html("("+$(this).val().length+" / 500)");

        if($(this).val().length > 500) {
            $(this).val($(this).val().substring(0, 500));
            $('#context_cnt').html("500 / 500");
        }
    });
});