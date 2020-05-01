var poiAdderComponent = Vue.component('poi-point-adder', {
  data: function(){
    return {
      children: []
    }
  },
  methods: {
    add() {
      this.children.push(poiComponent);
    }
  },
  mounted: function () {
    this.children = [];
  },
  template: `
  <div>
      <h1 class="title">POI</h1>
      <div>
        <template v-for="(child, index) in children">
          <poi-component :id="index"></poi-component>
        </template>
      </div>
      <button class="button is-primary" @click="add()">+</button>
  </div>
  `
});