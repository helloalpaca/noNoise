function loadMainChart() {
    let main_graph_canvas = document.getElementById('circle-graph');
    data = {
        datasets: [{
            data: [10, 20, 30],
            backgroundColor: [
                'red',
                'orange',
                'yellow'
            ]
        }],
        labels: [
            'Red',
            'Yellow',
            'Blue'
        ]
    };

    let main_graph = new Chart(main_graph_canvas, {
        type: 'doughnut',
        data: data,
        options: {
            legend: { // label 숨기기
                display: false,
            },
            responsive: false, // 크기 조절
        }
    });
}

function loadSubChart() {
    let date = 48;
    let bar_graph_canvas = document.getElementById('bar-graph');

    data = {
        datasets: [{
            data: [48],
            backgroundColor: [
                'red',
            ]
        }]
    };

    let bar_graph = new Chart(bar_graph_canvas, {
        type: 'horizontalBar',
        data: data,
        options: {
            legend: { // label 숨기기
                display: false,
            },
            responsive: false, // 크기 조절
            scales: {
                xAxes: [{ // x축 크기 조절
                    ticks: {
                        beginAtZero: true,
                        stepSize: 150,
                        suggestedMax: 150
                    }
                }]
            }
        }
    });
}