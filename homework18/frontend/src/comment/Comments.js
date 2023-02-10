import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup} from 'reactstrap';
import CommentsService from "../service/CommentsService";
import Loading from "../main/Loading";

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
            setIsLoading(false);
            if (d.length === 1 && d[0].id === 0) {
                setAvailable(false);
            }
        });
    }, [data]);

    const remove = id => {
        setIsLoading(true);
        commentsService.remove(id).then(() => {
            let updatedComments = [...comments].filter(i => i.id !== id);
            setComments(updatedComments);
            setIsLoading(false);
        }).catch((error) => {
            setComments([{id: 0, description: 'По техническим причинам комментарии недоступны', bookId: 0}]);
            setAvailable(false);
            setIsLoading(false);
        });
    }

    const add = () => {
        if (!description) {
            return;
        }
        setIsLoading(true)
        commentsService.add(description, data.book.id)
            .then(d => {
                setIsLoading(false);
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
        <div>
            <Loading isLoading={isLoading}/>
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
        </div>
    );
}
