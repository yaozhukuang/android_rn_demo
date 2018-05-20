import * as Actions from '../../core/action/Actions'

export function actionCountDown() {
    return {
        type:Actions.COUNT_DOWN,
        message:'count down to navigate from splash to home'
    }
}