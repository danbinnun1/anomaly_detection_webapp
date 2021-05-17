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

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <FileUploadBox onUpload={(file) => {
          const json = convertToJSON(file);
          fetch(json)
        }} />
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
