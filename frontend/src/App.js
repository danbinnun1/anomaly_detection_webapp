import {useState} from 'react'
import AnomalyPanel from './AnomalyPanel'
import ModelTrainingPanel from './ModelTrainingPanel'

function App() {

  const [currentModelId, setCurrentModelId] = useState();

  return (
    <div>
      <AnomalyPanel modelId={currentModelId} />

      <ModelTrainingPanel onModelSelect={modelId => {
        setCurrentModelId(modelId)}} />
    </div>
  );
}

export default App;
