import React, { Component } from 'react';
import logo from './logo.svg';
import Slider from 'react-rangeslider';
import './App.css';
import getData from './Api';
import 'react-input-range/lib/css/index.css';
import InputRange from 'react-input-range';
import './AppStyles.css';
import ReactChartkick, { LineChart, PieChart } from 'react-chartkick';
import Highcharts from 'highcharts/highstock';
import HighchartsReact from 'highcharts-react-official';
import ToggleButton from 'react-toggle-button';
import { RadioGroup, Radio } from 'react-radio-group';

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
        serverName: 'MrBurns',
        yearValue: { min: 2014, max: 2018 },
        monthValue: { min: 1, max: 12 },
        dayValue: { min: 1, max: 31 },
        hourValue: { min: 0, max: 23 },
        xLabel: 'ANO',
        data: [
            { name: 'r', data: [1, 2], visible: true },
            { name: 'sy', data: [1, 2], visible: true },
            { name: 'us', data: [1, 2], visible: true },
            { name: 'rMax', data: [1, 2], visible: true },
            { name: 'syMax', data: [1, 2], visible: true },
            { name: 'usMax', data: [1, 2], visible: true }
        ],
        clicked: false,
        selectedValue: 0
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
            const params = {
                serverName: this.state.serverName
            };

            getData(params).then((data) => {
                console.log(data);
                if (Object.keys(data).length > 0) {
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

    updateLabels = () => {
        this.state.data[0].visible = false;
    };

    changeServer = (value) => {
        this.setState(
            {
                serverName: value
            },
            () => this.updateData()
        );
    };

    render() {
        const data = this.state.data[0].data;
        const options = {
            title: {
                text: 'VM Stat'
            },
            plotOptions: {
                series: {
                    turboThreshold: 500000 //larger threshold or set to 0 to disable
                }
            },
            rangeSelector: {
                buttons: [
                    {
                        type: 'hour',
                        count: 1,
                        text: '1hr'
                    },
                    {
                        type: 'hour',
                        count: 3,
                        text: '3hr'
                    },
                    {
                        type: 'hour',
                        count: 12,
                        text: '12hr'
                    },
                    {
                        type: 'day',
                        count: 1,
                        text: '1d'
                    },
                    {
                        type: 'day',
                        count: 3,
                        text: '3d'
                    },
                    {
                        type: 'day',
                        count: 5,
                        text: '5d'
                    },
                    {
                        type: 'day',
                        count: 7,
                        text: '7d'
                    },
                    {
                        type: 'month',
                        count: 1,
                        text: '1m'
                    },
                    {
                        type: 'month',
                        count: 3,
                        text: '3m'
                    },
                    {
                        type: 'month',
                        count: 6,
                        text: '6m'
                    },
                    {
                        type: 'year',
                        count: 1,
                        text: '1y'
                    },
                    {
                        type: 'all',
                        text: 'All'
                    }
                ],
                selected: 2
            },
            chart: {
                zoomType: 'x'
            },
            xAxis: {
                type: 'datetime'
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
                        <RadioGroup name="fruit" selectedValue={this.state.serverName} onChange={this.changeServer}>
                            <Radio value="MrBurns" />MrBurns
                            <Radio value="yogafire" />Yogafire
                            <Radio value="neo-slayer" />NeoSlayer
                        </RadioGroup>
                        <div className="varContainerRow">
                            <div className="varContainer">
                                <div className="label">R</div>
                                <ToggleButton
                                    value={this.state.data[0].visible}
                                    onToggle={(value) => {
                                        var data = this.state.data;
                                        data[0].visible = !data[0].visible;
                                        this.setState({
                                            data: data
                                        });
                                    }}
                                />
                            </div>
                            <div className="varContainer">
                                <div className="label">SY</div>
                                <ToggleButton
                                    value={this.state.data[1].visible}
                                    onToggle={(value) => {
                                        var data = this.state.data;
                                        data[1].visible = !data[1].visible;
                                        this.setState({
                                            data: data
                                        });
                                    }}
                                />
                            </div>
                            <div className="varContainer">
                                <div className="label">US</div>
                                <ToggleButton
                                    value={this.state.data[2].visible}
                                    onToggle={(value) => {
                                        var data = this.state.data;
                                        data[2].visible = !data[2].visible;
                                        this.setState({
                                            data: data
                                        });
                                    }}
                                />
                            </div>
                        </div>
                        <div className="varContainerRow">
                            <div className="varContainer">
                                <div className="label">RMax</div>
                                <ToggleButton
                                    value={this.state.data[3].visible}
                                    onToggle={(value) => {
                                        var data = this.state.data;
                                        data[3].visible = !data[3].visible;
                                        this.setState({
                                            data: data
                                        });
                                    }}
                                />
                            </div>
                            <div className="varContainer">
                                <div className="label">SYMax</div>
                                <ToggleButton
                                    value={this.state.data[4].visible}
                                    onToggle={(value) => {
                                        var data = this.state.data;
                                        data[4].visible = !data[4].visible;
                                        this.setState({
                                            data: data
                                        });
                                    }}
                                />
                            </div>
                            <div className="varContainer">
                                <div className="label">USMax</div>
                                <ToggleButton
                                    value={this.state.data[5].visible}
                                    onToggle={(value) => {
                                        var data = this.state.data;
                                        data[5].visible = !data[5].visible;
                                        this.setState({
                                            data: data
                                        });
                                    }}
                                />
                            </div>
                        </div>
                    </div>
                </div>
                <div className="chartContainer">
                    <HighchartsReact highcharts={Highcharts} constructorType={'stockChart'} options={options} />
                </div>
                <p className="App-intro">{}</p>
            </div>
        );
    }
}

export default App;
