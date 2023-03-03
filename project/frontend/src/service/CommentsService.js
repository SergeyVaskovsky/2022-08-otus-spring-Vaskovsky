import AuthService from "./AuthService";

export default class CommentsService {

    getComments = async id => {
        return await fetch(`/api/comments/poem/` + id)
            .then(response => response.json());
    }

    add = async (description, poemId, comment) => {
        return await fetch(`/api/comments`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(
                {
                    'text': description,
                    'userId': AuthService.getCurrentUserId(),
                    'poemId': poemId,
                    'rootCommentId': comment == null ? null : comment.id,
                    'publishTime': new Date()
                }
            ),
        }).then(response => response.json());
    }
}

