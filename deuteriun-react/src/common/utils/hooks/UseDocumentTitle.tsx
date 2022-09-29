import React, {useEffect, useRef} from "react";

const useDocumentTitle = (title: string, stayWhileUnmount: boolean = false) => {
    const indexTitle = useRef(document.title).current

    useEffect(() => {
        document.title = title
    }, [title])

    useEffect(() => {
        return () => {
            if (stayWhileUnmount) {
                document.title = indexTitle
            }
        }
    }, [indexTitle, stayWhileUnmount])
}