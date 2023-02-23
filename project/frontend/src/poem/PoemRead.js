import React, {useEffect, useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import {Button, Container, FormGroup} from 'reactstrap';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";
import PoemPictureElementRead from "./PoemPictureElementRead";
import Comments from "../comment/Comments";

export default function PoemRead(state) {
    const {id} = useParams();

    const [item, setItem] = useState({});
    const [elements, setElements] = useState([]);
    const poemService = new PoemService();
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setIsLoading(true);
        poemService.getPoem(id)
            .then(data => {
                setItem(data);
                poemService.getPoemElements(id)
                    .then(data => {
                        data.sort((a, b) => a.id - b.id);
                        setElements(data);
                        setIsLoading(false);
                    })
            });
    }, [id, setItem, setElements]);


    const elementsList = elements.map((element, index) => {
        return element.type === "text" ?
            <div>
                {element.content.split("\n").map((i, key) => {
                    return <div key={key}>{i}</div>;
                })}
            </div> :
            <PoemPictureElementRead state={{"index": index, "element": element}}/>
    });

    return (
        <div>
            <Container>
                <Loading isLoading={isLoading}/>
                <FormGroup>
                    <h1>{item.title}</h1>
                    {elementsList}
                    {state.withComments && item !== {} ? <Comments poemId={id}/> : ""}
                </FormGroup>
                {
                    state.withComments ?
                        <FormGroup>
                            <Button color="secondary" tag={Link} to="/readonly/poems">Закрыть</Button>
                        </FormGroup> : ""
                }
            </Container>
        </div>
    );
}