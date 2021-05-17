import React from 'react'

export default function Model(props) {
    let color;
    if (props.status == "ready") {
        color = "green";
    }
    else{
        color="red";
    }
    const style={
        backgroundColor: color,
            padding: 20,
            width: 250,
            height: 50, border: 20,
            borderRadius: 25
    }
    return (
        <div style={style}>
            {props.date} <br />
            {props.status}
        </div>
    )
}