<script>
import {Pie} from 'vue-chartjs'

export default {
    extends: Pie,
    data: function() {
        return {
            datacollection: {
                labels: [],
                datasets: [
                    {
                        backgroundColor: ['rgba(220, 25, 25, 0.8)', 'rgba(54, 162, 235, 0.8)'],
                        pointBackgroundColor: 'white',
                        pointBorderColor: '#249EBF',
                        data: []
                    }
                ]
            },
            options: {
                maintainAspectRatio: true,
            }
        }
    },
    props: {
        dataset: Object
    },
    methods: {
        getValue: function() {
            // 그래프의 카테고리
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

<style>

</style>