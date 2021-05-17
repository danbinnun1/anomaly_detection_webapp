import React, {useState} from 'react'
import Model from './Model'

class Models extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            models : []
        }
    }

    componentDidMount() {
        fetch("/api/models")
        .then(respone => respone.json())
        .then(json => this.setState({models : json}));
    }

    render() {
        return (
            <ul class="list-group">
              {this.state.models.map(item=>(
                  <li className="list-group-item list-group-item-primary">
                      <Model status={item.status} date={item.uploadTime}></Model>
                  </li>
              ))}
            </ul>
        )
    }
}

export default Models