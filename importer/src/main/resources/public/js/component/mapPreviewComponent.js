var mapComponentPreview = Vue.component('map-preview', {
  props: {
    points: Array
  },
  data: {
    map: null,
    mapProjection: {
      EPSG4326: "EPSG:4326",
      EPSG3857: "EPSG:3857"
    },
    // Colors
    mapStyling: {
      black: "#000000"
    },
    processing: {
      decimalApproximation: 5
    }
  },
  methods: {
    onUpload() {
      var points = [];

      for (var i = 0; i < this.points.length; i++) {
        points[i] = [this.points[i].longitude.toFixed(this.processing.decimalApproximation),
        this.points[i].latitude.toFixed(this.processing.decimalApproximation)];
      }

      for (var i = 0; i < points.length; i++) {
        points[i] = ol.proj.transform(points[i], this.mapProjection.EPSG4326, this.mapProjection.EPSG3857);
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
          stroke: new ol.style.Stroke({ color: this.mapStyling.black, width: 5 })
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

  },
  template: '<div id="map" class="map"></div>'
});