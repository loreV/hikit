var positionInputComponent = Vue.component('position-input', {
  props: {
    classifier: String
  },
  data: function () {
  },
  mounted: function () {
  },
  template: '<div :id="classifier""> <div class="field"> <label class="label">Name(Optional)</label> <div class="control"> ' +
    '<input class="input" type="text" name="name"> </div> </div> <div class="field"> <label class="label">Postcode</label>' +
    '<div class="control"> <input class="input" type="text" placeholder="Text input" name="postcode"> </div> </div> <div class="field">' + 
    '<label class="label">Altitude</label> <div class="control"> <input class="input" type="text" placeholder="Text input" name="altitude"> '+
    '</div> </div> <div class="field"> <label class="label">Latitude</label> <div class="control"> <input class="input" type="text" placeholder="Text input" name="latitude">'+
    '</div> </div> <div class="field"> <label class="label">Longitude</label> <div class="control"> <input class="input" type="number" placeholder="Text input" name="longitude">'+
    '</div> </div> <textarea class="textarea" placeholder="e.g. Any short description" name="description"></textarea> <div class="field"> <label class="label">tags (divided by commas)</label>'+
    '<div class="control"> <input class="input" type="text" placeholder="Text input" name="tags"> </div> </div> </div>'
})
