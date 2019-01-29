
jQuery(document).ready(function(){
   /*     let formdata = new FormData();
    jQuery("#file-706").on('change',function(e){
        let file = jQuery("#file-706").val();

        jQuery(".loader").css('display', 'inline-block');
        if ( window.FileReader ) {
          let reader = new FileReader();
         reader.onload = function(e) {
                      this.setState({file: reader.result})
                    }
          reader.readAsDataURL(file);
        }
        if (formdata) {
          formdata.append("file-706", file);
            jQuery.ajax({
              url: "/save-cv",
              type: "POST",
              data: formdata,
              processData: false,
              contentType: false,
              success: function (res) {
              console.log("success");
                      jQuery(".loader").css('display', 'none');
                      jQuery(".tick").css('display', 'inline-block');


              },
              error: function() {
                console.log("error");
              }
            });
        }
    });*/
    jQuery('#file-706').change(function(){
        //on change event
        formdata = new FormData();
        if(jQuery(this).prop('files').length > 0)
        {
            jQuery(".loader").css('display', 'inline-block');
            jQuery(".error").css('display', 'none');

            file =jQuery(this).prop('files')[0];
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
                   jQuery(".loader").css('display', 'none');
                   jQuery(".tick").css('display', 'inline-block');
            },
            error: function() {
                            console.log("error");
                            jQuery(".loader").css('display', 'none');
                            jQuery(".error").css('display', 'inline-block');
                          }
        });
    });
});


