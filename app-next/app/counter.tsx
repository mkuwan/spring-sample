'use client'

import {useState} from "react";

export const Counter = () => {
    const [ count , setCount] = useState(0);

    return(
        <div>
            <p>You clicked {count} times</p>
            <button className={ "flex font-bold border-s-4 border-amber-600 bg-blue-500"} onClick={() => setCount(count + 1)}>Click Me</button>
        </div>
    )
}

export default Counter;