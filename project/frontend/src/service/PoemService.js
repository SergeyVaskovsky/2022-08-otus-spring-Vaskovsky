export default class PoemService {
    getPoems = async () => {
        return await fetch('/api/poems')
            .then(response => response.json());
    }

    getPoem = async id => {
        return await fetch(`/api/poems/${id}`)
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

    save = async item => {
        const formData = new FormData();
        formData.append("poem", item);
        return await fetch('/api/poems' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                //'Accept': 'application/json',
                //'Content-Type': 'application/json'
                //'Content-Type': 'multipart/form-data'
            },
            //body: JSON.stringify(item),
            data: formData
        }).then(response => response.json());
    }


}