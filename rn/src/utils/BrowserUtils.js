import {Linking} from 'react-native'

/**
 * 使用默认浏览器打开网页
 * @param url 指定网页路径
 */
export function openUrlByBrowser(url) {
    Linking.canOpenURL(url).then(supported => {
        if (!supported) {
            console.warn('Can\'t handle url: ' + url);
        } else {
            return Linking.openURL(url);
        }
    }).catch(() => console.error('An error occurred', url));
}
