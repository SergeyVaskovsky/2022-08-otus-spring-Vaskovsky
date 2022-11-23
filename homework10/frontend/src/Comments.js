import React, {Component} from 'react';
import {Button, ButtonGroup} from 'reactstrap';

class Comments extends Component {

    constructor(props) {
        super(props);
        this.state = {comments: []};
        this.remove = this.remove.bind(this);

    }

    componentDidMount() {
        this.getComments()
    }

    getComments() {
        fetch('/books/comments/' + this.props.book.id)
            .then(response => response.json())
            .then(data => this.setState({comments: data}));
        console.log(this.props.book);
    }

    async remove(id) {
        await fetch(`/books/comments/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedComments = [...this.state.comments].filter(i => i.id !== id);
            this.setState({comments: updatedComments});
        });
    }

    render() {
        const {comments} = this.state;

        const commentList = comments.map(comment => {
            return (
                <tr key={comment.id}>
                    <td style={{whiteSpace: 'nowrap'}}>{comment.description}</td>
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="danger" onClick={() => this.remove(comment.id)}>Удалить</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            )
        });

        return commentList;
    }
}

export default Comments;