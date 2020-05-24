// Get importer base address
var address = window.location.href.split("/");
var BASE_IMPORTER_ADDRESS = address[0] + "//" + address[2]

// Get service base address, by asking the user
let base_api_array = BASE_IMPORTER_ADDRESS.split(":")
base_api_array.splice(2,1, prompt("Please specifify Hikit-Web service port"));
var BASE_API_ADDRESS_SERVICE = base_api_array.join(":");

var vue = new Vue({ el: '#app' });

