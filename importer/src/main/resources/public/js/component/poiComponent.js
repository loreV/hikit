var poiComponent = Vue.component('poi-component', {
    props: {
        id: String
      },
      data: {
      },
      methods: {
      },
    template: `
<div :poi_index="id">
    <p>Hi n {{id}}</p>
</div>
`
});

