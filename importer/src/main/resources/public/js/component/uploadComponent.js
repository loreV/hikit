var uploadInputComponent = Vue.component('upload-input', {
  data: {},
  methods: {
    readGpxFile(event) {
      var customFormData = new FormData();
      const form = document.querySelector("#uploader")
      customFormData.append("gpxFile", form.files[0]);
      axios.post(BASE_IMPORTER_ADDRESS + "/api/v1/trails/gpx", customFormData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(response => (this.$emit('uploadResponse', response)))

      axios.post(BASE_API_ADDRESS_SERVICE + "/api/v1/trails/", customFormData, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(response => (this.$emit('uploadConnectionResponse', response)))
    }
  },
  template: `
  <form v-on:change="readGpxFile">
    <div class="file">
        <label class="file-label">
            <input class="file-input" type="file" id="uploader">
            <span class="file-cta">
                <span class="file-icon">
                    <i class="fas fa-upload"></i>
                </span>
                <span class="file-label"> Choose a GPX file </span>
            </span>
        </label>
    </div>
</form>
`
})
