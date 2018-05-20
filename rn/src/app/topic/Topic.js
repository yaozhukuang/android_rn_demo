import React, {PureComponent} from 'react'
import {FlatList, Text, View, StyleSheet, Image} from 'react-native'
import Request from '../../base/network/Request'
import * as Constant from '../../base/Config'
import {colors} from "../../base/Color";


export default class Topic extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }

    render() {
        return (
            <FlatList
                style={{backgroundColor: colors.white}}
                data={this.getItemList()}
                renderItem={this.renderItem}
                showsVerticalScrollIndicator={false}
                ItemSeparatorComponent={() => (<View style={{height: 1, backgroundColor:colors.divide}}/>)}
                keyExtractor={(item, index) => index.toString()}/>
        );
    }

    /**
     * 获取topic列表
     */
    getItemList = () => {
        let url = Constant.net.BASE_URL + Constant.net.TOPIC;
        Request.get(url, (response) => {
            console.log('response: ' + response);
        });
    };

    /**
     * 绘制列表item
     * @param index 列表索引
     */
    renderItem = ({index}) => {
        return (
            <View style={{paddingLeft:16, paddingRight:16}}>
                <View style={styles.itemTitle}>
                </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    itemTitle: {
        flexDirection:'row',
        alignItems:'center'
    }
});