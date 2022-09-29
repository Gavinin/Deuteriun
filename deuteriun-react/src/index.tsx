import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import 'antd/dist/antd.less'
import reportWebVitals from './reportWebVitals';
import ComponentProvider from "./components/global/ComponentProvider";
import "./i18n-config"
import {FullPageError} from "./components/global/FullyPageLoading";
import {ErrorBoundary} from "./components/global/ErrorBoundary";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <ComponentProvider>
                <App/>
        </ComponentProvider>
    </React.StrictMode>
);

reportWebVitals();
