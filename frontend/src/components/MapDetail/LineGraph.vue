<script>
import {Line} from 'vue-chartjs'

export default {
    extends: Line,
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
                        data: []
                    }
                ]
            },
            options: {
                maintainAspectRatio: true,
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            stepSize: 20,
                        }
                    }]
                }
            }
        }
    },
    props: {
        dataset: Object
    },
    methods: {
        getValue: function() {
            // 그래프 이름
            this.datacollection.datasets[0].label = this.dataset.name
            // 그래프의 x축에 해당하는 카테고리
            this.datacollection.labels = this.dataset.category
            // 각각의 카테고리에 해당하는 데이터 값
            this.datacollection.datasets[0].data = this.dataset.value
        }
    },
    mounted () {
        this.getValue()
        this.renderChart(this.datacollection, this.options)
    }
}
</script>

<style scoped lang="scss">

</style>