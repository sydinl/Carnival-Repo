import { ta } from "date-fns/locale"
import request from "src/utils/request"

// const { default: request } = require("src/utils/request")
const { BACK_PATH } = require("src/utils/Constants")

// const doLogin = (data) => {
//     return request({
//         url: BACK_PATH.DOLOGIN,
//         method: 'post',
//         data: data,
//     })
// }


// const getAuth = () => {
//     return request({
//         url: BACK_PATH.GETAUTH,
//         method: 'get',
//     })
// }

// const logout = () => {
//     return request({
//         url: BACK_PATH.LOGOUT,
//         method: 'get',
//     })
// }
const getTableList = (dbName) => {
    return request({
        url: BACK_PATH.GETTBLIST,
        method: 'get',
        params: { DBName: dbName }
    })
}
const getColumnList = (tableName) => {
    return request({
        url: BACK_PATH.GETCOLUMNLIST,
        method: 'get',
        params: { tableName: tableName }
    })
}

const triggerTemplate = (templateName) => {
    return request({
        url: BACK_PATH.TRIGGERTEMPLATE,
        method: 'get',
        params: { templateName: templateName }
    })
}
const saveTemplate = (data) => {
    return request({
        url: BACK_PATH.SAVETEMPLATE,
        method: 'post',
        data: data
    })
}
export {
    getTableList, getColumnList, saveTemplate,triggerTemplate
}