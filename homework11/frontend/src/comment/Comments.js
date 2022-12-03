import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup} from 'reactstrap';
import CommentsService from "../service/CommentsService";

export default function Comments(data) {
    const [comments, setComments] = useState([]);
    const [description, setNewDescription] = useState("");
    const commentsService = new CommentsService();

    useEffect(() => {
        commentsService.getComments(data.book.id).then(d => setComments(d));
    }, [data]);

    const remove = id => {
        commentsService.remove(id).then(() => {
            let updatedComments = [...comments].filter(i => i.id !== id);
            setComments(updatedComments);
        });
    }

    const add = () => {
        if (!description) {
            return;
        }
        commentsService.add(description, data.book.id)
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
