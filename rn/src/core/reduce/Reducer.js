import {combineReducers} from 'redux'
import splashReducer from '../../app/splash/SplashReducer'
import loginReducer from '../../app/login/LoginReducer'

export const reducer = combineReducers({
    splash:splashReducer,
    login:loginReducer
});