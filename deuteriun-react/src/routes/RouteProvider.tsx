import React from 'react';
import {BrowserRouter as Router} from "react-router-dom";

const RouteProvider = ({children}: { children?: React.ReactNode }) => {
    return (
        <Router>
            {children}
        </Router>
    )
}

export default RouteProvider;