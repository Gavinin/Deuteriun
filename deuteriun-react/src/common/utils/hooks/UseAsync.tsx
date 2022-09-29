import {useState} from "react";

interface State<S> {
    data: S | null;
    error: Error | null;
    state: "idle" | "loading" | "successful" | "error";
}

const initState: State<null> = {
    state: "idle",
    error: null,
    data: null
}

export function useAsync<S>(stateData?: State<S>) {
    const [status, setStatus] = useState<State<S>>({
        ...initState,
        ...stateData
    })

    const setData = (data: S) => setStatus({
        data,
        state: "successful",
        error: null,
    })

    const setError = (error: Error) => setStatus({
        data: null,
        state: "error",
        error: error
    })

    const run = (promise: Promise<S>) => {
        if (promise === null || !promise.then) {
            throw new Error("Must input the type of promise.")
        }
        setStatus({
            ...initState,
            state: "loading"
        })
        return promise
            .then((res) => {
                setData(res);
                return res;
            })
            .catch((error: Error) => {
                setError(error);
                return Promise.reject(error)
            });
    }
    return {
        isIdle: status.state === "idle",
        isError: status.state === "error",
        isSuccess: status.state === "successful",
        isLoading: status.state === "loading",
        run,
        setData,
        setError,
        ...status
    }

}