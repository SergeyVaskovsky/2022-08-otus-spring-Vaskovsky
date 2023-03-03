import React, {useEffect, useState} from 'react';
import {Button, Container, FormGroup, Input, Label} from 'reactstrap';
import {Image, StyleSheet, View} from 'react-native-web';

export default function PoemPictureElementEdit ({ state, onChangePictureState, onDeleteState, onChangePictureScale }) {

    const [outerSize, setOuterSize] = useState({"width": 0, "height": 0});
    const [file, setFile] = useState(state.element.picture ? 'data:image/*;base64,' + state.element.picture : {});
    const [size, setSize] = useState({"width": 0, "height": 0});
    const [scale, setScale] = useState(state.element.scale);

    useEffect(() => {
        if (state.element.picture) {
            Image.getSize(file, (width, height) => {
                setOuterSize({"width": width, "height": height});
                setSize({
                    "width": Math.round(width / 100 * state.element.scale),
                    "height": Math.round(height / 100 * state.element.scale)
                })
            });
        }
    }, [file, setSize]);


    const handleChangeState = (event) => {
        setScale(100);
        const url = URL.createObjectURL(event.target.files[0]);
        Image.getSize(url, (width, height) => {
            setSize({"width": width, "height": height});
            setOuterSize({"width": width, "height": height});
        });
        setFile(url);
        onChangePictureState(state.index, event.target.files[0], scale);
    };

    const handleChangeScale = (value) => {
        setScale(value);
    };

    const handleDelete = () => {
        onDeleteState(state.index);
    };

    const handleNewScale = () => {
        setSize({
            "width": Math.round(outerSize.width / 100 * scale),
            "height": Math.round(outerSize.height / 100 * scale)
        })
        onChangePictureScale(state.index, scale);
    }

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
                            onChange={(event) => {
                                handleChangeState(event)
                            }}
                        />
                        <br/>
                        <br/>
                        <Label for="name">Масштаб</Label>
                        <Input type="text" name="scale" id="scale" defaultValue={state.element.scale || ''}
                               onChange={(event) => handleChangeScale(event.target.value)} autoComplete="scale"/>
                        <Button color="primary" onClick={() => handleNewScale()}>Применить новый масштаб</Button>
                        <br/>
                        <Button color="secondary" onClick={() => handleDelete()}>Удалить</Button>
                    </FormGroup>
            </Container>
        </div>
    );
}