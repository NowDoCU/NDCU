<script>
import {Bar} from 'vue-chartjs'

export default {
    name: 'DetailGraph',
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
                        data: []
                    }
                ]
            },
            options: {
                maintainAspectRatio: false,
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            stepSize: 10,
                        },
                    }],
                    xAxes: [{
                        gridLines: {
                            lineWidth: 0
                        }
                    }]
                }
            }
        }
    },
    props: {
        dataset: Object
    },
    methods:{
        getValue: function() {
            // 그래프 이름
            this.datacollection.datasets[0].label = this.dataset.name
            // 그래프의 x축에 해당하는 카테고리
            this.datacollection.labels = this.dataset.category
            // 각각의 카테고리에 해당하는 데이터 값
            this.datacollection.datasets[0].data = this.dataset.value

            // 데이터값이 가장 큰 막대만 색을 빨간색으로 바꿔서 색상을 넣어준 것 (나머지는 회색)
            var maxValue = Math.max.apply(null, this.dataset.value)
            for(var i=0;i<this.dataset.value.length;i++) {
                if(this.dataset.value[i] == maxValue) {
                    this.datacollection.datasets[0].backgroundColor.push('rgb(220, 27, 27, 0.7)')
                }
                else {
                    this.datacollection.datasets[0].backgroundColor.push('#cccccc')
                }
            }
        }
    },
    mounted () {
        this.getValue()
        this.renderChart(this.datacollection, this.options)
    },
};
</script>
<style scoped lang="scss">

</style>