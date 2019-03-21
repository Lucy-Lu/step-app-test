import React, {Component} from "react";
import {Link, withRouter} from "react-router-dom"
import {Table, Card, CardBody, Form, Input, Button, Alert, Nav, NavItem, Col, Row} from "reactstrap";
import Moment from 'react-moment';
import 'moment-timezone';
import TitleSearch from "./TitleSearch";

class TitlePage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: undefined,
            titleHistoryList: [],
            ownerChangeValue: "",
            visible: false,
            showError: false,
        }
        this.loadTitle = this.loadTitle.bind(this);
        this.ownerNameHandleChange = this.ownerNameHandleChange.bind(this);
        this.ownerNameHandleSubmit = this.ownerNameHandleSubmit.bind(this);
        this.deleteTitle = this.deleteTitle.bind(this);
        this.onDismiss = this.onDismiss.bind(this);
    }

    onDismiss() {
        this.setState({visible: false});
        this.props.history.push(`/`);
    }

    ownerNameHandleChange(event) {
        this.setState({ownerChangeValue: event.target.value});
    }

    ownerNameHandleSubmit(event) {
        event.preventDefault();
        var self = this;
        fetch(`/api/titles/${this.state.data.id}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ownerName: this.state.ownerChangeValue}),
        })
            .then(res => res.json())
            .then(json => {
                console.log("json  ***********", json)
                self.setState({
                    data: json,
                    titleHistoryList: json.titleHistoryList,
                    ownerChangeValue: '',
                })
            });
    }

    loadTitle() {
        var self = this;
        console.log('props : ', this.props)
        var titleNo = this.props.match.params.titleNo;
        fetch(`/api/titles/${titleNo}`)
            .then(res => {
                if (res.ok) {
                    return res.json();
                } else {
                    self.setState({
                        showError: true
                    });
                    throw new Error('Something went wrong');
                }
            })
            .then(json => {
                console.log("json is ????", json)
                console.log("titleHistoryList  *********** is ", json.titleHistoryList)
                self.setState({
                    data: json,
                    titleHistoryList: json.titleHistoryList,

                })
            })
            .catch((error) => {
                console.log("error is :", error)
            });
        ;
    }


    deleteTitle(event) {
        event.preventDefault();

        var self = this;

        fetch(`/api/titles/${this.state.data.id}`, {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json",
            },
            body: {},
        })
            .then(res => {
                console.log("res  ***********", res.statusText)
                self.setState({visible: true,});


            });
    }


    componentDidMount() {
        this.loadTitle();
    }

    componentDidUpdate(newProps) {
        var titleNo = this.props.match.params.titleNo;
        if (this.state.data && this.state.data.id != titleNo) {
            this.loadTitle();
        }
    }

    renderTitleItem(title, index, lastIndex) {
        return (
            <div key={title.id} style={(index == (lastIndex)) ? {backgroundColor: '#f8f9fa', fontWeight: 'bold'} : {}}>
                <Row>
                    <Col md={5}>
                        <div>
                            <Moment format="YYYY/MM/DD HH:mm">
                                {title.updatedTime}
                            </Moment>
                        </div>
                    </Col>
                    <Col md={5}>
                        <div>{title.ownerName}</div>
                    </Col>
                </Row>
                <hr style={{backgroundColor: '#fff', borderTop: '2px dashed #8c8b8b'}}/>

            </div>
        )
    }

    render() {
        var titleNo = this.props.match.params.titleNo;
        var titles = (this.state.titleHistoryList);
        var lastIndex = titles.length - 1;
        console.log('data ', this.state.data)

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
                    <TitleSearch/>
                </div>
                <hr/>
                <div>
                    <h3>Title #{titleNo}</h3>
                    {this.state.showError && <Alert color="danger">
                        Title not found!
                    </Alert>}
                    {this.state.data === undefined && <p>
                        Loading...
                    </p>}


                    {this.state.data &&
                    <div>
                        <Table>
                            <tbody>
                            <tr>
                                <th>Title Record</th>
                                <td>{titleNo}</td>
                            </tr>
                            <tr>
                                <th>Description</th>
                                <td>{this.state.data.description}</td>
                            </tr>
                            <tr>
                                <th>Current Owner</th>
                                <td>{this.state.data.ownerName}</td>
                            </tr>
                            <tr>
                                <th>updatedTime</th>
                                <td>
                                    <Moment format="YYYY/MM/DD HH:mm">
                                        {this.state.data.updatedTime}
                                    </Moment>
                                </td>

                            </tr>
                            </tbody>
                        </Table>
                        <div>
                            <h2>History</h2>
                            <Col sm="12">
                                <Row>
                                    <Col md={5}>
                                        <h3>Updated Time</h3>
                                    </Col>
                                    <Col md={5}>
                                        <h3>Owner Name</h3>
                                    </Col>
                                </Row>
                            </Col>
                            {titles.map((title, index) =>
                                this.renderTitleItem(title, index, lastIndex)
                            )}
                            <Card color="light" style={{marginTop: "50px"}}>
                                <CardBody>
                                    <h4>Change Owner</h4>
                                    <p>As a registered conveyancing lawyer, you may record a change of ownership of this
                                        title.</p>
                                    <Form inline onSubmit={this.ownerNameHandleSubmit}>
                                        <Input type="text" value={this.state.ownerChangeValue}
                                               onChange={this.ownerNameHandleChange}
                                               placeholder="Enter the new owner name" style={{width: "400px"}}/>
                                        &nbsp;
                                        <Button color="primary" type="submit" value="Submit">Save</Button>
                                    </Form>
                                </CardBody>
                            </Card>
                            <Alert color="danger" isOpen={this.state.visible} toggle={this.onDismiss} fade={false}>
                                Title Deleted!
                            </Alert>
                            <Card style={{marginTop: "50px", backgroundColor: "#f9dc86", borderColor: "#f9dc86"}}>
                                <CardBody>
                                    <h4>Delete Title</h4>
                                    <p>As the system manager, you can delete this title.</p>
                                    <Button color="danger" onClick={this.deleteTitle}>Delete</Button>
                                </CardBody>
                            </Card>

                        </div>
                    </div>
                    }

                </div>
            </div>

        );
    }
}

export default withRouter(TitlePage);