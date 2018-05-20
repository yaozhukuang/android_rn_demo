import React, { PureComponent } from 'react';
import { Text, View, StyleSheet, TouchableOpacity } from "react-native";
import { colors } from "../../base/Color";
import { actionCountDown } from './SplashAction'
import { connect } from "react-redux";
import * as StoreUtil from '../../utils/StoreUtils'
import { cache } from '../../base/Config'

class Splash extends PureComponent {

    render() {
        return (
            <View style={{ flex: 1, paddingTop: 220, alignItems: 'center', backgroundColor: colors.white }}>
                <Text style={styles.text}>{'Splash'}</Text>

                <TouchableOpacity
                    onPress={() => this.skip()}
                    style={{flex: 1, alignSelf: 'stretch', justifyContent: 'flex-end', alignItems: 'flex-end'}}>
                    <Text style={styles.countDown}>{'跳过 ' + this.props.state.splash.count}</Text>
                </TouchableOpacity>
            </View>
        );
    };

    countDown = () => {
        this.props.dispatch(actionCountDown());
        if (this.props.state.splash.count <= 0) {
            this.skip();
        }
    };

    skip = () => {
        if (this.interval) {
            clearInterval(this.interval);
        }
        this.checkLogin();
    };

    checkLogin() {
        StoreUtil.get(cache.USER, (value) => {
            this.props.navigation.replace('Home');
        }, (error) => {
            this.props.navigation.replace('Home');
        })
    };

    componentDidMount() {
        this.interval = setInterval(this.countDown, 1000);
    }

    componentWillUnmount() {
        clearInterval(clearInterval(this.interval));
    }
}


const styles = StyleSheet.create({
    text: {
        color: colors.textBlack,
        fontSize: 23,
    },
    countDown: {
        marginBottom: 35,
        marginRight: 30,
        borderRadius: 20,
        borderColor: colors.divide,
        borderWidth: 1,
        paddingLeft: 10,
        paddingRight: 10,
        textAlign: 'center',
        fontSize: 12,
        paddingTop: 2
    },
});


function mapStateToProps(state) {
    return {
        state: state
    }
}

export default connect(mapStateToProps)(Splash)