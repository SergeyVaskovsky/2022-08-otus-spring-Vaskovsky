import React from 'react';
import './App.css';
import Home from './main/Home';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import PoemList from './poem/PoemList';
import PoemEdit from "./poem/PoemEdit";
import PoemRead from "./poem/PoemRead";
import Login from "./user/Login";
import useUser from "./user/UseUser";
import RegisterUser from "./user/RegisterUser";
import AppNavbar from "./main/AppNavbar";

export default function App() {

    const {user, setUser} = useUser();

    if (!user) {
        return (<Router>
                <Routes>
                    <Route path='/' exact={true} element={<Login setUser={setUser}/>}/>
                    <Route path='/register' exact={true} element={<RegisterUser/>}/>
                </Routes>
            </Router>
        )
    }

    return (
        <Router>
            <AppNavbar setUser={setUser}/>
            <Routes>
                <Route path='/' exact={true} element={<Home/>}/>
                <Route path='/register' exact={true} element={<RegisterUser/>}/>
                <Route path='/poems' exact={true} element={<PoemList/>}/>
                <Route path='/poems/:id' element={<PoemEdit/>}/>
                <Route path='/readonly/poems' exact={true} element={<PoemList readonly={true}/>}/>
                <Route path='/readonly/poems/:id' element={<PoemRead withComments={true}/>}/>
            </Routes>
        </Router>
    )
}

