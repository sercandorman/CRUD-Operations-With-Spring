$(document).ready(function() {
	var state = true;
				function close_accordion_section() {
					$('.accordion .accordion-section-title').removeClass('active');
					$('.accordion .accordion-section-content').slideUp(400).delay(500).removeClass('open');
				}
			$('.accordion-section-title').click(function(e) {
				if(state==true){
					$(".accordion ").animate({width:"100%"},500);
					state = !state;
				}else{
					setTimeout(function(){
						$(".accordion ").animate({width:"50%"},500);
						state = !state;
					}, 400);
				}
				
				// Grab current anchor value
				var currentAttrValue = $(this).attr('href');
				if($(e.target).is('.active')) {
					close_accordion_section();
				}else {
					close_accordion_section();
					// Add active class to section title
					$(this).addClass('active');
					// Open up the hidden content panel
					$('.accordion ' + currentAttrValue).slideDown(400).addClass('open'); 
				}
			e.preventDefault();
			});
});