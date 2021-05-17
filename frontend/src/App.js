import logo from './logo.svg';
import './App.css';
import Models from './ModelsList'

async function App() {
  const modelsResponse=await fetch("/api/models");
  const modelsJson=await modelsResponse.json();
  return (
    <Models models={modelsJson}></Models>
  );
}

export default App;
