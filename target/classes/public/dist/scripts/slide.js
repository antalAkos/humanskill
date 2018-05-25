//$(document).on('scroll', check_if_in_view);


function check_if_in_view() {
    //let $animation_elements =
    var window_height = $window.height();
    var window_top_position = $window.scrollTop();
    var window_bottom_position = (window_top_position + window_height);

    $.each($animation_elements, function() {
        var $element = $(this);
        var element_height = $element.outerHeight();
        var element_top_position = $element.offset().top;
        var element_bottom_position = (element_top_position + element_height);

        //check to see if this current container is within viewport
        if ((element_bottom_position >= window_top_position) &&
            (element_top_position <= window_bottom_position)) {
            $element.addClass('non-reached');
        } else {
            $element.removeClass('non-reached');
        }
    });
}

slide = {
    reInsertAnimation: function (elements) {
        for (let i=0; i< elements.length;i++) {
            elements[i].style.webkitAnimationPlayState = "running";
            elements[i].addEventListener('webkitAnimationEnd', function() {
                this.style.webkitAnimationPlayState = "paused";
            });
        }

    },
    inserAOS: function (elements) {
        for (let i=0; i< elements.length;i++) {
            elements[i].setAttribute("data-aos", "fade-right");

        }

    },

    reviveAnimation: function (elements) {
        for (let i=0; i< elements.length;i++) {
            elements[i].classList.remove("run-animation");
            void elements[i].offsetWidth;
            elements[i].classList.add("run-animation");
        }

    }
}
