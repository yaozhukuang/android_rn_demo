import React, { PureComponent } from 'react'
import {FlatList, Image, Text, TouchableHighlight,View, StyleSheet} from "react-native";
import GithubRanking from "../ranking/GithubRanking";
import { colors } from "../../base/Color";

const topicIcon = require('../../assets/ic_topic.png');
const projectIcon = require('../../assets/ic_project.png');
const newsIcon = require('../../assets/ic_news.png');
const rankIcon = require('../../assets/ic_ranking.png');
const webIcon = require('../../assets/ic_website.png');
const mineIcon = require('../../assets/ic_mine.png');

export default class Home extends PureComponent {

    render() {
        return (
            <FlatList
                data={this.getItemList()}
                renderItem={this.renderItem}
                numColumns={2}
                showsVerticalScrollIndicator={false}
                style={styles.list}
                keyExtractor={(item, index) => index.toString()} />
        );
    }

    /**
     * 获取首页item列表
     */
    getItemList = () => {
        return [
            this.createItem('社区', topicIcon, 'Topic'),
            this.createItem('项目', projectIcon, 'Project'),
            this.createItem('News', newsIcon, 'News'),
            this.createItem('Github排名', rankIcon, 'GithubRanking'),
            this.createItem('酷站', webIcon, 'WebSite'),
            this.createItem('我的', mineIcon, 'Mine'),
        ];
    };

    /**
     * 创建首页item
     * @param name item名字
     * @param icon item图标
     * @param target 点击item跳转的目标也
     */
    createItem = (name, icon, target) => {
        return { name: name, icon: icon, target: target }
    };

    /**
     * 绘制item
     * @param item item信息
     */
    renderItem = ({ item }) => {
        return (
            <TouchableHighlight
                underlayColor={'#F7F7F7'}
                onPress={() => this.props.navigation.navigate(item.target)}
                style={styles.touch}>
                <View style={styles.item}>
                    <Image source={item.icon} style={{ width: 100, height: 100 }} />
                    <Text style={{ color: colors.textLight, fontSize: 16, marginTop: 5 }}>{item.name}</Text>
                </View>
            </TouchableHighlight>
        );
    };

}

const styles = StyleSheet.create({
    list: {
        paddingLeft: 8,
    },
    touch: {
        flex: 1,
        height: 200,
        marginTop: 8,
        marginRight: 8,
        backgroundColor: colors.white
    },
    item: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    }
});
