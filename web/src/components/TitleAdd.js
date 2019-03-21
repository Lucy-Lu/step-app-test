import React, {Component} from "react";
import {Link, withRouter} from "react-router-dom"
import {
    Table,
    Card,
    CardBody,
    Form,
    Input,
    Button,
    Col,
    Row,
    FormGroup,
    Label,
    FormText,
    Nav,
    NavItem
} from "reactstrap";
import Moment from 'react-moment';
import 'moment-timezone';


class TitleAdd extends Component {
    constructor(props) {
        super(props);
        this.state = {
            ownerName: "",
            description: "",
        };
        this.setOwnerName = this.setOwnerName.bind(this);
        this.setDescription = this.setDescription.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    setOwnerName(event) {
        this.setState({ownerName: event.target.value});
    }

    setDescription(event) {
        this.setState({description: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        var self = this;
        fetch(`/api/titles/`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                // titleRecordId: this.state.titleRecordId,
                ownerName: this.state.ownerName,
                description: this.state.description
            })
        }).then(res => {
            if (res.ok) {
                return res.json();
            } else {
                console.log('res is :', res)
                throw new Error('Something went wrong');
            }
        })
            .then(json => {
                console.log('add title successfully!', json)
                self.props.history.push(`/titles/${json.id}`);
            });
    }

    componentDidMount() {
        // this.loadTitle();
    }


    render() {
        return (
            <div>
                <div>
                    <Nav>
                        <NavItem>
                            <Link style={{marginRight: "10px"}} to="/">Home</Link>
                        </NavItem>
                        <NavItem>
                            <Link style={{marginLeft: "10px"}} to="/add">Add title</Link>
                        </NavItem>
                    </Nav>
                    <hr/>
                    <Form onSubmit={this.handleSubmit}>
                        <Col sm="12" md={{size: 8, offset: 2}}>
                            <Col md={{size: 8, offset: 2}}>
                                <h3>Add a new title</h3>
                            </Col>
                            <hr/>
                            <Row form>
                                <Col md={6}>
                                    <FormGroup>
                                        <Label for="ownerName">Owner Name</Label>
                                        <Input type="text" name="ownerName" id="ownerName"
                                               value={this.state.ownerName} onChange={this.setOwnerName}
                                               placeholder="Owner Name"/>
                                    </FormGroup>
                                </Col>
                                <Col md={6}>
                                    <FormGroup>
                                        <Label for="description">Description</Label>
                                        <Input type="text" name="description" id="description"
                                               value={this.state.description} onChange={this.setDescription}
                                               placeholder="description"/>
                                    </FormGroup>
                                </Col>
                            </Row>

                            <Button color="primary" type="submit" value="Submit">Add</Button>
                        </Col>

                    </Form>
                </div>


            </div>


        );
    }
}

export default withRouter(TitleAdd);