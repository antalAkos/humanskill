$(document).ready(function () {

    $("#submitForm").on('click', function (event) {
        console.log("form submitted");
        //stop submit the form, we will post it manually.
        event.preventDefault();
        if($("#name").val().length > 1 && $("#phone").val().length > 1) {
            // Get form
            let form = $('#submitCV')[0];
            console.log(form);
            // Create an FormData object
            var data = new FormData(form);

            // disabled the submit button
            //$("#submitForm").prop("disabled", true);

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

                    $("#feedBack").show().text("Sikeresen jelentkezett!").css({"color" : "green"});
                    console.log("SUCCESS : ", data);
                    //$("#submitForm").prop("disabled", false);

                },
                error: function (e) {

                    $("#feedBack").show().text("Jelentkezése nem sikerült, kérjük próbálja újra!").css({"color" : "red"});
                    console.log("ERROR : ", e);
                    // $("#submitForm").prop("disabled", false);

                }
            });
        }


    });

});
