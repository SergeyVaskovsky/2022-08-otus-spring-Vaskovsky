const login = async (username, password) => {
  return await fetch(`/api/auth`, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: {'username': username,
           'password': password},

    })
    .then((response) => {
      if (response.ok) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  login,
  logout,
  getCurrentUser
};

export default AuthService;
