var $=jQuery.noConflict();
function saveCV() {
    $("#wpcf7-form").submit(function() {
           $.ajax({
                 type: "POST",
                 enctype: 'multipart/form-data',
                 url: "/save-cv",
                 data: data,
                 processData: false,
                 contentType: false,
                 cache: false,
                 timeout: 600000,
                 success: function (data) {

                     alert("Success")

                 },
                 error: function (e) {
                    alert(e.responseText())

                 }
           });

    })
}

saveCV();