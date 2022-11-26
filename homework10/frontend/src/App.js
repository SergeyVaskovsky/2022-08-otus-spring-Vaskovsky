import React from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import BookList from './BookList';
import BookEdit from "./BookEdit";

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path='/' exact={true} element={<Home/>}/>
                <Route path='/books' exact={true} element={<BookList/>}/>
                <Route path='/books/:id' element={<BookEdit/>}/>
            </Routes>
        </Router>
    )
}

