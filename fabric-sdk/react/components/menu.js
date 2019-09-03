import React from 'react'
import { NavLink } from 'react-router-dom'

const navBorderStyle = {
    borderBottom : '1.7px solid rgba(92, 101, 93, 0.3)'
}
const selectedStyle = {
    borderBottom : '3px solid #2DB400'
}

export const Menu = () => 
    <nav className="navbar navbar-expand-lg navbar-light">
        <div className="container">
        <div className="collapse navbar-collapse" id="navbarResponsive" style={navBorderStyle}>
            <ul className="navbar-nav">
                <li className="nav-item">
                    <NavLink to="/play" activeStyle={selectedStyle} className="nav-link">
                        Play
                    </NavLink>
                </li>
                <li className="nav-item">
                    <NavLink to="/search" activeStyle={selectedStyle} className="nav-link">
                        Search
                    </NavLink>
                </li>
            </ul>
        </div>
    </div>
    </nav>
    

export default Menu

