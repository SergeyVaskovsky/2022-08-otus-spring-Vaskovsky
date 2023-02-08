import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";
import PoemTextElementEdit from "./PoemTextElementEdit";
import PoemPictureElementEdit from "./PoemPictureElementEdit";

export default function PoemEdit() {

    const emptyItem = {
        title: ''
    };

    const emptyTextElement = {
        id: 'new',
        content: '',
        type: 'text',
        poem: {}
    }

    const emptyPictureElement = {
        id: 'new',
        file: '',
        type: 'picture',
        poem: {}
    }

    const [item, setItem] = useState(emptyItem);
    const [elements, setElements] = useState([]);
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
                    setItem(data);
                    poemService.getPoemElements(id)
                        .then(data => {
                            setElements(data.elements);
                            setIsLoading(false);
                })
            });
        }
    }, [id, setItem, setElements]);

    const handleChange = value => {
        let poem = {...item};
        poem.title = value;
        setItem(poem);
    }

    const handleSubmit = async event => {
        event.preventDefault();

        setIsLoading(true);

        await poemService.savePoem(item).then(poem => {
            setIsLoading(false);
            navigate('/poems');
        })
    }

    const handleAddText = event => {
        let element = emptyTextElement;
        element.poem = item;
        poemService.addPoemElement(element)
            .then(data => {
                elements.push(data);
                setElements([...elements]);
            });
    }

    const onChangeTextState = (index, text) => {
        elements[index].content = text;
        poemService.updatePoemTextElement(elements[index])
            .then(data => {
                setElements([...elements]);
            });
    };

    const onDeleteState = (outerIndex) => {
        const id = elements[outerIndex].id;
        let remainingElements = [...elements].filter((value, innerIndex) => innerIndex !== outerIndex)
        setElements(remainingElements);
        poemService.deletePoemElement(id);
    };

    const handleAddPicture = event => {
        let element = emptyPictureElement;
        element.poem = item;
        poemService.addPoemElement(element)
            .then(data => {
                elements.push(data);
                setElements([...elements]);
            });
    }

    const onChangePictureState = (index, file) => {
        elements[index].file = file;
        poemService.updatePoemPictureElement(elements[index])
            .then(data => {
                setElements([...elements]);
            });
    };

    const elementsList =
        elements.map((element, index) => {
            return element.type === "text" ?
                    <PoemTextElementEdit state={{"index": index, "element": element}}
                                         onChangeTextState={onChangeTextState}
                                         onDeleteState={onDeleteState}/> :
                    <PoemPictureElementEdit state={{"index": index, "element": element}}
                                            onChangePictureState={onChangePictureState}
                                            onDeleteState={onDeleteState}/>


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
                        <Link to={""} onClick={(event) => handleAddPicture(event)}>Добавить иллюстрацию</Link>
                        <br/>
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

