import * as Actions from '../../core/action/Actions'

const initState = {
    count: 1
};


export default function splashReducer(state = initState, action) {

    switch (action.type) {
        case Actions.COUNT_DOWN:
            return {
                count: state.count - 1
            };
        default:
            return state;
    }
}