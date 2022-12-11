import { useState } from 'react';
import AuthService from "../service/AuthService";

export default function useUser() {

    const [user, setUser] = useState();

    const saveUser = user=> {
        localStorage.setItem('user', user);
        setUser(user);
    };

    return {
        setUser: saveUser,
        user
    }

}