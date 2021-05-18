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
            <div style={{ position: 'fixed', width: 320, overflowY: 'scroll', top: 10, bottom: '20%', left: '83%' }}>
            {this.state.models.map(item=>(
                <li style={{listStyleType:'none'}}>
                    <Model status={item.status} date={item.uploadTime} />
                    <br />
                </li>
            ))}
            </div>
        )
    }
}

export default Models