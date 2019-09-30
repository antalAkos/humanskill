jQuery(document).ready(function () {



    jQuery("#submitForm").on('click', function (event) {

        event.preventDefault();
        clickUp();

    });

     jQuery("#submitFormBottom").on('click', function (event) {

            event.preventDefault();
            clickUp();

        });

});

function clickUp() {
    if( jQuery("#phone").val().length > 6) {
                // Get form
                let form = jQuery('#submitCV')[0];
                // Create an FormData object
                /*var data = new FormData(form);
                data.append();*/

                // disabled the submit button
                //jQuery("#submitForm").prop("disabled", true);
                data = JSON.stringify({"phone":jQuery("#phone").val()});

                jQuery.ajax({
                    type: "POST",
                    enctype: 'application/json',
                    url: "/addname",
                    data: data,
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 6000,
                    success: function (data) {
                       window.location.href += '/jelentkezes'

                    },
                    error: function (e) {

                        /*swal("Jelentkezése nem sikerült, kérjük próbálja újra!", "", "warning");*/
                       window.location.href += '/hiba'


                       console.log("ERROR : ", e);
                        // jQuery("#submitForm").prop("disabled", false);

                    }
                });
            } else {
                   window.location.href = window.location += '/hiba'

            }

}