<script>
import { Line } from 'vue-chartjs';

export default {
   extends: Line,
   props: {
      dataset: Object,
      isLoad: Boolean,
   },
   data: function() {
      return {
         datacollection: {
            labels: [],
            datasets: [
               {
                  label: '',
                  pointBackgroudColor: 'white',
                  pointBorderColor: '#cccccc',
                  borderColor: '#cccccc',
                  data: [],
               },
            ],
         },
         options: {
            maintainAspectRatio: true,
            scales: {
               yAxes: [
                  {
                     ticks: {
                        beginAtZero: true,
                        stepSize: 500000,
                     },
                  },
               ],
            },
         },
      };
   },
   methods: {
      getValue: function() {
         this.datacollection.datasets[0].label = this.dataset.name;
         this.datacollection.labels = this.dataset.category;
         this.datacollection.datasets[0].data = this.dataset.value;

         // 셋팅 후 재랜더링
         this.renderChart(this.datacollection, this.options);
         //  console.log('- 그래프 값 셋팅 완료', this.datacollection.datasets[0].data[0]);
      },
      initValue: function() {
         this.datacollection.datasets[0].label = '';
         this.datacollection.labels = [];
         this.datacollection.datasets[0].data = [];
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
