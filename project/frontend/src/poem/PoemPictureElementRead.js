import React, {useEffect, useState} from 'react';
import {Container, FormGroup} from 'reactstrap';
import {Image, StyleSheet, View} from 'react-native-web';

export default function PoemPictureElementEdit({state}) {
    const [file, setFile] = useState(state.element.picture ? 'data:image/*;base64,' + state.element.picture : {});
    const [size, setSize] = useState({"width": 0, "height": 0});

    useEffect(() => {
        if (state.element.picture) {
            Image.getSize(file, (width, height) => {
                setSize({
                    "width": Math.round(width / 100 * state.element.scale),
                    "height": Math.round(height / 100 * state.element.scale)
                })
            });
        }
    }, [file, setSize]);

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
        <div key={state.index}>
            <Container>
                <FormGroup>
                    <View>
                        <Image
                            style={styles.image}
                            source={{
                                uri: file
                            }}
                        />
                    </View>
                </FormGroup>
            </Container>
        </div>
    );
}