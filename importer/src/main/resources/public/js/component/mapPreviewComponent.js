var mapComponentPreview = Vue.component('map-preview', {
  props: {
    points: Array
  },
  data: {
    map: null
  },
  methods: {
    onUpload() {
      var points = [];

      for(var i = 0; i < this.points.length; i++){
         points[i] = [this.points[i].longitude.toFixed(5), this.points[i].latitude.toFixed(5)];
      }

      for (var i = 0; i < points.length; i++) {
          points[i] = ol.proj.transform(points[i], 'EPSG:4326', 'EPSG:3857');
      }

      var featureLine = new ol.Feature({
        geometry: new ol.geom.LineString(points)
      });

      var vectorLine = new ol.source.Vector({});
      vectorLine.addFeature(featureLine);

      var vectorLineLayer = new ol.layer.Vector({
        source: vectorLine,
        style: new ol.style.Style({
          fill: new ol.style.Fill({ color: '#00FF00', weight: 4 }),
          stroke: new ol.style.Stroke({ color: '#000000', width: 5 })
        })
      });

      this.view.setZoom(15)
      this.view.setCenter(points[0]);
      this.map.addLayer(vectorLineLayer);
    }
  },
  watch: {
    'points': function () {
      this.onUpload();
    }
  },
  mounted: function () {
    this.view = new ol.View({
      center: ol.proj.fromLonLat([11.365946, 44.513743]),
      zoom: 6
    });
    this.map = new ol.Map({
      target: 'map',
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM()
        })
      ],
      view: this.view
    });

//    var points = [[11.0578, 44.0158], [11.0558, 44.0198]];
//
//      for (var i = 0; i < points.length; i++) {
//          points[i] = ol.proj.transform(points[i], 'EPSG:4326', 'EPSG:3857');
//      }
//
//      var featureLine = new ol.Feature({
//        geometry: new ol.geom.LineString(points)
//      });
//
//      var vectorLine = new ol.source.Vector({});
//      vectorLine.addFeature(featureLine);
//
//      var vectorLineLayer = new ol.layer.Vector({
//        source: vectorLine,
//        style: new ol.style.Style({
//          fill: new ol.style.Fill({ color: '#00FF00', weight: 4 }),
//          stroke: new ol.style.Stroke({ color: '#000000', width: 5 })
//        })
//      });
//      this.view.setCenter(points[0]);
//      this.view.setZoom(15)
//      this.map.addLayer(vectorLineLayer);
  
},
  template: '<div id="map" class="map"></div>'
});