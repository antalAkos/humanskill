
$(document).ready(function(){
   /*     let formdata = new FormData();
    $("#file-706").on('change',function(e){
        let file = $("#file-706").val();

        $(".loader").css('display', 'inline-block');
        if ( window.FileReader ) {
          let reader = new FileReader();
         reader.onload = function(e) {
                      this.setState({file: reader.result})
                    }
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
    });*/
    $('#file-706').change(function(){
        //on change event
        formdata = new FormData();
        if($(this).prop('files').length > 0)
        {
            $(".loader").css('display', 'inline-block');
            $(".error").css('display', 'none');

            file =$(this).prop('files')[0];
            formdata.append("file-706", file);
        }
        jQuery.ajax({
            url: "/save-cv",
            type: "POST",
            data: formdata,
            processData: false,
            contentType: false,
            success: function (result) {
                  console.log("success");
                   $(".loader").css('display', 'none');
                   $(".tick").css('display', 'inline-block');
            },
            error: function() {
                            console.log("error");
                            $(".loader").css('display', 'none');
                            $(".label").append('<span class="error" style="color:red;">Hiba történt, próbálja újra!</span>');

                          }
        });
    });
});


