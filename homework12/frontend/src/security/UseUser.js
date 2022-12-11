import { useState } from 'react';

export default function useUser() {

    const [user, setUser] = useState();

    const saveUser = user => {
        localStorage.setItem('user', user);
        setUser(user);
    };

    return {
        setUser: saveUser,
        user
    }

}