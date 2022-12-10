import React, { useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import {Form, Input} from 'reactstrap';

import AuthService from "./service/AuthService";

const Login = () => {
  let navigate = useNavigate();

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
          navigate("/books");
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
        <Form onSubmit={handleLogin} ref={form}>
          <div>
            <label htmlFor="username">Имя пользователя</label>
            <Input
              type="text"
              name="username"
              value={username}
              onChange={onChangeUsername}
            />
          </div>

          <div>
            <label htmlFor="password">Пароль</label>
            <Input
              type="password"
              name="password"
              value={password}
              onChange={onChangePassword}
            />
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
      </div>
    </div>
  );
};

export default Login;
