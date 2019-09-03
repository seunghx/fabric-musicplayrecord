import React from 'react'
import Menu from '../components/menu'

const PageTemplate = ({children}) =>
    <div className="container">
        <Menu />
        {children}
    </div>

export default PageTemplate