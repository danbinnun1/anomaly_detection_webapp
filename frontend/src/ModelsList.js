import React from 'react'
import Model from './Model'

export default function Models(props){
    return (
        <ul class="list-group">
          {props.models.map(item=>(
              <li className="list-group-item list-group-item-primary">
                  <Model status={item.status} date={item.uploadTime}></Model>
              </li>
          ))}
        </ul>
    )
}