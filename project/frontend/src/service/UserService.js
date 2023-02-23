export default class UserService {
    register = async (user) => {
        return await fetch(`/api/users`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(
                {
                    'name': user.name,
                    'email': user.email,
                    'password': user.password
                }
            ),
        }).then(response => response.json());
    }
}