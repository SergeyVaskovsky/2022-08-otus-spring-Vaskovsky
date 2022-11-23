import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup} from 'reactstrap';

export default function Comments(book) {
    const [comments, setComments] = useState([]);

    useEffect(() => {
        fetch(`/books/comments/${book.book.id}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setComments(data);
            });

    }, [book]);

    const remove = async id => {
        await fetch(`/books/comments/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedComments = [...comments].filter(i => i.id !== id);
            setComments(updatedComments);
        });
    }

    return (
        comments.map(comment => {
                return (
                    <tr key={comment.id}>
                        <td style={{whiteSpace: 'nowrap'}}>{comment.description}</td>
                        <td>
                            <ButtonGroup>
                                <Button size="sm" color="danger" onClick={() => remove(comment.id)}>Удалить</Button>
                            </ButtonGroup>
                        </td>
                    </tr>
                )
            }
        ));
}
