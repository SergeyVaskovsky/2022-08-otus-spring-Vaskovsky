import React, {Component} from 'react';
import Home from './Home';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import BookList from './BookList';
import BookEdit from "./BookEdit";

class App extends Component {
    render() {
        return (
            <Router>
                <Routes>
                    <Route path='/' element={<Home/>}/>
                    <Route path='/book' element={<BookList/>}/>
                    <Route path='/book/new' element={<BookEdit/>}/>
                </Routes>
            </Router>
        )
    }
}

export default App;