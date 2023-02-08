export default class PoemService {
    getPoems = async () => {
        return await fetch('/api/poems')
            .then(response => response.json());
    }

    getPoem = async id => {
        return await fetch(`/api/poems/${id}`)
            .then(response => response.json())
    }

    getPoemElements = async id => {
        return await fetch(`/api/poems/${id}/elements`)
            .then(response => response.json())
    }
/*
    remove = async id => {
        return await fetch(`/api/books/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error();
            }
        });
    }*/

    savePoem = async item => {
        return await fetch('/api/poems' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        }).then(response => response.json());
    }

    addPoemElement = async element => {
        return await fetch('/api/poems/' + element.poem.id + '/elements', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(element)
        }).then(response => response.json());
    }

    updatePoemTextElement = async element => {
        return await fetch('/api/poems/text-elements/'+element.id, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(element)
        }).then(response => response.json());
    }

    updatePoemPictureElement = async element => {
        const formData = new FormData();
        formData.append("element", element);
        formData.append("file", element.file);
        return await fetch('/api/poems/picture-elements/'+element.id, {
            method: 'PUT',
            data: formData
        }).then(response => response.json());
    }

    deletePoemElement = async id => {
        return await fetch('/api/poems/elements/' + id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => response.json());
    }


}