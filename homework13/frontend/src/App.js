import React from 'react';
import './App.css';
import Home from './main/Home';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import BookList from './book/BookList';
import BookEdit from "./book/BookEdit";
import Login from "./security/Login";
import useUser from "./security/UseUser";
import AppNavbar from "./main/AppNavbar";

export default function App() {

    const { user, setUser } = useUser();

    if(!user) {
        return <Login setUser={setUser} />
    }

    return (

        <Router>
            <AppNavbar setUser={setUser} />
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/books" element={<BookList />} />
                <Route path='/books/:id' element={<BookEdit />} />
            </Routes>
        </Router>

    )
}

