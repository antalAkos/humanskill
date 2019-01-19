
$(document).ready(function(){
        let formdata = new FormData();
        let file = $("#file-706").val();
    $("#file-706").on('change',function(){

        $(".loader").css('display', 'inline-block');
        if ( window.FileReader ) {
          let reader = new FileReader();

          reader.readAsDataURL(file);
        }
        if (formdata) {
          formdata.append("file-706", file);
            $.ajax({
              url: "/save-cv",
              type: "POST",
              data: formdata,
              processData: false,
              contentType: false,
              success: function (res) {
              console.log("success");
                      $(".loader").css('display', 'none');
                      $(".tick").css('display', 'inline-block');


              },
              error: function() {
                console.log("error");
              }
            });
        }
    });
});


