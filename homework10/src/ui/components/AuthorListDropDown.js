import React from 'react'

export const AuthorDropdown = (props) => (
    <div>
        <select
            name="Автор"
            onChange={props.onChange}>

            <option defaultValue>Выберите автора</option>
            {props.options.map((item, index) => (
                <option key={index} value={item.id}>
                    {item.name}
                </option>
            ))}
        </select>
    </div>
)

export default class AuthorListDropDown extends React.Component {
    constructor() {
        super()
        this.state = {
            collection: [],
            value: '',
        }
    }

    componentDidMount() {
        fetch('api/author')
            .then((response) => response.json())
            .then((res) => this.setState({collection: res}))
    }

    onChange = (event) => {
        this.setState({value: event.target.value})
    }

    render() {
        return (
            <div>
                <label>Автор</label>
                <CustomDropdown
                    name={this.state.name}
                    options={this.state.collection}
                    onChange={this.onChange}
                />
            </div>
        )
    }
}