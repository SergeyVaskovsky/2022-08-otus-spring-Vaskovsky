import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import BookList from './BookList';
import BookEditWithNavigate from "./BookEdit";

class App extends Component {
    render() {
        return (
            <Router>
                <Routes>
                    <Route path='/' exact={true} element={<Home/>}/>
                    <Route path='/books' exact={true} element={<BookList/>}/>
                    <Route path='/books/:id' element={<BookEditWithNavigate/>}/>
                </Routes>
            </Router>
        )
    }
}

export default App;