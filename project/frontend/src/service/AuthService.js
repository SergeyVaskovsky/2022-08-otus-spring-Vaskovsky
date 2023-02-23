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
                localStorage.setItem("user", username);
                return;
            } else if (response.status === 403) {
                throw new Error("У Вас нет доступа к данному ресурсу или неправильные логин или пароль");
            }
            throw new Error("Неправильные логин или пароль");
        });
};

const logout = () => {
    localStorage.removeItem("user");
    Cookies.remove('JSESSIONID')
};

const getCurrentUser = () => {
    const user = localStorage.getItem("user");
    return user === "undefined" ? undefined : user;
};

const AuthService = {
    login,
    logout,
    getCurrentUser
};

export default AuthService;