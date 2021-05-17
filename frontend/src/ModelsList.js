import React from 'react'
import Model from './Model'

export default function Models(props){
    return (
        <ul class="list-group">
          <li class="list-group-item list-group-item-primary">List item 1</li>
          <li class="list-group-item list-group-item-primary">List item 2</li>
          <li class="list-group-item list-group-item-primary">List item 3</li>
          {props.models.map(item=>(
              <li className="list-group-item list-group-item-primary">
                  <Model status={item.status} date={item.date}></Model>
              </li>
          ))}
        </ul>
    )
}