import React, { Component, TextInput } from 'react';
import './App.css';
import { styles } from './AppStyles';
import DatePicker from 'react-datepicker';
import { XYPlot, VerticalBarSeries } from 'react-vis';
import * as moment from 'moment'
import 'react-datepicker/dist/react-datepicker.css';


class App extends Component {

  constructor (props) {
    super(props)
    this.state = {
      startDate: moment()
    };
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(date) {
    this.setState({
      startDate: date
    });
  }

  render() {

    const data = [
      { x: 0, y: 8 },
      { x: 1, y: 5 },
      { x: 2, y: 4 },
      { x: 3, y: 9 },
      { x: 4, y: 1 },
      { x: 5, y: 7 },
      { x: 6, y: 6 },
      { x: 7, y: 3 },
      { x: 8, y: 2 },
      { x: 9, y: 0 }
    ];

    return (
      <div className="App">
        <div style={styles.navBar}>
          TESTE
        </div>
        <DatePicker
            selected={this.state.startDate}
            onChange={this.handleChange}
            showTimeSelect
            timeFormat="HH:mm"
            injectTimes={[
              moment().hours(0).minutes(1),
              moment().hours(12).minutes(5),
              moment().hours(23).minutes(59)
            ]}
            dateFormat="LLL"
          />
      </div>
    );
  }
}

export default App;
