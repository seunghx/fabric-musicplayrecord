import {SearchContainer, MusicPlayersContainer} from '../util/containers'
import React from 'react'
import {
    Route,
    Redirect,
    Switch
} from 'react-router-dom'


const App = () => 
    <div className="container bg-white" >
        <Switch>
            <Redirect exact from="/" to="/play" />
            <Route path="/play" component={MusicPlayersContainer} />
            <Route path="/search" component={SearchContainer} />
        </Switch>
    </div>

    
export default App
