import {useParams} from "react-router-dom";
import {useAsync} from "../../../common/utils/hooks/UseAsync";
import useMount from "../../../common/utils/hooks/UseMount";
import React, {useEffect} from "react";

const MessageDetail = () => {
    const {uuid} = useParams();


    useMount(()=>{
        if (uuid){

        }
    })

    useEffect(()=>{

    },[])

    return(
        <div>
            {uuid?uuid:null}
        </div>
    )

}

export default MessageDetail;