Vue.component('position-input', {
  props: ["idpassed", "id"],
  data: function () {
    return {
      count: 0
    }
  },
  template: '<div> <div class="field"> {{ idpassed }} <label class="label">Name(Optional)</label> <div class="control"> <input class="input" type="text" id="{{id}}" placeholder="{{ idpassed }}"> </div> </div> <div class="field"> <label class="label">Postcode</label> <div class="control"> <input class="input" type="text" placeholder="Text input"> </div> </div> <div class="field"> <label class="label">Start altitude</label> <div class="control"> <input class="input" type="text" placeholder="Text input"> </div> </div> <div class="field"> <label class="label">Latitude</label> <div class="control"> <input class="input" type="text" placeholder="Text input"> </div> </div> <div class="field"> <label class="label">Longitude</label> <div class="control"> <input class="input" type="text" placeholder="Text input"> </div> </div> <textarea class="textarea" placeholder="e.g. Any short description"></textarea> <div class="field"> <label class="label">tags (divided by commas)</label> <div class="control"> <input class="input" type="text" placeholder="Text input"> </div> </div> </div>'
})
