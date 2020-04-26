Vue.component('importer', {
    methods: {
        onUpload(response) {
            console.log('uploadCompleted!')
        }
    },
    components: {
        'positionComponent': positionInputComponent,
        'uploadComponent': uploadInputComponent
    },
    template: '<section id="home" class="hero is-medium"> <div class="hero-body" id="importer"> <div class="container"> <div class="columns"> <div class="column is-2"> <upload-input v-on:uploadResponse="onUpload"></upload-input> </div> <div class="column is-5"> <h1 class="title">Import Trail</h1> <form> <div class="field"> <label class="label">Trail code</label> <div class="control"> <input class="input" type="text" placeholder="E.g: 100, 101BO"> </div> </div> <div class="field"> <label class="label">Name (Optional)</label> <div class="control"> <input class="input" type="text" placeholder="Text input"> </div> </div> <div class="field"> <label class="label">Ranking level</label> <div class="control"> <div class="select"> <select> <option>Select dropdown</option> <option>With options</option> </select> </div> </div> </div> <textarea class="textarea" placeholder="e.g. Any short description"></textarea> </form> <div> <h1 class="title">Connections</h1> <button class="button is-primary is-light">Add connecting-waypoint</button> </div> <div> <h1 class="title">Start Pos</h1> <position-input classifier="startPos"></position-input> </div> <div> <h1 class="title">Final Pos</h1> <position-input classifier="endPos"></position-input> </div> </div> </div> </div> </div> </section>'
});