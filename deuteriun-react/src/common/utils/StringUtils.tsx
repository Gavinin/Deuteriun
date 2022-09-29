import React from "react";

export const isEmpty = (str: unknown) => {
    return typeof str == "undefined" || str == null || str === "";
}

export const isNotEmpty = (str: unknown) => {
    return !isEmpty(str);
}

