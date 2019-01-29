;(function () {
	
	'use strict';

	var mobileMenuOutsideClick = function() {

		jQuery(document).click(function (e) {
	    var container = jQuery("#gtco-offcanvas, .js-gtco-nav-toggle");
	    if (!container.is(e.target) && container.has(e.target).length === 0) {
	    	jQuery('.js-gtco-nav-toggle').addClass('gtco-nav-white');

	    	if ( jQuery('body').hasClass('offcanvas') ) {

    			jQuery('body').removeClass('offcanvas');
    			jQuery('.js-gtco-nav-toggle').removeClass('active');
				
	    	}
	    
	    	
	    }
		});

	};


	var offcanvasMenu = function() {

		jQuery('#page').prepend('<div id="gtco-offcanvas" />');
		jQuery('#page').prepend('<a href="#" class="js-gtco-nav-toggle gtco-nav-toggle gtco-nav-white"><i></i></a>');
		var clone1 = jQuery('.menu-1 > ul').clone();
		jQuery('#gtco-offcanvas').append(clone1);
		var clone2 = jQuery('.menu-2 > ul').clone();
		jQuery('#gtco-offcanvas').append(clone2);

		jQuery('#gtco-offcanvas .has-dropdown').addClass('offcanvas-has-dropdown');
		jQuery('#gtco-offcanvas')
			.find('li')
			.removeClass('has-dropdown');

		// Hover dropdown menu on mobile
		jQuery('.offcanvas-has-dropdown').mouseenter(function(){
			var jQuerythis = jQuery(this);

			jQuerythis
				.addClass('active')
				.find('ul')
				.slideDown(500, 'easeOutExpo');				
		}).mouseleave(function(){

			var jQuerythis = jQuery(this);
			jQuerythis
				.removeClass('active')
				.find('ul')
				.slideUp(500, 'easeOutExpo');				
		});


		jQuery(window).resize(function(){

			if ( jQuery('body').hasClass('offcanvas') ) {

    			jQuery('body').removeClass('offcanvas');
    			jQuery('.js-gtco-nav-toggle').removeClass('active');
				
	    	}
		});
	};


	var burgerMenu = function() {

		jQuery('body').on('click', '.js-gtco-nav-toggle', function(event){
			var jQuerythis = jQuery(this);


			if ( jQuery('body').hasClass('overflow offcanvas') ) {
				jQuery('body').removeClass('overflow offcanvas');
			} else {
				jQuery('body').addClass('overflow offcanvas');
			}
			jQuerythis.toggleClass('active');
			event.preventDefault();

		});
	};



	var contentWayPoint = function() {
		var i = 0;

		// jQuery('.gtco-section').waypoint( function( direction ) {


			jQuery('.animate-box').waypoint( function( direction ) {

				if( direction === 'down' && !jQuery(this.element).hasClass('animated-fast') ) {
					
					i++;

					jQuery(this.element).addClass('item-animate');
					setTimeout(function(){

						jQuery('body .animate-box.item-animate').each(function(k){
							var el = jQuery(this);
							setTimeout( function () {
								var effect = el.data('animate-effect');
								if ( effect === 'fadeIn') {
									el.addClass('fadeIn animated-fast');
								} else if ( effect === 'fadeInLeft') {
									el.addClass('fadeInLeft animated-fast');
								} else if ( effect === 'fadeInRight') {
									el.addClass('fadeInRight animated-fast');
								} else {
									el.addClass('fadeInUp animated-fast');
								}

								el.removeClass('item-animate');
							},  k * 200, 'easeInOutExpo' );
						});
						
					}, 100);
					
				}

			} , { offset: '85%' } );
		// }, { offset: '90%'} );
	};


	var dropdown = function() {

		jQuery('.has-dropdown').mouseenter(function(){

			var jQuerythis = jQuery(this);
			jQuerythis
				.find('.dropdown')
				.css('display', 'block')
				.addClass('animated-fast fadeInUpMenu');

		}).mouseleave(function(){
			var jQuerythis = jQuery(this);

			jQuerythis
				.find('.dropdown')
				.css('display', 'none')
				.removeClass('animated-fast fadeInUpMenu');
		});

	};


	var owlCarousel = function(){
		
		var owl = jQuery('.owl-carousel-carousel');
		owl.owlCarousel({
			items: 3,
			loop: true,
			margin: 20,
			nav: true,
			dots: true,
			smartSpeed: 800,
			autoHeight: true,
			navText: [
		      "<i class='ti-arrow-left owl-direction'></i>",
		      "<i class='ti-arrow-right owl-direction'></i>"
	     	],
	     	responsive:{
	        0:{
	            items:1
	        },
	        600:{
	            items:2
	        },
	        1000:{
	            items:3
	        }
	    	}
		});


		var owl = jQuery('.owl-carousel-fullwidth');
		owl.owlCarousel({
			items: 1,
			loop: true,
			margin: 20,
			nav: true,
			dots: true,
			smartSpeed: 800,
			autoHeight: true,
			navText: [
		      "<i class='ti-arrow-left owl-direction'></i>",
		      "<i class='ti-arrow-right owl-direction'></i>"
	     	]
		});


		

	};

	var tabs = function() {

		// Auto adjust height
		jQuery('.gtco-tab-content-wrap').css('height', 0);
		var autoHeight = function() {

			setTimeout(function(){

				var tabContentWrap = jQuery('.gtco-tab-content-wrap'),
					tabHeight = jQuery('.gtco-tab-nav').outerHeight(),
					formActiveHeight = jQuery('.tab-content.active').outerHeight(),
					totalHeight = parseInt(tabHeight + formActiveHeight + 90);

					tabContentWrap.css('height', totalHeight );

				jQuery(window).resize(function(){
					var tabContentWrap = jQuery('.gtco-tab-content-wrap'),
						tabHeight = jQuery('.gtco-tab-nav').outerHeight(),
						formActiveHeight = jQuery('.tab-content.active').outerHeight(),
						totalHeight = parseInt(tabHeight + formActiveHeight + 90);

						tabContentWrap.css('height', totalHeight );
				});

			}, 100);
			
		};

		autoHeight();


		// Click tab menu
		jQuery('.gtco-tab-nav a').on('click', function(event){
			
			var jQuerythis = jQuery(this),
				tab = jQuerythis.data('tab');

			jQuery('.tab-content')
				.addClass('animated-fast fadeOutDown');

			jQuery('.tab-content')
				.removeClass('active');

			jQuery('.gtco-tab-nav li').removeClass('active');
			
			jQuerythis
				.closest('li')
					.addClass('active')

			jQuerythis
				.closest('.gtco-tabs')
					.find('.tab-content[data-tab-content="'+tab+'"]')
					.removeClass('animated-fast fadeOutDown')
					.addClass('animated-fast active fadeIn');


			autoHeight();
			event.preventDefault();

		}); 
	};


	var goToTop = function() {

		jQuery('.js-gotop').on('click', function(event){
			
			event.preventDefault();

			jQuery('html, body').animate({
				scrollTop: jQuery('html').offset().top
			}, 500, 'easeInOutExpo');
			
			return false;
		});

		jQuery(window).scroll(function(){

			var jQuerywin = jQuery(window);
			if (jQuerywin.scrollTop() > 200) {
				jQuery('.js-top').addClass('active');
			} else {
				jQuery('.js-top').removeClass('active');
			}

		});
	
	};


	// Loading page
	var loaderPage = function() {
		jQuery(".gtco-loader").fadeOut("slow");
	};

	var counter = function() {
		jQuery('.js-counter').countTo({
			 formatter: function (value, options) {
	      return value.toFixed(options.decimals);
	    },
		});
	};

	var counterWayPoint = function() {
		if (jQuery('#gtco-counter').length > 0 ) {
			jQuery('#gtco-counter').waypoint( function( direction ) {
										
				if( direction === 'down' && !jQuery(this.element).hasClass('animated') ) {
					setTimeout( counter , 400);					
					jQuery(this.element).addClass('animated');
				}
			} , { offset: '90%' } );
		}
	};

	
	jQuery(function(){
		mobileMenuOutsideClick();
		offcanvasMenu();
		burgerMenu();
		contentWayPoint();
		dropdown();
		owlCarousel();
		tabs();
		goToTop();
		loaderPage();
		counterWayPoint();
	});


}());