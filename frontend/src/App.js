import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './pages/Home';
import About from './pages/About';
import Post from './pages/Post';
import Header from './components/Header';
import Footer from './components/Footer';
import './assets/styles/main.css';

const App = () => {
    return (
        <Router>
            <div>
                <Header />
                <Switch>
                    <Route path="/" exact component={Home} />
                    <Route path="/about" component={About} />
                    <Route path="/post/:id" component={Post} />
                </Switch>
                <Footer />
            </div>
        </Router>
    );
};

export default App;