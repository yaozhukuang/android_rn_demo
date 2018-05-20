import React from 'react'
import {createStackNavigator} from 'react-navigation'
import Splash from "../../app/splash/Splash";
import Login from '../../app/login/Login'
import Project from "../../app/project/Project";
import Topic from "../../app/topic/Topic";
import News from "../../app/news/News";
import GithubRanking from "../../app/ranking/GithubRanking";
import Mine from "../../app/mine/Mine";
import WebSite from "../../app/website/WebSite";
import Home from "../../app/home/Home";
import {View, StatusBar, StyleSheet, Image, Text, TouchableOpacity} from "react-native";
import {dimens} from "../../base/Dimen";
import {colors} from "../../base/Color";

export const AppNavigator = createStackNavigator({
    Splash: {
        screen: Splash,
        navigationOptions: () => setNavigateBar()
    },
    Login: {
        screen: Login,
        navigationOptions: () => setNavigateBar()
    },
    Home: {
        screen: Home,
        navigationOptions: () => setNavigateBar(createHomeNavigateBar())
    },
    Topic: {
        screen: Topic,
        navigationOptions: {
            header: ({navigation}) => creatCommonNavigateBar({navigation}, '社区')
        }
    },
    Project: {
        screen: Project,
        navigationOptions: {
            header: ({navigation}) => creatCommonNavigateBar({navigation}, '项目')
        }
    },
    News: {
        screen: News,
        navigationOptions: {
            header: ({navigation}) => creatCommonNavigateBar({navigation}, 'News')
        }
    },
    GithubRanking: {
        screen: GithubRanking,
        navigationOptions: {
            header: ({navigation}) => creatCommonNavigateBar({navigation}, 'Github排名')
        }
    },
    WebSite: {
        screen: WebSite,
        navigationOptions: {
            header: ({navigation}) => creatCommonNavigateBar({navigation}, '酷站')
        }
    },
    Mine: {
        screen: Mine,
        navigationOptions: {
            header: ({navigation}) => creatCommonNavigateBar({navigation}, '我的')
        }
    },
});

const setNavigateBar = (header = null) => {
    return {header};
};


const createHomeNavigateBar = () => {
    return (
        <View>
            <StatusBar translucent={true} backgroundColor={colors.transparent}/>
            <View style={[styles.container, {justifyContent: 'center', alignItems: 'center'}]}>
                <Text style={{color: colors.white, fontSize: 19}}>{'Home'}</Text>
            </View>
        </View>
    );
};

const creatCommonNavigateBar = ({navigation}, title) => {
    return (
        <View>
            <StatusBar translucent={true} backgroundColor={colors.transparent}/>
            <View style={styles.container}>
                <TouchableOpacity
                    onPress={() => navigation.goBack(null)}>
                    <Image source={require('../../assets/ic_back.png')} style={{width: 25, height: 25}}/>
                </TouchableOpacity>
                <Text style={{color: colors.white, fontSize: 18}}>{title}</Text>
                <View style={{width: 25, height: 25}}/>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        backgroundColor: colors.blue,
        paddingTop: dimens.statusBarHeight,
        alignItems: 'center',
        justifyContent: 'space-between',
        height: dimens.navigatorHeight + dimens.statusBarHeight,
        paddingLeft: 16,
        paddingRight: 16
    }
});