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
            this.datacollection.datasets[0].label = this.dataset.name
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

<style scoped lang="scss">

</style>