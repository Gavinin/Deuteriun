export const getDate = () => {
    return Date.now();
}

export const getFormatDate = () => {
    return dateFormatter(getDate())
}

export const dateFormatter = (time: number) => {
    if (!time) return ''
    let date = new Date(time)
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
        + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds()
}