import React, {useEffect, useState} from 'react';
import {Button, Container, Table} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import {Link} from 'react-router-dom';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";

export default function PoemList() {

    const [poems, setPoems] = useState([]);

    const [isLoading, setIsLoading] = useState(true);
    const poemService = new PoemService();

    useEffect(() => {
        poemService.getPoems()
            .then(data => {
                setPoems(data);
                setIsLoading(false);
            });
    }, [setPoems, setIsLoading]);

    const poemList =
        poems.map(poem => {
            return <tr key={poem.id}>
                <td style={{whiteSpace: 'nowrap'}}>
                    <Link to={"/poems/" + poem.id}>{poem.title}</Link>
                </td>
            </tr>
        });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <Loading isLoading={isLoading}/>
                <h2>Список стихотворений</h2>
                    <div>
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/poems/new">Добавить стихотворение</Button>
                        </div>

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