var wayPointAdderComponent = Vue.component('way-point-adder', {
  data: function(){
    return {
      children: []
    }
  },
  methods: {
    add() {
      this.children.push(wayPointComponent);
    }
  },
  mounted: function () {
    this.children = [];
  },
  template: `
    <div>
    <h1 class="title">Connections</h1>
    <div>
      <template v-for="(child, index) in children">
        <way-point-connection :id="index"></way-point-connection>
      </template>
    </div>
    <button class="button is-primary" @click="add()">+</button>
    </div>
    `
});