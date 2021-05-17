import logo from './logo.svg';
import './App.css';
import Models from './ModelsList'
import { Component } from 'react';

class App extends Component {
  state = {
    models: []
  };
  async componentDidMount() {
    const modelsResponse = await fetch("/api/models");
    this.setState({models: await modelsResponse.json()})
    //this.state.models = await modelsResponse.json();
  }
  render() {
    return (
      <Models models={this.state.models}></Models>
    );
  }
}


export default App;
