import React, { useState, useRef } from "react";
import {Container, Form, FormGroup, Input, Label} from 'reactstrap';
import PropTypes from 'prop-types';
import AuthService from "../service/AuthService";

const Login = ({setUser}) => {

  const form = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    setMessage("");
    setLoading(true);

      AuthService.login(username, password).then(
        () => {
          setUser(AuthService.getCurrentUser())
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          setLoading(false);
          setMessage(resMessage);
        }
      );
  };

  return (
    <div>
      <div>
        <Container>
        <Form onSubmit={handleLogin} ref={form}>
          <div>
            <FormGroup>
            <Label htmlFor="username">Имя пользователя</Label>
            <Input
              type="text"
              name="username"
              value={username}
              onChange={onChangeUsername}
            />
            </FormGroup>
          </div>

          <div>
            <FormGroup>
            <Label htmlFor="password">Пароль</Label>
            <Input
              type="password"
              name="password"
              value={password}
              onChange={onChangePassword}
            />
            </FormGroup>
          </div>

          <div>
            <button className="btn btn-primary btn-block" disabled={loading}>
              {loading && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Войти</span>
            </button>
          </div>

          {message && (
            <div>
              <div className="alert alert-danger" role="alert">
                {message}
              </div>
            </div>
          )}
        </Form>
        </Container>
      </div>
    </div>
  );
};

export default Login;

Login.propTypes = {
  setUser: PropTypes.func.isRequired
}

