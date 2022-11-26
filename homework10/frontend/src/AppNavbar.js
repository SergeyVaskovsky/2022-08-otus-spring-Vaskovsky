import React from 'react';
import {Navbar, NavbarBrand} from 'reactstrap';
import {Link} from 'react-router-dom';

export default function AppNavbar() {
    return <Navbar color="dark" dark expand="md">
        <NavbarBrand tag={Link} to="/">Домой</NavbarBrand>
    </Navbar>;
}