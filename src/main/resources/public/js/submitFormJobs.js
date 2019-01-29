jQuery(document).ready(function () {

    jQuery("#submitForm").on('click', function (event) {
        console.log("form submitted");
        //stop submit the form, we will post it manually.
        event.preventDefault();
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
                    swal({
                        title: "Sikeresen jelentkezett!",
                        text: "Köszönjük jelentkezését, elfogadtuk és kollégánk 24 órán belül felkeresi! Amennyiben hamarabb felvenné velünk a kapcsolatot, a +36203827224 számon teheti meg",
                        icon: "success"
                    });

                },
                error: function (e) {
                    swal("Jelentkezése nem sikerült, kérjük próbálja újra!", "", "warning");

                   console.log("ERROR : ", e);
                    // jQuery("#submitForm").prop("disabled", false);

                }
            });
        } else {
            swal("Jelentkezése nem sikerült, kérjük próbálja újra!", "", "warning");
        }


    });

});
