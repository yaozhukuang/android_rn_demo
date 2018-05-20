import * as Actions from '../../core/action/Actions'

const initState = {
    loginDisable: true
};

export default function loginReducer(state = initState, action) {
    switch (action.type) {
        case Actions.LOGIN_STATE:
            return {loginDisable: action.status};
        default:
            return state;
    }
}