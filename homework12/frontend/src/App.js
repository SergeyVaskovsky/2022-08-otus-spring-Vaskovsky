import React from 'react';
import './App.css';
import Home from './main/Home';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import BookList from './book/BookList';
import BookEdit from "./book/BookEdit";
import Login from "./Login";
import AuthService from "./service/AuthService";

export default function App() {
    const user = AuthService.getCurrentUser();
    return (
        <Router>
            <Routes>
                <Route path='/' exact={true} element={<Home/>}/>
                <Route path="/login" element={<Login/>} />
                    <Route path='/books' exact={true} element={user ? <BookList/> : <Login/>}/>
                    <Route path='/books/:id' element={user ? <BookEdit/> : <Login/>}/>
            </Routes>
        </Router>
    )
}

