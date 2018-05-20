import React, {PureComponent} from 'react'
import {View, Text, StyleSheet, StatusBar, TouchableOpacity, TextInput, Button} from 'react-native'
import {connect} from "react-redux";
import {colors} from "../../base/Color";
import * as LoginAction from './LoginAction'
import * as BrowserUtils from '../../utils/BrowserUtils'
import {net, config, cache} from '../../base/Config'
import Request from '../../base/network/Request'
import {save} from '../../utils/StoreUtils'

class Login extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            account: '',
            password: ''
        }
    }

    render() {
        return (
            <View style={{backgroundColor: colors.white, flex: 1}}>
                <StatusBar translucent={true} backgroundColor={colors.transparent}/>

                <TouchableOpacity
                    onPress={() => BrowserUtils.openUrlByBrowser(net.REGISTER)}>
                    <Text style={styles.register}>{'注册'}</Text>
                </TouchableOpacity>

                <Text style={{fontSize: 27, color: colors.textBlack, marginTop: 70, marginLeft: 30}}>{'登录'}</Text>

                <TextInput
                    style={styles.input}
                    placeholder={'请输入账号'}
                    placeholderTextColor={colors.textLight}
                    underlineColorAndroid={colors.transparent}
                    onChangeText={(text) => this.inputAccount(text)}/>

                <TextInput
                    style={styles.input}
                    placeholder={'输入密码'}
                    placeholderTextColor={colors.textLight}
                    underlineColorAndroid={colors.transparent}
                    onChangeText={(text) => this.inputPassword(text)}/>

                <View style={styles.login}>
                    <Button disabled={this.props.state.login.loginDisable}
                            color='#D5D5D5' title={'登录'} onPress={() => this.login()}/>
                </View>
            </View>
        );
    }

    /**
     * 记录输入的用户名
     * @param account 用户输入用户名
     */
    inputAccount = (account) => {
        this.setState({
            account: account
        });
        this.setLoginBtnStatus(account, this.state.password);
    };

    /**
     * 记录输入的密码
     * @param password 用户输入密码
     */
    inputPassword = (password) => {
        this.setState({
            password: password
        });
        this.setLoginBtnStatus(this.state.account, password);
    };

    /**
     * 设置登录按钮是否可以点击
     */
    setLoginBtnStatus = (account, password) => {
        let disable = !(account && password);
        if (disable !== this.props.state.login.loginDisable) {
            this.props.dispatch(LoginAction.actionLoginStatus(disable));
        }
    };

    /**
     * 登录
     */
    login = () => {
        let body = {
            client_id: config.CLIENT_ID,
            client_secret: config.CLIENT_SECRET,
            grant_type: 'password',
            username: this.state.account,
            password: this.state.password
        };
        let navigate = this.props.navigation;
        Request.post(net.OAUTH_URL, {body: JSON.stringify(body)}, (response) => {
            if (response.access_token && response.refresh_token) {
                save(cache.USER, JSON.stringify(response));
                navigate.replace('Home');
            } else {
                alert('登录失败: ' + JSON.stringify(response));
            }
        });
    };

}

const styles = StyleSheet.create({
    register: {
        color: colors.textBlack,
        alignSelf: 'flex-end',
        paddingRight: 20,
        paddingTop: 20,
        fontSize: 16
    },
    input: {
        color: colors.textBlack,
        borderBottomWidth: 1,
        borderBottomColor: colors.divide,
        paddingBottom: 10,
        marginLeft: 30,
        marginRight: 30,
        marginTop: 40,
        fontSize: 16
    },
    login: {
        marginTop: 40,
        marginLeft: 30,
        marginRight: 30,
        borderRadius: 5,
        height: 50
    }
});


function mapStateToProps(state) {
    return {
        state: state
    }
}

export default connect(mapStateToProps)(Login)