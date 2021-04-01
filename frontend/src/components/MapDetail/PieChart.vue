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
            this.datacollection.labels = this.dataset.category
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