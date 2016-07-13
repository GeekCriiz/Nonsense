
function loadMap(latitude, longitude, nameGym, addressGym, descriptionGym) {
    var latlng = new google.maps.LatLng(latitude, longitude);
    var myOptions = {
      zoom: 19,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_container"),myOptions);
	
    var contentString = '<div id="content">'+
    '<div id="siteNotice">'+
    '</div>'+
    '<h1 id="firstHeading" class="firstHeading">' + nameGym + '</h1>'+
    '<div id="bodyContent">'+
    '<p><b>' + nameGym + '</b> (' + addressGym + ').</p>'+
    '<p>' + descriptionGym + '</p>'+
    '</div>'+
    '</div>';

    var infowindow = new google.maps.InfoWindow({
    	content: contentString
    });
    
    var marker = new google.maps.Marker({
      position: latlng, 
      map: map, 
      draggable: true,
      animation: google.maps.Animation.BOUNCE,
      title: "my hometown, Malim Nawar!"
    }); 
    marker.addListener('click', function() {
        infowindow.open(map, marker);
    });
}



