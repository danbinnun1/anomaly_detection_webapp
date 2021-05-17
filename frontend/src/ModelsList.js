import React from 'react'

class ModelsList extends React.Component {
    
    constructor(props) {
        super(props);

        this.state = {
            models: []
        };
    }

    addModel(model) {
        this.state.models.push(model);
    }
    removeModel(model) {
        const index = this.state.models.indexOf(model);
        if (index >= 0) {
            this.state.models.splice(index, 1);
        }
    }

    render() {
        
    }
}