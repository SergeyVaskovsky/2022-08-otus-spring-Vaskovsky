import React, {useEffect, useState} from 'react';
import {Button, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";

export default function PoemList(state) {

    const [poems, setPoems] = useState([]);

    const [isLoading, setIsLoading] = useState(true);
    const poemService = new PoemService();

    useEffect(() => {
        poemService.getPoems(state.readonly)
            .then(data => {
                setPoems(data);
                setIsLoading(false);
            });
    }, [setPoems, setIsLoading, state.readonly]);

    const poemList =
        poems.map(poem => {
            return <tr key={poem.id}>
                <td style={{whiteSpace: 'nowrap'}}>
                    {state.readonly ?
                        <Link to={"/readonly/poems/" + poem.id}>{poem.title}</Link> :
                        <Link to={"/poems/" + poem.id}>{poem.title}</Link>
                    }
                </td>
            </tr>
        });

    return (
        <div>
            <Container fluid>
                <Loading isLoading={isLoading}/>
                <h2>Список стихотворений</h2>
                <div> {!state.readonly ?
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/poems/new">Добавить стихотворение</Button>
                    </div> : ""
                }
                    <Table className="mt-4">
                        <tbody>
                        {poemList}
                        </tbody>
                    </Table>
                </div>
            </Container>
        </div>
    );
}