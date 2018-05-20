import React, { PureComponent } from 'react'
import { Provider } from "react-redux";
import { store } from "./core/store/Store";
import { AppNavigator } from './core/navigate/AppNavigator'
import { YellowBox } from 'react-native';

YellowBox.ignoreWarnings(['Warning: isMounted(...) is deprecated', 'Module RCTImageLoader']);
export default class Test extends PureComponent {

    render() {
        return (
            <Provider store={store}>
                <AppNavigator />
            </Provider>
        );
    };

}