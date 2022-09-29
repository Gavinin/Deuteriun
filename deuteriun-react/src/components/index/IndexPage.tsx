import React, {useEffect, useState} from "react";
import {useAuth} from "../../context/AuthContext";
import {getUserToken} from "../../services/AuthService";

const IndexPage = () => {
    const {user} = useAuth()

    return (

        <div>
           HI
        </div>
    )
}

export default IndexPage
