import React, {PureComponent} from 'react'
import {StyleSheet, Text, TextInput, View} from 'react-native'
import {colors} from "../../base/Color";

export default class Mine extends PureComponent {

    render() {
        return (
            <View>
                <Text>{'mine'}</Text>
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
            </View>
        );
    }

    inputAccount = (account) => {
        console.log('inputAccount: ' + account);
    };

    inputPassword = (password) => {
        console.log('inputPassword: ' + password);
    };
}

const styles = StyleSheet.create({
    input: {
        color: colors.textBlack,
        borderBottomWidth: 1,
        borderBottomColor: colors.divide,
        paddingBottom: 10,
        marginLeft: 30,
        marginRight: 30,
        marginTop: 40,
        fontSize: 16
    }
});