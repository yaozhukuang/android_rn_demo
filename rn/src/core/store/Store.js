import {createStore, applyMiddleware} from 'redux'
import thunk from 'redux-thunk'
import {reducer} from '../reduce/Reducer'

let logger = store => next => action => {
    if (typeof action === 'function') {
        console.log('dispatching a function');
    } else {
        console.log('dispatching', action);
    }
    let result = next(action);
    console.log('next state', store.getState());
    return result;
};

let middleware = [
    thunk,
    logger
];

const createStoreWithMiddleware = applyMiddleware(...middleware)(createStore);

export const store = createStoreWithMiddleware(reducer);