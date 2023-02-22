import React from 'react';
import './App.css';
import Home from './main/Home';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import PoemList from './poem/PoemList';
import PoemEdit from "./poem/PoemEdit";
import PoemRead from "./poem/PoemRead";

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path='/' exact={true} element={<Home/>}/>
                <Route path='/poems' exact={true} element={<PoemList/>}/>
                <Route path='/poems/:id' element={<PoemEdit/>}/>
                <Route path='/readonly/poems' exact={true} element={<PoemList readonly={true}/>}/>
                <Route path='/readonly/poems/:id' element={<PoemRead withComments={true}/>}/>
            </Routes>
        </Router>
    )
}

