import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup} from 'reactstrap';
import CommentsService from "../service/CommentsService";
import logo from "../assets/loading.gif";

export default function Comments(data) {
    const [comments, setComments] = useState([]);
    const [description, setNewDescription] = useState("");
    const commentsService = new CommentsService();
    const [isLoading, setIsLoading] = useState(true);
    const [available, setAvailable] = useState(true);

    useEffect(() => {
        setIsLoading(true);
        setAvailable(true);
        commentsService.getComments(data.book.id).then(d => {
            setComments(d);
            console.log(d);
            setIsLoading(false);
            if (d.length === 1 && d[0].id === 0) {
                setAvailable(false);
            }
        });
    }, [data]);

    const remove = id => {
        commentsService.remove(id).then(() => {
            let updatedComments = [...comments].filter(i => i.id !== id);
            setComments(updatedComments);
        }).catch((error) => {
            setComments([{id: 0, description: 'По техническим причинам комментарии недоступны', bookId: 0}]);
            setAvailable(false);
        });
    }

    const add = () => {
        if (!description) {
            return;
        }
        commentsService.add(description, data.book.id)
            .then(d => {
                console.log(d);
                if (d.id === 0) {
                    setComments([{id: 0, description: 'По техническим причинам комментарии недоступны', bookId: 0}]);
                    setAvailable(false);
                } else {
                    comments.push(d);
                    setComments([...comments]);
                }
            });
    }

    return (
        <div> {isLoading ?
            <img src={logo} alt="loading..."/> :
            <div>
                <table className="mt-4">
                    <tbody>
                    {comments.map(comment => {
                            return (
                                <tr key={comment.id}>
                                    <td style={{whiteSpace: 'nowrap'}}>{comment.description}</td>
                                    <td> {comment.id !== 0 ?
                                        <ButtonGroup>
                                            <Button size="sm" color="danger"
                                                    onClick={() => remove(comment.id)}>Удалить</Button>
                                        </ButtonGroup> : ""
                                    }
                                    </td>
                                </tr>
                            )
                        }
                    )
                    }
                    </tbody>
                </table>
                {available ?
                    <div>
                        <textarea onChange={(event) => setNewDescription(event.target.value)}/>
                        <br/>
                        <Button size="sm" onClick={() => add()}>Добавить</Button>
                    </div> : ""
                }
            </div>
        }
        </div>
    );
}
