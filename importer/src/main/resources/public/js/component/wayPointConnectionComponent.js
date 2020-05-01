var wayPointComponent = Vue.component('way-point-connection', {
    props: {
        id: Number
      },
      data: {
      },
      methods: {
      },
    template: `
<div :waypoint="id">
    <h1>Connection n{{ id }}</h1>
    <position-input></position-input>
    <div class="field">
        <label class="label">TrailCode</label>
        <div class="control">
            <input class="input" type="text" placeholder="Text input">
        </div>
    </div>
    <div class="field">
        <label class="label">Postcode</label>
        <div class="control">
            <input class="input" type="text" placeholder="Text input">
        </div>
    </div>
</div>
`
});

