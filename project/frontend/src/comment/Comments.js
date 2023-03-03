import React, {useEffect, useState} from 'react';
import CommentsService from "../service/CommentsService";
import {Link} from "react-router-dom";
import Comment from "./Comment";
import "./Comments.css"

export default function Comments(data) {
    const [comments, setComments] = useState([]);
    const [activeIndex, setActiveIndex] = useState(null);
    const commentsService = new CommentsService();

    useEffect(() => {
        commentsService.getComments(data.poemId).then(comments => {
            comments.sort((a, b) => a.id - b.id);
            setComments(comments);
        });
    }, [data]);

    const handleAddComment = (event, comment) => {
        event.preventDefault();
        if (comment !== null) {
            setActiveIndex(comment.id);
        } else {
            setActiveIndex(-1);
        }
    }

    const onClose = () => {
        setActiveIndex(null);
    };

    const onAdded = (data) => {
        setActiveIndex(null);
        let localComments = [...comments];
        localComments.push(data);
        setComments(localComments);
    };

    const commentsList = (rootId) => {
        return comments.filter(comment => comment.rootCommentId === rootId).map((comment, index) => {

            const commentsCore = (
                <div key={index}>
                    <div>
                        {comment.name}
                    </div>
                    {comment.text.split("\n").map((i, key) => {
                        return <div key={key}>{i}</div>;
                    })}

                    {!comment.moderated ? "" :
                        <Link to={""} onClick={(event) => handleAddComment(event, comment)}>Ответить</Link>
                    }
                    {commentsList(comment.id)}
                    <ul>
                        <li>
                            {activeIndex === comment.id ?
                                <Comment data={{"poemId": data.poemId, "comment": comment}} onClose={onClose}
                                         onAdded={onAdded}/> : ""}
                        </li>
                    </ul>

                </div>
            );

            if (rootId === null) {
                return commentsCore;
            } else {
                return <ul>
                    <li>{commentsCore}</li>
                </ul>;
            }
        });
    }

    return (
        <div>
            <Link to={""} onClick={(event) => handleAddComment(event, null)}>Комментировать</Link>
            {commentsList(null)}
            {activeIndex === -1 ?
                <Comment data={{"poemId": data.poemId, "comment": null}} onClose={onClose} onAdded={onAdded}/> : ""}
        </div>
    );
}
