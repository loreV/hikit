var poiComponent = Vue.component('poi-component', {
    props: {
        index: String
      },
      data: {
      },
      methods: {
      },
    template: `
<div :poi_index="index">
    <p>Hi n {{index}}</p>
</div>
`
});

