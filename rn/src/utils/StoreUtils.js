import * as AsyncStorage from "react-native/Libraries/Storage/AsyncStorage";

/**
 * 保存字符串到本地
 * @param key 保存的key
 * @param value 待保存的内容
 * @param failed 保存失败的处理
 */
export function save(key, value, failed) {
    AsyncStorage.setItem(key, value, (error) => {
        if (failed) {
            failed(error);
        }
    })
}

/**
 * 获取本地保存的字符串
 * @param key 保存的ky
 * @param result 成功回调
 * @param failed 失败回调
 */

export function get(key, result, failed) {
    AsyncStorage.getItem(key, (error, value) => {
        if (value) {
            result(value);
        } else {
            failed(error);
        }
    })
}

/**
 * 清除本地保存的内容
 * @param key 保存的ky
 * @param failed 失败回调
 */
export function remove(key, failed) {
    AsyncStorage.removeItem(key, (error) => {
        if (failed) {
            failed(error);
        }
    })
}