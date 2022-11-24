import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup} from 'reactstrap';

export default function Comments(data) {
    const [comments, setComments] = useState([]);
    const [description, setNewDescription] = useState("");

    useEffect(() => {
        fetch(`/books/comments/${data.book.id}`)
            .then(response => response.json())
            .then(d => {
                setComments(d);
            });

    }, [data]);

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

    const add = async () => {
        if (!description) {
            return;
        }
        await fetch(`/books/${data.book.id}/comments`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: description,
        }).then(response => response.json())
            .then(d => {
                comments.push(d);
                setComments([...comments]);
            })
    }

    return (
        <div>
            <table className="mt-4">
                <tbody>
                {comments.map(comment => {
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
                )
                }
                </tbody>
            </table>
            <textarea onChange={(event) => setNewDescription(event.target.value)}/>
            <br/>
            <Button size="sm" onClick={() => add()}>Добавить</Button>
        </div>
    );
}
