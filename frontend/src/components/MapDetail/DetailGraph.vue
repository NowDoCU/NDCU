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
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        pointBackgroundColor: 'white',
                        borderWidth: 1,
                        pointBorderColor: '#249EBF',
                        data: []
                    }
                ]
            },
            options: {
                maintainAspectRatio: false,
                scales: {
                    yAxes: [{
                        tickes: {
                            beginAtZero: true,
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
            this.datacollection.datasets[0].label = this.dataset.name
            this.datacollection.labels = this.dataset.category
            this.datacollection.datasets[0].data = this.dataset.value
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