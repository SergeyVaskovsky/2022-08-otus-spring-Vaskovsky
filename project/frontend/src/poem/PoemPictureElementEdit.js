import React, {useEffect, useState} from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import {View, Image, StyleSheet} from 'react-native-web';

export default function PoemPictureElementEdit ({ state, onChangePictureState, onDeleteState, onChangePictureScale }) {

    const [file, setFile] = useState('data:image/*;base64,' + state.element.picture);
    const [size, setSize] = useState({"width": 0, "height": 0});

    useEffect(() => {
        Image.getSize(file, (width, height) => {setSize({"width": width, "height": height})});
    }, [file, setSize]);


    const handleChangeState = (event) => {
        const url = URL.createObjectURL(event.target.files[0]);
        Image.getSize(url, (width, height) => {setSize({"width": width, "height": height});});
        setFile(url);
        onChangePictureState(state.index, event.target.files[0], 100);
    };

    const handleScaleChange = (value) => {
        const scale = value
        setSize({"width": Math.round(size.width / 100 * scale), "height": Math.round(size.height / 100 * scale)})
        onChangePictureScale(state.index, scale);
    };

    const handleDelete = () => {
        onDeleteState(state.index);
    };

    const styles = StyleSheet.create({
        container: {
            paddingTop: 50,
        },
        image: {
            width: size.width,
            height: size.height
        }
    });

    return (
        <div key = {state.index}>
            <Container>
                    <FormGroup>
                        <View >
                            <Image
                                style={styles.image}
                                source={{
                                    uri: file
                                }}
                            />
                        </View>
                        <br/>
                        <input
                            type="file"
                            name="picture"
                            onChange={(event) => {handleChangeState(event)}}
                        />
                        <br/>
                        <br/>
                        <Label for="name">Масштаб</Label>
                        <Input type="text" name="scale" id="scale" defaultValue={state.element.scale || ''}
                               onChange={(event) => handleScaleChange(event.target.value)} autoComplete="scale"/>
                        <br/>
                        <Button color="secondary" onClick={() => handleDelete()}>Удалить</Button>
                    </FormGroup>
            </Container>
        </div>
    );
}