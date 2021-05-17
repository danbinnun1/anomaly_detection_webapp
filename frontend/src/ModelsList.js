import React from 'react'
import Model from './Model'

export default function Models(props){
    return (
        <div style={{position:'fixed',width:320,overflowY:'scroll',top:0,bottom:0}}>
          {props.models.map(item=>(
              <li style={{listStyleType:'none'}}>
                  <Model status={item.status} date={item.uploadTime}></Model>
                  <br></br>
              </li>
          ))}
        </div>
    )
}