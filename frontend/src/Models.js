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
            <div>
            {this.state.models.map(item=>(
                <li style={{listStyleType:'none'}}>
                    <Model status={item.status} date={item.uploadTime}></Model>
                    <br></br>
                </li>
            ))}
            </div>
        )
    }
}

export default Models