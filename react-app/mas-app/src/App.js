import React, { Component } from 'react';
import logo from './logo.svg';
import Slider from 'react-rangeslider';
import './App.css';
import getData from './Api';
import 'react-input-range/lib/css/index.css';
import InputRange from 'react-input-range';
import './AppStyles.css';
import ReactChartkick, { LineChart, PieChart } from 'react-chartkick';
import Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';

const throttle = (func, limit) => {
    let inThrottle;
    return function() {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
            func.apply(context, args);
            inThrottle = true;
            setTimeout(() => (inThrottle = false), limit);
        }
    };
};

ReactChartkick.addAdapter(Highcharts);

class App extends Component {
    state = {
        server: 1,
        yearValue: { min: 2014, max: 2018 },
        monthValue: { min: 1, max: 12 },
        dayValue: { min: 1, max: 31 },
        hourValue: { min: 0, max: 23 },
        minValue: { min: 10, max: 50 },
        xLabel: 0,
        data: [
            { name: 'r', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'b', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'swp', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'free', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'buff', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'si', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'so', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'bo', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'in', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'cs', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'us', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'svalue', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'id', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'wa', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] },
            { name: 'st', data: [['2014', 29.9], ['2015', 71.5], ['2016', 106.4]] }
        ],
        clicked: false
    };

    componentDidMount() {
        // const data = getData();
        // this.setState({
        //     data: data
        // });
    }

    onYearChanged = (value) => {
        this.setState({ yearValue: value });
        this.updateData();
    };

    onMinuteChanged = (value) => {
        this.setState({ minValue: value });
        this.updateData();
    };

    onMonthChanged = (value) => {
        this.setState({ monthValue: value });
        this.updateData();
    };

    onDayChanged = (value) => {
        this.setState({ dayValue: value });
        this.updateData();
    };

    onHourChanged = (value) => {
        this.setState({ hourValue: value });
        this.updateData();
    };

    updateData = () => {
        if (!this.state.clicked) {
            getData().then((data) => {
                console.log(data);
                if (data) {
                    this.setState({
                        data: data.data
                    });
                }
            });
            this.setState({
                clicked: true
            });
            setTimeout(() => {
                this.setState({
                    clicked: false
                });
            }, 700);
        }
    };

    render() {
        //const data = this.state.data[0].data;
        const options = {
            title: {
                text: 'VM Stat'
            },
            // xAxis: {
            //     tickInterval: 1,
            //     labels: {
            //         enabled: true,
            //         formatter: function() {
            //             return data[this.value][0];
            //         }
            //     }
            // },
            series: this.state.data
        };

        return (
            <div className="mainContainer">
                <div className="navBar">
                    <div className="varContainer">
                        <div className="label">Ano</div>
                        <InputRange maxValue={2018} minValue={2014} value={this.state.yearValue} onChange={(yearValue) => this.onYearChanged(yearValue)} />
                    </div>
                    <div className="varContainer">
                        <div className="label">Mês</div>
                        <InputRange maxValue={12} minValue={1} value={this.state.monthValue} onChange={(monthValue) => this.onMonthChanged(monthValue)} />
                    </div>
                    <div className="varContainer">
                        <div className="label">Dia</div>
                        <InputRange maxValue={30} minValue={1} value={this.state.dayValue} onChange={(dayValue) => this.onDayChanged(dayValue)} />
                    </div>
                    <div className="varContainer">
                        <div className="label">Hora</div>
                        <InputRange maxValue={23} minValue={1} value={this.state.hourValue} onChange={(hourValue) => this.onHourChanged(hourValue)} />
                    </div>
                    <div className="varContainer">
                        <div className="label">Minuto</div>
                        <InputRange maxValue={50} minValue={0} value={this.state.minValue} onChange={(minValue) => this.onMinuteChanged(minValue)} />
                    </div>
                </div>
                <div className="chartContainer">
                    {/* <LineChart data={this.state.data} /> */}
                    <HighchartsReact highcharts={Highcharts} options={options} />
                </div>
                <p className="App-intro">{}</p>
            </div>
        );
    }
}

export default App;
