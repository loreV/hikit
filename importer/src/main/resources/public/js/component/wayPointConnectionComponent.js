var wayPointComponent = Vue.component('way-point-connection', {
    props: {
        index: Number
      },
      data: {
      },
      methods: {
      },
    template: `
<div :waypoint="index">
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

