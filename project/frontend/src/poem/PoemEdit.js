import React, {useEffect, useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import {Button, Container, FormGroup, Input, Label} from 'reactstrap';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";
import PoemTextElementEdit from "./PoemTextElementEdit";
import PoemPictureElementEdit from "./PoemPictureElementEdit";
import PoemRead from "./PoemRead";
import "./Poem.css"

export default function PoemEdit() {

    const {id} = useParams();

    const emptyItem = {
        id: undefined,
        title: '',
        publishTime: undefined
    };

    const emptyTextElement = {
        id: -1,
        content: '',
        type: 'text',
        poem: {}
    }

    const emptyPictureElement = {
        id: -1,
        file: '',
        type: 'picture',
        poem: {},
        scale: 100
    }

    const [item, setItem] = useState(emptyItem);
    const [elements, setElements] = useState([]);
    const poemService = new PoemService();
    const [isLoading, setIsLoading] = useState(true);

    const [isChecked, setIsChecked] = useState(false)

    const checkHandler = async () => {
        setIsChecked(!isChecked);
        let poem = {...item};
        poem.publishTime = !isChecked ? new Date() : null;
        setItem(poem);
        setIsLoading(true);
        await poemService.savePoem(poem).then(poem => {
            setIsLoading(false);
        })
    }


    useEffect(() => {
        setIsLoading(false);
        if (id !== 'new') {
            setIsLoading(true);
            poemService.getPoem(id)
                .then(data => {
                    setItem(data);
                    if (data.publishTime != null) {
                        setIsChecked(true);
                    }
                    poemService.getPoemElements(id)
                        .then(data => {
                            data.sort((a, b) => a.id - b.id);
                            setElements(data);
                            setIsLoading(false);
                        })
                });
        } else {
            setIsLoading(true);
            poemService.savePoem(item).then(poem => {
                setItem(poem);
                setIsLoading(false);
            })
        }
    }, [id, setItem, setElements]);

    const handleChange = async value => {
        let poem = {...item};
        poem.title = value;
        setItem(poem);
        setIsLoading(true);
        await poemService.savePoem(poem).then(poem => {
            setIsLoading(false);
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
        let remainingElements = [...elements].filter((value, innerIndex) => value.id !== id)
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

    const onChangePictureState = (index, file, scale) => {
        elements[index].file = file;
        elements[index].scale = scale;
        poemService.updatePoemPictureElement(elements[index]);
    };

    const onChangePictureScale = (index, scale) => {
        elements[index].scale = scale;
        poemService.updatePoemPictureElementScale(elements[index]);
    };

    const elementsList =
        elements.map((element, index) => {
            return element.type === "text" ?
                <PoemTextElementEdit state={{"index": index, "element": element}}
                                     onChangeTextState={onChangeTextState}
                                     onDeleteState={onDeleteState}/> :
                <PoemPictureElementEdit state={{"index": index, "element": element}}
                                        onChangePictureState={onChangePictureState}
                                        onDeleteState={onDeleteState}
                                        onChangePictureScale={onChangePictureScale}/>
        });

    const checkbox = (
        <div>
            <input
                type="checkbox"
                id="checkbox"
                checked={isChecked}
                onChange={checkHandler}
            />
            <label htmlFor="checkbox"> Опубликовано </label>
        </div>
    );


    return (
        <div>

            <Container>
                <Loading isLoading={isLoading}/>
                <h2> Редактировать стихотворение </h2>
                <div id="parent">
                    <div id="wide">
                        <FormGroup>
                            {checkbox}
                        </FormGroup>
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
                            <Button color="secondary" tag={Link} to="/poems">Закрыть</Button>
                        </FormGroup>
                    </div>
                    <div id="narrow" className="center"><PoemRead withComments={false}/></div>
                </div>
            </Container>
        </div>
    );
}

