import React from 'react';
import {BrowserRouter, Route, Switch } from 'react-router-dom';


import Header from '../components/Header';
import NotFoundPage from '../components/NotFoundPage';
import HomePage from '../components/HomePage';


const AppRouter = () => {
    return(
       
                <BrowserRouter>
                    <div>
                        <Header />
                        <Switch>
                            <Route path="/" component={HomePage} exact={true} />
                            <Route component={NotFoundPage} />
                        </Switch>
                    </div>
                </BrowserRouter>
        
    );
};

export { AppRouter as default }

