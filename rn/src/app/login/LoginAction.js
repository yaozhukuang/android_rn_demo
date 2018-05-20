import * as Actions from '../../core/action/Actions'

/**
 * 设置登录按钮是否可以点击
 * @param status true：可以点击，false：不可点击
 * @returns {{type, status: boolean}}
 */
function actionLoginStatus(status) {
    return {
        type: Actions.LOGIN_STATE,
        status:status
    }
}


export {
    actionLoginStatus
}