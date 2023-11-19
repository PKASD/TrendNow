    function deleteBoard(idx) {
        if (confirm("이 글을 삭제하시겠습니까?")) {
            boardNum = BigInt(idx);
            const deleteUrl = boardNum + '/delete';

            fetch(deleteUrl, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                   window.location.href = './'
                }
            })
            .catch(error => {
                console.error('에러 발생:', error);
            });
        }
    }

  function deleteComment(comIdx) {
        if (confirm("이 댓글을 삭제하시겠습니까?")) {
            comNum = BigInt(comIdx);
            const deleteUrl = '/gallery/comment-delete/' + comIdx;

            fetch(deleteUrl, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                   window.location.reload();
                }
            })
            .catch(error => {
                console.error('에러 발생:', error);
            });
        }
  }

    $('#add_comment').on("click", function() {
        let c = $('input[name="context"]').val();

        let obj = {
            'context': c
        };

        $.ajax({
            method: "POST",
            url: 'write',
            data: JSON.stringify(obj),
            contentType: 'application/json',
            success: function(data, status, xhr) {
                console.log("Comment Saved idx: " + data.idx);
            },
        });
    });

    $(document).ready(function() {
    $('#comment-input').on('keyup', function() {
        $('#comment_cnt').html("("+$(this).val().length+" / 30)");

        if($(this).val().length > 30) {
            $(this).val($(this).val().substring(0, 30));
            $('#comment_cnt').html("(30 / 30)");
        }
    });
});