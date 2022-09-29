import React from "react";
import {AuthProvider} from "../../context/AuthContext";
import RouteProvider from "../../routes/RouteProvider";
import {FullPageError} from "./FullyPageLoading";
import {ErrorBoundary} from "./ErrorBoundary";
import WindowProvider from "./WindowProvider";
import Navbar from "../navbar/Navbar";
import RouterConfig from "../../routes/RouterConfig";

const ComponentProvider = ({children}: { children: React.ReactNode }) => {
    return (
        <AuthProvider>
            <ErrorBoundary fallbackRender={FullPageError}>
                <RouteProvider>
                    <Navbar/>
                    <WindowProvider>
                        <RouterConfig>
                            {children}
                        </RouterConfig>
                    </WindowProvider>
                </RouteProvider>
            </ErrorBoundary>
        </AuthProvider>
    )
}

export default ComponentProvider;
