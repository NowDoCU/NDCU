<script>
import { Bar } from 'vue-chartjs';

export default {
   name: 'BarGraph',
   extends: Bar,
   data: function() {
      return {
         datacollection: {
            labels: [],
            datasets: [
               {
                  label: '',
                  backgroundColor: [],
                  pointBackgroundColor: 'white',
                  borderWidth: 1,
                  data: [],
               },
            ],
         },
         options: {
            maintainAspectRatio: false,
            scales: {
               yAxes: [
                  {
                     ticks: {
                        beginAtZero: true,
                        stepSize: 100000,
                     },
                  },
               ],
               xAxes: [
                  {
                     gridLines: {
                        lineWidth: 0,
                     },
                  },
               ],
            },
         },
      };
   },
   props: {
      dataset: Object,
      isLoad: Boolean,
   },
   methods: {
      getValue: function() {
         this.datacollection.datasets[0].label = this.dataset.name;
         this.datacollection.labels = this.dataset.category;
         this.datacollection.datasets[0].data = this.dataset.value;

         var maxValue = Math.max.apply(null, this.dataset.value);
         console.log(maxValue);

         for (var i = 0; i < this.dataset.value.length; i++) {
            if (this.datacollection.datasets[0].data[i] == maxValue) {
               this.datacollection.datasets[0].backgroundColor.push('rgb(220, 27, 27, 0.7)');
            } else {
               this.datacollection.datasets[0].backgroundColor.push('#cccccc');
            }
         }

         // 셋팅 후 재랜더링
         this.renderChart(this.datacollection, this.options);
      },
      initValue: function() {
         this.datacollection.datasets[0].label = '';
         this.datacollection.labels = [];
         this.datacollection.datasets[0].data = [];
         this.datacollection.datasets[0].backgroundColor = [];
         //  console.log('- 그래프 값 초기화', this.datacollection.datasets[0].data.length);
      },
   },
   mounted() {
      this.renderChart(this.datacollection, this.options);
   },
   watch: {
      isLoad: function() {
         if (this.isLoad) {
            // 로딩이 끝났으면, 그래프 출력
            this.getValue();
         } else {
            // 로딩이 안되었으면, 초기화
            this.initValue();
         }
      },
   },
};
</script>
<style scoped lang="scss"></style>
