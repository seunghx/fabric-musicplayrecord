import App from './components/app'
import store from './util/store'
import React from 'react'
import {HashRouter} from 'react-router-dom'
import { Provider } from 'react-redux'
import { render } from 'react-dom'
import '../resources/static/vendor/bootstrap/css/bootstrap.css';
import 'react-datepicker/dist/react-datepicker.css'

window.react = React
window.store = store

render(
    <Provider store={store}>
        <HashRouter>
            <App />
        </HashRouter>
    </Provider> , 
    document.getElementById('root')
)

