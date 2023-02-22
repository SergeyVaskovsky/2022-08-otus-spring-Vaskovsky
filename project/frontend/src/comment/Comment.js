import React, {useState} from 'react';
import {Button} from 'reactstrap';
import CommentsService from "../service/CommentsService";

export default function Comment({data, onClose}) {
    const [description, setNewDescription] = useState("");
    const commentsService = new CommentsService();

    const add = () => {
        if (!description) {
            return;
        }
        commentsService.add(description, data.poemId, data.comment)
            .then(data => onClose());
    }

    const close = () => {
        onClose();
    }

    return (
        <div>
            <textarea onChange={(event) => setNewDescription(event.target.value)}/>
            <br/>
            <Button size="sm" onClick={() => add()}>Добавить</Button>
            <Button size="sm" onClick={() => close()}>Отменить</Button>
        </div>
    );

}