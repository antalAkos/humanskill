jQuery(document).ready(function($){(function(){try{$('.magnific-pop').magnificPopup({type:'image'});}catch(ignore){}})();});(function(){var slides_selector='.cycloneslider-template-dark .cycloneslider-slides';slides_selector+=',.cycloneslider-template-default .cycloneslider-slides';slides_selector+=',.cycloneslider-template-standard .cycloneslider-slides';slides_selector+=',.cycloneslider-template-thumbnails .cycloneslider-slides';slides_selector+=',.cycloneslider-template-galleria .cycloneslider-slides';slides_selector+=',.cycloneslider-template-text .cycloneslider-slides';slides_selector+=',.cycloneslider-template-dos .cycloneslider-slides';jQuery(document).on('cycle-before',slides_selector,function(event,optionHash,outgoingSlideEl,incomingSlideEl,forwardFlag){var slide=jQuery(outgoingSlideEl),curHeight=0,nextHeight=0;if("on"==optionHash.dynamicHeight){curHeight=jQuery(outgoingSlideEl)[0].getBoundingClientRect().height;if(undefined==curHeight||0==curHeight){curHeight=jQuery(outgoingSlideEl).outerHeight();}nextHeight=jQuery(incomingSlideEl)[0].getBoundingClientRect().height;if(undefined==nextHeight||0==nextHeight){nextHeight=jQuery(incomingSlideEl).outerHeight();}if(nextHeight!=curHeight)jQuery(this).animate({height:nextHeight},optionHash.autoHeightSpeed,optionHash.autoHeightEasing);}if(slide.hasClass('cycloneslider-slide-youtube'))pauseYoutube(slide);if(slide.hasClass('cycloneslider-slide-vimeo'))pauseVimeo(slide);});jQuery(document).on('cycle-initialized cycle-after',slides_selector,function(event,optionHash,outgoingSlideEl,incomingSlideEl,forwardFlag){var index=(event.type=='cycle-initialized')?optionHash.currSlide:optionHash.nextSlide;var slide=jQuery(optionHash.slides[index]);if(false==optionHash.hideNonActive)slide.css('zIndex',parseInt(slide.css('zIndex'))+20);});function pauseYoutube(slide){var data={"event":"command","func":"pauseVideo","args":[],"id":""}
postMessage(slide.find('iframe'),data,'*');}function pauseVimeo(slide){postMessage(slide.find('iframe'),{method:'pause'},slide.find('iframe').attr('src'));}function postMessage(iframe,data,url){try{if(iframe[0]){iframe[0].contentWindow.postMessage(JSON.stringify(data),url);}}catch(ignore){}}})();