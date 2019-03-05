$(document).ready(function () {

    $("#submitForm").on('click', function (event) {
        console.log("form submitted");
        //stop submit the form, we will post it manually.
        event.preventDefault();
        if($("#phone").val().length > 6) {
            // Get form
            let form = $('#submitCV')[0];
            // Create an FormData object
            /*var data = new FormData(form);
            data.append();*/

            // disabled the submit button
            //$("#submitForm").prop("disabled", true);
            data = JSON.stringify({"phone":$("#phone").val(), "job" : "welder"});

            $.ajax({
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
                swal({
                        title: "Sikeresen jelentkezett!",
                        text: "Köszönjük jelentkezését, elfogadtuk és kollégánk 24 órán belül felkeresi! Amennyiben hamarabb felvenné velünk a kapcsolatot, a +36203827224 számon teheti meg",
                        icon: "success"
                    });
                    /*swal("Jelentkezése nem sikerült, kérjük próbálja újra!", "", "warning");*/
                    console.log("ERROR : ", e);

                }
            });
        } else {
            swal("Jelentkezése nem sikerült, kérjük próbálja újra!", "", "warning");
        }


    });

});
