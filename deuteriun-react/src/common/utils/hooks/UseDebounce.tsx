import {useEffect, useState} from "react";

const DEFAULT_DELAY = 200;

/**
 * @param value
 * @param delay
 */
export const useDebounce = <V,> (value:V, delay?: number) => {
    const [debounceValue, setDebounceValue] = useState(value);

    useEffect(() => {
        let delayValue = delay
        if (delayValue === undefined) {
            delayValue = DEFAULT_DELAY
        }
        const timeout = setTimeout(() => setDebounceValue(value), delayValue);
        return () => clearTimeout(timeout);
    }, [value, delay])

    return debounceValue;
}