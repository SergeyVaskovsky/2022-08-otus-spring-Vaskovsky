import Cookies from 'js-cookie'

const login = async (username, password) => {
    return await fetch(`/api/auth`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(
            {
                'username': username,
                'password': password
            })
    })
        .then((response) => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 403) {
                throw new Error("У Вас нет доступа к данному ресурсу или неправильные логин или пароль");
            }
            throw new Error("Неправильные логин или пароль");
        }).then(data => {
            localStorage.setItem("userId", data.id);
            localStorage.setItem("userName", data.name);
            localStorage.setItem("user", username);
            localStorage.setItem("authorities", data.authorities);
        });
};

const logout = () => {
    localStorage.removeItem("userId");
    localStorage.removeItem("userName");
    localStorage.removeItem("user");
    localStorage.removeItem("authorities");
    Cookies.remove('JSESSIONID')
};

const getCurrentUserId = () => {
    const userId = localStorage.getItem("userId");
    return !userId || userId === null ? undefined : userId;
};

const getCurrentUserName = () => {
    const userName = localStorage.getItem("userName");
    return !userName || userName === null ? "" : userName;
};

const getCurrentUser = () => {
    const user = localStorage.getItem("user");
    return !user || user === null ? undefined : user;
};

const getCurrentAuthorities = () => {
    const authorities = localStorage.getItem("authorities");
    return !authorities ? [] : authorities;
};

const AuthService = {
    login,
    logout,
    getCurrentUser,
    getCurrentAuthorities,
    getCurrentUserId,
    getCurrentUserName
};

export default AuthService;