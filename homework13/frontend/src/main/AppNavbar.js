import React from 'react';
import {Button, Navbar, NavbarBrand} from 'reactstrap';
import {Link} from 'react-router-dom';
import AuthService from "../service/AuthService";

export default function AppNavbar({setUser}) {

    const logOut = () => {
        AuthService.logout();
        setUser(AuthService.getCurrentUser());
    };

    const user = AuthService.getCurrentUser();

    return <Navbar color="dark" dark expand="md">
        <NavbarBrand tag={Link} to="/">Домой</NavbarBrand>
        {user ? (
            <Button size="sm" color="secondary" onClick={logOut}>Выйти</Button>
        ) : <></>}
    </Navbar>;
}