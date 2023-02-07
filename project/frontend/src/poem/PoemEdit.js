import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";
import PoemTextElementEdit from "./PoemTextElementEdit";

export default function PoemEdit() {

    const emptyItem = {
        title: '',
        elements: []
    };

    const emptyTextElement = {
        id: -1,
        content: '',
        type: 'text'
    }

    const emptyPictureElement = {
        id: -1,
        picture: '',
        type: 'picture'
    }

    const [item, setItem] = useState(emptyItem);
    let navigate = useNavigate();
    const {id} = useParams();
    const poemService = new PoemService();
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setIsLoading(false);
        if (id !== 'new') {
            setIsLoading(true);
            poemService.getPoem(id)
                .then(data => {
                    setIsLoading(false);
                    setItem(data);
                });
        }
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

    const handleAddText = event => {
        item.elements.push(emptyTextElement);
        setItem(item);
    }

    const onChangeTextState = (index, text) => {
        item.elements[index].content = text;
        setItem(item);
    };

    const onDeleteTextState = (outerIndex) => {
        let remainingElements = [...item.elements].filter((value, innerIndex) => innerIndex !== outerIndex)
        item.elements = [];
        item.elements = remainingElements.slice();
        setItem(item);
    };

    const elementsList =
        item.elements.map((element, index) => {
            return <PoemTextElementEdit state={{"index": index, "element": element}}
                                        onChangeTextState={onChangeTextState}
                                        onDeleteTextState={onDeleteTextState}/>
        });

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
                        {elementsList}
                        <br/>
                        <Link to={""} onClick={(event) => handleAddText(event)}>Добавить текст</Link>
                        <br/>
                        {/*<Link to={""} onClick={handleAddPicture()}>Добавить иллюстрацию</Link>
                        <br/>*/}
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

