$(function() {
  $('#submit').click(function() {
    var $btn = $(this);
    var form_data = new FormData($('#uploadForm')[0]);
    $.ajax({
      type: 'POST',
      url: '/detect',
      data: form_data,
      contentType: false,
      processData: false,
      beforeSend: function() {
        $btn.prop('disabled', true);
        $btn.html(
          `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Loading...`
        );
      },
      complete: function() {
        $btn.removeAttr('disabled');
        $btn.html('Submit');
      },
      dataType: 'json'
    }).done(function(data, textStatus, jqXHR) {
      swal('Hasil', data.label + ': ' + data.percent.toFixed(2) + '%', 'success')
    }).fail(function(data) {
      console.error(data);
    });
  });
});