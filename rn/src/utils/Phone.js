import {Platform} from "react-native";
import {Dimensions} from "react-native";

/**
 * 判断当前手机SDK版本是否高于指定版本
 * @param version 传入的版本号
 * @returns boolean true手机版本号高于传入版本号，false则相反
 */
export function higher(version) {
    return Platform.OS === 'android' && Platform.Version > version;
}
