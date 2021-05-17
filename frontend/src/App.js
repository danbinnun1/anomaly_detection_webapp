import logo from './logo.svg';
import './App.css';
import FileUploadBox from './FileUploadBox';

const convertToJSON = file => {
  var reader = new FileReader();

  reader.onload = () => {
    var lines = reader.result.split("\n");
    var result = [];
  
    var headers = lines[0].split(",");
  
    for (var i = 1; i < lines.length; ++i){
  
      var obj = {};
      var currentline = lines[i].split(",");
  
      for (var j = 0; j < headers.length; ++j) {
        obj[headers[j]] = currentline[j];
      }
  
      result.push(obj);
    }

    return JSON.stringify(result);
  }

  reader.readAsText(file);
}

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
