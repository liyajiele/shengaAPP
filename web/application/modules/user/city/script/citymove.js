
  var ic = [];
  $(function() {
    var listgt = $(".zflist div").eq(0).height();
    $(".zflist div").each(          //侧栏26个字母
      function(r) {
        ic.push($(".zflist div").eq(r).offset().top - $(window).scrollTop())
      }
    )
    
    $(".zflist").on("touchstart", function(event) {      //触摸出现
      ff(event)
      $(".biaoqian").show();
    })
    
    
    $(".zflist").on("touchend", function() {         //点击跳转
//  	alert(1)
console.log($(".aui-list-header").eq(icae).offset().top/1000)
      $(".citybigbox").animate({
        scrollTop: $(".aui-list-header").eq(icae).offset().top /1000+ "rem"
      }, {
        duration: 500,
        easing: "swing"
      });
      $(".biaoqian").hide();
      return false;
    })
    $(".zflist").on("touchmove", function(event) {
      event.preventDefault();
      ff(event)

    })
    function ff(event) {
      var dheight = event.changedTouches[0].clientY;
      
      $(ic).each(
        function(ri) {
          if(dheight < ic[0]) {
            overfun(0);
            return false;
          }
          if(dheight > ic[ic.length - 1]) {
            overfun(ic.length - 1);
            return false;
          }
          if(ic[ri] < dheight && dheight < ic[ri] + listgt) {
            overfun(ri);
            return false;
          }
        }
      )
    }
    var icae = 0;
    function overfun(aic) {
      icae = aic;
      $(".biaoqian").html($(".zflist div").eq(aic).html());
      $("html, body").stop(true);
      return false;
    }
  })
