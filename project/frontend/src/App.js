import React from 'react';
import './App.css';
import Home from './main/Home';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import PoemList from './poem/PoemList';
import PoemEdit from "./poem/PoemEdit";

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path='/' exact={true} element={<Home/>}/>
                <Route path='/poems' exact={true} element={<PoemList/>}/>
                <Route path='/poems/:id' element={<PoemEdit/>}/>
            </Routes>
        </Router>
    )
}

