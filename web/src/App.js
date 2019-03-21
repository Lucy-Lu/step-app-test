import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link, withRouter } from "react-router-dom";
// import { Nav, NavItem, NavLink } from 'reactstrap';
import Home from "./components/Home";
import TitleAdd from "./components/TitleAdd";
import TitlePage from "./components/TitlePage";

class App extends Component {
  constructor(props) {
    super(props);
    this.selectAddress = this.selectAddress.bind(this);
  }
  selectAddress(address) {
    this.props.history.push(`/titles/${address.title_no.replace('/', '$')}`);
  }
  render() {
    return (
      <Router>
        <div style={{margin: "15px"}}>
          <Route exact path="/" component={Home} />
          <Route path="/add" component={TitleAdd} />
          <Route path="/titles/:titleNo" component={TitlePage} />
        </div>
      </Router>
    );
  }
}

export default App;