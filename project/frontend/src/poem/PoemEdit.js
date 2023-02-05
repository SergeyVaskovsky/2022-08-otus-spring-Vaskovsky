import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";

export default function PoemEdit() {

    const emptyItem = {
        title: ''
    };
    const [item, setItem] = useState(emptyItem);
    let navigate = useNavigate();
    const {id} = useParams();
    const poemService = new PoemService();
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setIsLoading(false);
        /*if (id !== 'new') {
            setIsLoading(true);
            poemService.getBook(id)
                .then(data => {
                    setIsLoading(false);
                    if (data.id === 0) {
                        setCrash(true);
                    }

                    setItem(data);
                });
        }*/
    }, [id, setItem]);

    const handleChange = value => {
        let poem = {...item};
        poem.title = value;
        setItem(poem);
    }

    const handleSubmit = async event => {
        event.preventDefault();

        setIsLoading(true);
        await poemService.save(item).then(poem => {
            setIsLoading(false);
            navigate('/poems');
        })
    }

    return (
        <div>
            <AppNavbar/>
            <Container>
                <Loading isLoading={isLoading}/>
                <h2> {item.id ? 'Изменить стихотворение' : 'Добавить стихотворение'} </h2>
                <Form onSubmit={(event) => {
                    handleSubmit(event)
                }}>
                    <FormGroup>
                        <Label for="name">Название</Label>
                        <Input type="text" name="title" id="title" value={item.title || ''}
                               onChange={(event) => handleChange(event.target.value)} autoComplete="title"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Сохранить</Button>
                        <Button color="secondary" tag={Link} to="/poems">Отмена</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
}

